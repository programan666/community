package com.programan.cm.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.JsonArray;
import com.programan.cm.common.Config;
import com.programan.cm.common.utils.FileSizeUtil;
import com.programan.cm.common.utils.HttpUtil;
import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.*;
import com.programan.cm.web.manager.*;
import com.alibaba.fastjson.JSONObject;
import com.programan.cm.web.model.LatestTrend;
import com.programan.cm.web.util.StatisticsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

    private Config config;

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }

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

    @RequestMapping(value = "/getRegister")
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
        try {
            logger.info("/user/register");
            User user = new User(0L, username, pwd, username, "", "", null, phone, "", industryManager.selectById(industryId), "", "", "/imgs/xiaohuangren.png", 0L);
            userManager.saveUser(user);
            model.addAttribute("username", username);
            model.addAttribute("pwd", pwd);
            logger.info("finished /user/register");
        } catch (Exception e) {
            if(e.toString().indexOf("user_name") > 0) {
                model.addAttribute("error", "用户名已存在");
            } else if(e.toString().indexOf("phone") > 0) {
                model.addAttribute("error", "该手机号已经注册");
            } else {
                model.addAttribute("error", "未知错误");
            }
            model.addAttribute("username", username);
            model.addAttribute("pwd", pwd);
            model.addAttribute("phone", phone);
            model.addAttribute("industryId", Long.parseLong(industryId));
            List<Industry> industryList = industryManager.selectAll();
            model.addAttribute("industry", industryList);
            return "register";
        }
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

    @RequestMapping(value = "/page/latest/trend", method = RequestMethod.POST)
    public String getLatestTrend(Model model) {
        Map<String, List<LatestTrend>> result = new HashMap<>();
        List<LatestTrend> list = new ArrayList<>();
        org.json.JSONObject jsonParam = new org.json.JSONObject();
        JSONObject headerJsonParam = new JSONObject();
        headerJsonParam.put("username", "ranhongsuiyue");
        headerJsonParam.put("password", "IGAIA666666");
        headerJsonParam.put("token", "5e5e02a4e3da0ef3607d8e78da589b9c");
        headerJsonParam.put("account_type", "1");
        jsonParam.put("header", headerJsonParam);
        JSONObject bodyJsonParam = new JSONObject();
        bodyJsonParam.put("site_id", "13260371");
        bodyJsonParam.put("metrics", "area,ip,visit_time,start_time");
        bodyJsonParam.put("method", "trend/latest/a");
        bodyJsonParam.put("max_results", "100");
        bodyJsonParam.put("area", "");
        jsonParam.put("body", bodyJsonParam);
        System.out.println(jsonParam);

        String url="https://api.baidu.com/json/tongji/v1/ReportService/getData";
        String data=HttpUtil.getJsonData(jsonParam,url);
        //返回的是一个[{}]格式的字符串时:

        JSONObject json = JSONObject.parseObject(data);
        System.out.println(json);
        JSONArray jsonArray = json.getJSONObject("body").getJSONArray("data").getJSONObject(0).getJSONObject("result").getJSONArray("items");
        JSONArray detailInfo = jsonArray.getJSONArray(0);
        JSONArray info = jsonArray.getJSONArray(1);
        int length = info.size();
        for(int i = 0; i < length; i++) {
            LatestTrend latestTrend = new LatestTrend(info.getJSONArray(i).get(0).toString(),
                    info.getJSONArray(i).get(1).toString(),
            info.getJSONArray(i).get(2).toString(),
                    detailInfo.getJSONArray(i).getJSONObject(0).getJSONObject("detail").get("os").toString(),
            detailInfo.getJSONArray(i).getJSONObject(0).getJSONObject("detail").get("visitorType").toString(),
                    detailInfo.getJSONArray(i).getJSONObject(0).getJSONObject("detail").get("resolution").toString(),
                    detailInfo.getJSONArray(i).getJSONObject(0).getJSONObject("detail").get("browser").toString(),
                    info.getJSONArray(i).get(3).toString());
            list.add(latestTrend);
        }
        DataTablesOutput<LatestTrend> output = new DataTablesOutput();
        output.setDraw(1);
        output.setRecordsTotal(length);
        output.setRecordsFiltered(length);
        output.setData(list);

        Map<String, Integer> dayVisit = StatisticsUtil.calculateDayTotal(list);
        Map<String, Integer> hourVisit = StatisticsUtil.calculateHoursTotal(list);
        model.addAttribute("latestTrendList", list);
        model.addAttribute("dayVisit", dayVisit);
        model.addAttribute("hourVisit", hourVisit );
        return "html/m-latestTrend";
    }

    @RequestMapping(value = "/getStaticSize", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<Map<String, Float>> etStaticSize() throws FileNotFoundException, IOException {
        String filePath = config.getPath();
        File file = new File(filePath);
        float fileSize = FileSizeUtil.getSize(file.listFiles()) / 1024;
        float freeSpace = file.getFreeSpace() / 1024 / 1024 / 1024;
        float usableSpace = file.getUsableSpace() / 1024 / 1024 / 1024;
        float totalSpace = file.getTotalSpace() / 1024 / 1024 / 1024;
        Map<String, Float> map = new HashMap<>();
        map.put("fileSize", fileSize);
        map.put("freeSpace", freeSpace);
        map.put("usableSpace", usableSpace);
        map.put("totalSpace", totalSpace);

        return JSONResult.success(map);
    }

    @RequestMapping(value = "/getSingleStaticSize", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<Map<String, Float>> getSingleStaticSize() throws FileNotFoundException, IOException {
        String filePath = config.getPath();
        List<String> fileList = FileSizeUtil.readfile(filePath);
        Map<String, Float> workMap = new HashMap<>();
        for(String path: fileList){
            String fileName = path.split("/")[path.split("/").length - 1];
            File file = new File(path);
            workMap.put(fileName, FileSizeUtil.getSize(file.listFiles()));
        }
        return JSONResult.success(workMap);
    }



}
