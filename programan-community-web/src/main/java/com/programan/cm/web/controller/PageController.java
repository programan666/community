package com.programan.cm.web.controller;


import com.mysql.cj.xdevapi.Collection;
import com.programan.cm.db.model.*;
import com.programan.cm.web.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private ArticleManager articleManager;

    private UserManager userManager;

    private CreateTypeManager createTypeManager;

    private TopicManager topicManager;

    private UserFollowManager userFollowManager;

    private ArticleLikeManager articleLikeManager;

    private ArticleCommentManager articleCommentManager;

    private IndustryManager industryManager;

    private CourseManager courseManager;

    @Autowired
    public void setCourseManager (CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @Autowired
    public void setIndustryManager(IndustryManager industryManager) {
        this.industryManager = industryManager;
    }

    @Autowired
    public void setArticleCommentManager(ArticleCommentManager articleCommentManager) {
        this.articleCommentManager = articleCommentManager;
    }

    @Autowired
    public void setArticleLikeManager(ArticleLikeManager articleLikeManager) {
        this.articleLikeManager = articleLikeManager;
    }

    @Autowired
    public void setUserFollowManager(UserFollowManager userFollowManager) {
        this.userFollowManager = userFollowManager;
    }

    @Autowired
    public void setTopicManager(TopicManager topicManager) {
        this.topicManager = topicManager;
    }

    @Autowired
    public void setCreateTypeManager(CreateTypeManager createTypeManager) {
        this.createTypeManager = createTypeManager;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setArticleManager(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @RequestMapping(value = "/register")
    public String getRegister(Model model) {
        List<Industry> industryList = industryManager.selectAll();
        model.addAttribute("industry", industryList);
        return "register";
    }

    @RequestMapping(value = "/index")
    public String goToIndex() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            List<GrantedAuthority> list = new ArrayList<>();
            userDetails.getAuthorities().forEach(userDetails1 -> list.add(userDetails1));
            String role = list.get(0).getAuthority();
            switch (role){
                case "ROLE_USER":
                    return "index";
                case "ROLE_ADMIN":
                    return "manager";
                default:
                    return "index";
            }
        } catch (Exception e) {
            return "index";
        }

    }

    @RequestMapping(value = "/page/top-nav")
    public String getTopNav(Model model) {
        User user = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userManager.selectByUserName(userDetails.getUsername());
        } catch (Exception e) {
            logger.info("当前用户未登录");
        }
        model.addAttribute("user", user);
        return "html/top-nav";
    }

    @RequestMapping(value = "/page/edit")
    public String getEdit() {
        return "html/edit";
    }

    @RequestMapping(value = "/page/userBaseEdit")
    public String getUserBaseEdit() {
        return "html/userBaseEdit";
    }

    @RequestMapping(value = "/page/userInfoEdit")
    public String getUserInfoEditt() {
        return "html/userInfoEdit";
    }

    @RequestMapping(value = "/page/editBlog")
    public String getEditBlog() {
        return "html/editBlog";
    }

    @RequestMapping(value = "/page/articleManage")
    public String getArticleManage(String username, Model model) {
        List<Article> articleList = articleManager.selectAllByUser(userManager.selectByUserName(username));
        List<CreateType> createTypeList = createTypeManager.selectAll();
        List<Topic> topicList = topicManager.selectAll();
//        createTypeList.add(new CreateType(0L,"所有"));
//        topicList.add(new Topic(0L,"所有"));
        model.addAttribute("articleList", articleList);
        model.addAttribute("createTypeList", createTypeList);
        model.addAttribute("topicList", topicList);
        return "html/articleManage";
    }

    @RequestMapping(value = "/page/article")
    public String getArticle(@RequestParam("articleId") String articleId, Model model) {

        User loginUser = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUser = userManager.selectByUserName(userDetails.getUsername());
        } catch (Exception e) {
            logger.info("当前用户没有登陆");
        }
        Article article = articleManager.selectById(articleId, true);
        User user = article.getUser();
        UserFollow userFollow = userFollowManager.selectByBoth(user, loginUser);
        ArticleLike articleLike = articleLikeManager.selectByBoth(article, loginUser);
        List<Article> articleList = articleManager.selectAllByUser(user);
        int likeNum = articleLikeManager.selectCountByArticle(article);
        int totalFansNum = userFollowManager.selectCountByFocus(user);
        int totalLikeNum = 0;
        int totalCommentNum = 0;
        String edit = "no";
        for(Article article1 : articleList) {
            totalLikeNum += articleLikeManager.selectCountByArticle(article1);
            totalCommentNum += articleCommentManager.selectCountByArticle(article1);
        }
        String alreadyFans = "no";
        String alreadyLike = "no";
        if(userFollow != null) {
            alreadyFans = "yes";
        }
        if(articleLike != null) {
            alreadyLike = "yes";
        }
        if(loginUser!=null && loginUser.getId() == user.getId()) {
            edit = "yes";
        }
        model.addAttribute("like", alreadyLike);
        model.addAttribute("follow", alreadyFans);
        model.addAttribute("article", article);
        model.addAttribute("user", user);
        model.addAttribute("likeNum", likeNum);
        model.addAttribute("totalFansNum", totalFansNum);
        model.addAttribute("totalLikeNum", totalLikeNum);
        model.addAttribute("totalCommentNum", totalCommentNum);
        model.addAttribute("edit", edit);
        return "html/article";
    }

    @RequestMapping(value = "/page/blog")
    public String getBlog() {
        return "html/blog";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String getRegister(@RequestParam("username") String username,
                              @RequestParam("pwd") String pwd,
                              @RequestParam("phone") String phone,
                              @RequestParam("industry") String industryId,
                              Model model) {
        logger.info("/user/register");
        User user = new User(0L, username, pwd, username, "", "", null, phone, "", industryManager.selectById(industryId), "", "", "/imgs/xiaohuangren.png", 0L);
        userManager.saveUser(user);
        model.addAttribute("username", username);
        model.addAttribute("pwd", pwd);
        logger.info("finished /user/register");
        return "login";
    }

    @RequestMapping(value = "/login.html")
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/forgetPwdPage", method = RequestMethod.GET)
    public String getForgetPwd() {
        return "forgetPassword";
    }

    @RequestMapping(value = "/getVideo/{courseId}", method = RequestMethod.GET)
    public String video(@PathVariable String courseId, Model model) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            User user = userManager.selectByUserName(userDetails.getUsername());
            Course course = courseManager.selectById(courseId);
            model.addAttribute("courseUrl", course.getVideoUrl());
        } catch (Exception e) {
            model.addAttribute("courseUrl", "");
        }
        return "html/video";
    }



}
