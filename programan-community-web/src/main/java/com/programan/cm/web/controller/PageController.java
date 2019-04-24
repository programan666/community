package com.programan.cm.web.controller;


import com.mysql.cj.xdevapi.Collection;
import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.CreateType;
import com.programan.cm.db.model.Topic;
import com.programan.cm.db.model.User;
import com.programan.cm.web.manager.ArticleManager;
import com.programan.cm.web.manager.CreateTypeManager;
import com.programan.cm.web.manager.TopicManager;
import com.programan.cm.web.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class PageController {

    private ArticleManager articleManager;

    private UserManager userManager;

    private CreateTypeManager createTypeManager;

    private TopicManager topicManager;

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



    @RequestMapping(value = "/index")
    public String goToIndex() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        List<GrantedAuthority> list = new ArrayList<>();
        userDetails.getAuthorities().forEach(userDetails1 -> list.add(userDetails1));
        String role = list.get(0).getAuthority();
        switch (role){
            case "ROLE_USER":
                return "/index";
            case "ROLE_ADMIN":
                return "/manager";
            default:
                return "/index";
        }

    }

    @RequestMapping(value = "/page/top-nav")
    public String getTopNav() {
        return "/html/top-nav";
    }

    @RequestMapping(value = "/page/edit")
    public String getEdit() {
        return "/html/edit";
    }

    @RequestMapping(value = "/page/userBaseEdit")
    public String getUserBaseEdit() {
        return "/html/userBaseEdit";
    }

    @RequestMapping(value = "/page/userInfoEdit")
    public String getUserInfoEditt() {
        return "/html/userInfoEdit";
    }

    @RequestMapping(value = "/page/editBlog")
    public String getEditBlog() {
        return "/html/editBlog";
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
        return "/html/articleManage";
    }

    @RequestMapping(value = "/page/article")
    public String getArticle(@RequestParam("articleId") String articleId, Model model) {
        Article article = articleManager.selectById(articleId);
        User user = article.getUser();
        model.addAttribute("article", article);
        model.addAttribute("user", user);
        return "/html/article";
    }


}
