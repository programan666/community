package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.ArticleLike;
import com.programan.cm.db.model.UserFollow;
import com.programan.cm.web.manager.ArticleLikeManager;
import com.programan.cm.web.manager.ArticleManager;
import com.programan.cm.web.manager.UserFollowManager;
import com.programan.cm.web.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/articleLike")
public class ArticleLikeController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleLikeController.class);

    private ArticleLikeManager articleLikeManager;

    private UserManager userManager;

    private ArticleManager articleManager;

    @Autowired
    public void setArticleManager(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setArticleLikeManager(ArticleLikeManager articleLikeManager) {
        this.articleLikeManager = articleLikeManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<ArticleLike> getArticleLikeList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/ArticleLike/list");
        DataTablesOutput<ArticleLike> output = articleLikeManager.findArticleLike(input);
        logger.info("finished /ArticleLike/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/listByArticle/{articleId}")
    public JSONResult<List<ArticleLike>> getArticleLikeListByArticle(@PathVariable String articleId){
        List<ArticleLike> articleLikeList = articleLikeManager.selectByArticle(articleManager.selectById(articleId));
        return JSONResult.success(articleLikeList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByUser/{userId}")
    public JSONResult<List<ArticleLike>> getArticleLikeListByUser(@PathVariable String userId){
        List<ArticleLike> articleLikeList = articleLikeManager.selectByUser(userManager.selectById(userId));
        return JSONResult.success(articleLikeList);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertArticleLike(@RequestParam("id") String id,
                                        @RequestParam("articleId") String articleId) {
        logger.info("/articleLike/save");

        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ArticleLike articleLike = new ArticleLike(Long.parseLong(id), articleManager.selectById(articleId), userManager.selectByUserName(userDetails.getUsername()));
            articleLikeManager.saveArticleLike(articleLike);
            logger.info("finished /articleLike/save");
        } catch (Exception e) {
            logger.info("Insert articleLike error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteArticleLike(@PathVariable String ids) {
        logger.info("/ArticleLike/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                articleLikeManager.deleteArticleLike(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBoth/{articleId}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteArticleLikeByBoth(@PathVariable String articleId) {
        logger.info("/deleteByBoth", articleId);
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ArticleLike articleLike = articleLikeManager.selectByBoth(articleManager.selectById(articleId), userManager.selectByUserName(userDetails.getUsername()));
            articleLikeManager.deleteArticleLike(articleLike.getId().toString());
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
