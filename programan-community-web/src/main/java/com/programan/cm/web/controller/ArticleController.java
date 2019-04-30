package com.programan.cm.web.controller;


import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleLike;
import com.programan.cm.db.model.CreateType;
import com.programan.cm.db.model.Topic;
import com.programan.cm.web.manager.ArticleManager;
import com.programan.cm.web.manager.CreateTypeManager;
import com.programan.cm.web.manager.TopicManager;
import com.programan.cm.web.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private ArticleManager articleManager;

    private TopicManager topicManager;

    private CreateTypeManager createTypeManager;

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
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
    public void setArticleManager(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<Article> getTopicList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/article/list");
        DataTablesOutput<Article> output = articleManager.findArticle(input);
        logger.info("finished /article/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Article>> getArticleList(){
        List<Article> articleList = articleManager.selectAll();
        return JSONResult.success(articleList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByTitle")
    public JSONResult<List<Article>> getArticleListByTitle(@RequestParam("titleKey") String titleKey){
        List<Article> articleList = articleManager.selectAllByTitle(titleKey);
        return JSONResult.success(articleList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByTopic/{topicId}/{fromIndex}/{pageNum}", method = RequestMethod.GET)
    public JSONResult<List<Article>> getArticleList(@PathVariable String topicId,
                                                    @PathVariable String fromIndex,
                                                    @PathVariable String pageNum){
        List<Article> articleList = articleManager.selectByTopicPage(topicManager.selectById(topicId),
                Integer.parseInt(fromIndex), Integer.parseInt(pageNum));
        if(articleList.size() == 0) {
            return JSONResult.failed("error", null, null);
        }
        return JSONResult.success(articleList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Article> getArticleDetail(@PathVariable String id) {
        logger.info("/article/detail");
        Article article = articleManager.selectById(id);
        logger.info("finished /article/detail");
        return JSONResult.success(article);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertArticle(@RequestParam("id") String id,
                                  @RequestParam("articleTitle") String articleTitle,
                                  @RequestParam("articleBody") String articleBody,
                                  @RequestParam("createTypeId") String createTypeId,
                                  @RequestParam("topicId") String topicId,
                                  @RequestParam("username") String username) {
        logger.info("/article/save");

        try {
            Article articleOld = articleManager.selectById(id);
            Article article = null;
            if (articleOld != null) {
                article = new Article(Long.parseLong(id), articleTitle, articleBody, articleOld.getReadNum(), new Date(),
                        topicManager.selectById(topicId), createTypeManager.selectById(createTypeId),
                        userManager.selectByUserName(username));
            } else {
                article = new Article(Long.parseLong(id), articleTitle, articleBody, 0L, new Date(),
                        topicManager.selectById(topicId), createTypeManager.selectById(createTypeId),
                        userManager.selectByUserName(username));
            }
            articleManager.saveArticle(article);
            logger.info("finished /article/save");
        } catch (Exception e) {
            logger.info("Insert article error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/mSave", method = RequestMethod.POST)
    public JSONResult updateArticle(@RequestParam("id") String id,
                                    @RequestParam("articleTitle") String articleTitle,
                                    @RequestParam("readNum") String readNum,
                                    @RequestParam("articleBody") String articleBody,
                                    @RequestParam("createTypeId") String createTypeId,
                                    @RequestParam("topicId") String topicId) {
        logger.info("/article/mSave");
        try {
            Article article = articleManager.selectById(id);
            article.setTitle(articleTitle);
            article.setReadNum(Long.parseLong(readNum));
            article.setBody(articleBody);
            article.setCreateType(createTypeManager.selectById(createTypeId));
            article.setTopic(topicManager.selectById(topicId));
            articleManager.saveArticle(article);
            logger.info("finished /article/mSave");
        } catch (Exception e) {
            logger.info("Update article error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteArticle(@PathVariable String ids) {
        logger.info("/article/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                articleManager.deleteArticle(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/userArticleList/{username}", method = RequestMethod.GET)
    public JSONResult<List<Article>> getUserArticleList(@PathVariable String username) {
        logger.info("/userArticleList/"+ username);
        List<Article> article = articleManager.selectAllByUser(userManager.selectByUserName(username));
        logger.info("finished /userArticleList/"+ username);
        return JSONResult.success(article);
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String getSelectedArticleList(@RequestParam("createTypeId") String createTypeId,
                                         @RequestParam("topicId") String topicId,
                                         @RequestParam("articleTitle") String articleTitle, Model model) {
        logger.info("/article/select");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Article> articleList = articleManager.selectAll(createTypeId, topicId, articleTitle, userManager.selectByUserName(userDetails.getUsername()).getId().toString());
        List<CreateType> createTypeList = createTypeManager.selectAll();
        List<Topic> topicList = topicManager.selectAll();
        model.addAttribute("articleList", articleList);
        model.addAttribute("createTypeList", createTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("createTypeId", Long.parseLong(createTypeId));
        model.addAttribute("topicId", Long.parseLong(topicId));
        model.addAttribute("articleTitle", articleTitle);
        logger.info("finished /article/select");
        return "html/articleManage";
    }

}
