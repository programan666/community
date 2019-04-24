package com.programan.cm.web.controller;


import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.CreateType;
import com.programan.cm.db.model.Topic;
import com.programan.cm.web.manager.CreateTypeManager;
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
@RequestMapping("/createType")
public class CreateTypeController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private CreateTypeManager createTypeManager;

    @Autowired
    public void setCreateTypeManager(CreateTypeManager createTypeManager) {
        this.createTypeManager = createTypeManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<CreateType> getCreateTypeList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/CreateType/list");
        DataTablesOutput<CreateType> output = createTypeManager.findCreateType(input);
        logger.info("finished /CreateType/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<CreateType>> getCreateTypeList(){
        List<CreateType> createTypeList = createTypeManager.selectAll();
        return JSONResult.success(createTypeList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<CreateType> getCreateTypeDetail(@PathVariable String id) {
        logger.info("/CreateType/detail");
        CreateType createType = createTypeManager.selectById(id);
        logger.info("finished /CreateType/detail");
        return JSONResult.success(createType);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertTopic(@RequestParam("id") String id,
                                  @RequestParam("createTypeName") String createTypeName) {
        logger.info("/createType/save");

        try {
            CreateType createType = new CreateType(Long.parseLong(id), createTypeName);
            createTypeManager.saveCreateType(createType);
            logger.info("finished /createType/save");
        } catch (Exception e) {
            logger.info("Insert createType error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteCreateType(@PathVariable String ids) {
        logger.info("/CreateType/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                createTypeManager.deleteCreateType(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
