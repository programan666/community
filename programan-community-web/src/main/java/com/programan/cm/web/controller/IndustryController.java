package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.User;
import com.programan.cm.web.manager.IndustryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/industry")
public class IndustryController {

    private static final Logger logger = LoggerFactory.getLogger(IndustryController.class);

    private IndustryManager industryManager;

    @Autowired
    public void setIndustryManager(IndustryManager industryManager) {
        this.industryManager = industryManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tablelist")
    public DataTablesOutput<Industry> getIndustryList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/industry/list");
        DataTablesOutput<Industry> output = industryManager.findIndustry(input);
        logger.info("finished /industry/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Industry>> getIndustryList(){
        List<Industry> industryList = industryManager.selectAll();
        return JSONResult.success(industryList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Industry> getUserDetail(@PathVariable String id) {
        logger.info("/industry/detail");
        Industry industry = industryManager.selectById(id);
        logger.info("finished /industry/detail");
        return JSONResult.success(industry);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertUser(@RequestParam("id") String id,
                                 @RequestParam("industryName") String industryName) {
        logger.info("/industry/save");

        try {
            Industry industry = new Industry(Long.parseLong(id), industryName);
            industryManager.saveIndustry(industry);
            logger.info("finished /industry/save");
        } catch (Exception e) {
            logger.info("Insert industry error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUser(@PathVariable String ids) {
        logger.info("/industry/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                industryManager.deleteIndustry(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
