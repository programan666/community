package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.Recommended;
import com.programan.cm.web.manager.ArticleManager;
import com.programan.cm.web.manager.IndustryManager;
import com.programan.cm.web.manager.RecommendedManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/recommended")
public class RecommendedController {

    private static final Logger logger = LoggerFactory.getLogger(RecommendedController.class);

    private RecommendedManager recommendedManager;

    private ArticleManager articleManager;

    @Autowired
    public void setArticleManager(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @Autowired
    public void setRecommendedManager(RecommendedManager recommendedManager) {
        this.recommendedManager = recommendedManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<Recommended> getRecommendedList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/Recommended/list");
        DataTablesOutput<Recommended> output = recommendedManager.findRecommended(input);
        logger.info("finished /Recommended/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Recommended>> getRecommendedList(){
        List<Recommended> recommendedList = recommendedManager.selectAll();
        return JSONResult.success(recommendedList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Recommended> getRecommendedDetail(@PathVariable String id) {
        logger.info("/Recommended/detail");
        Recommended recommended = recommendedManager.selectById(id);
        logger.info("finished /Recommended/detail");
        return JSONResult.success(recommended);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertRecommended(@RequestParam("id") String id,
                                        @RequestParam("imgUrl") String imgUrl,
                                        @RequestParam("articleId") String articleId) {
        logger.info("/Recommended/save");

        try {
            Recommended recommended = new Recommended(Long.parseLong(id), imgUrl, articleManager.selectById(articleId));
            recommendedManager.saveRecommended(recommended);
            logger.info("finished /Recommended/save");
        } catch (Exception e) {
            logger.info("Insert Recommended error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUser(@PathVariable String ids) {
        logger.info("/Recommended/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                recommendedManager.deleteRecommended(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
