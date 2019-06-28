package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.ArticleComment;
import com.programan.cm.db.model.Role;
import com.programan.cm.web.manager.ArticleCommentManager;
import com.programan.cm.web.manager.ArticleManager;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("articleComment")
public class ArticleCommentController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private ArticleCommentManager articleCommentManager;

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
    public void setArticleCommentManager(ArticleCommentManager articleCommentManager) {
        this.articleCommentManager = articleCommentManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<ArticleComment> getArticleCommentList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/articleComment/list");
        DataTablesOutput<ArticleComment> output = articleCommentManager.findArticleComment(input);
        logger.info("finished /articleComment/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteArticleComment(@PathVariable String ids) {
        logger.info("/articleComment/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                articleCommentManager.deleteArticleComment(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertArticleComment(@RequestParam("id") String id,
                                           @RequestParam("commentInfo") String commentInfo,
                                           @RequestParam("articleId") String articleId) {
        logger.info("/articleComment/save");

        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ArticleComment articleComment = new ArticleComment(Long.parseLong(id), userManager.selectByUserName(userDetails.getUsername()), commentInfo,articleManager.selectById(articleId), new Date());
            articleCommentManager.saveArticleComment(articleComment);
            logger.info("finished /articleComment/save");
        } catch (Exception e) {
            logger.info("Insert ArticleComment error:", e);
            return JSONResult.failed("error", "请先登录", null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
    public JSONResult<List<ArticleComment>> getArticleCommentList(@PathVariable String id){
        List<ArticleComment> articleCommentList = articleCommentManager.selectByArticle(articleManager.selectById(id));
        return JSONResult.success(articleCommentList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByLoginUser", method = RequestMethod.GET)
    public JSONResult<List<ArticleComment>> getArticleCommentListByLoginUser(){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<ArticleComment> articleCommentList = articleCommentManager.selectByUser(userManager.selectByUserName(userDetails.getUsername()));
            logger.info("finished /listByLoginUser/save");
            return JSONResult.success(articleCommentList);
        } catch (Exception e) {
            logger.info("listByLoginUser error:", e);
            return null;
        }
    }

}
