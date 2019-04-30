package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.FileType;
import com.programan.cm.db.model.Industry;
import com.programan.cm.web.manager.FileTypeManager;
import com.programan.cm.web.manager.IndustryManager;
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
@RequestMapping("/fileType")
public class FileTypeController {

    private static final Logger logger = LoggerFactory.getLogger(FileTypeController.class);

    private FileTypeManager fileTypeManager;

    @Autowired
    public void setFileTypeManager(FileTypeManager fileTypeManager) {
        this.fileTypeManager = fileTypeManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<FileType> getFileTypeList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/fileType/list");
        DataTablesOutput<FileType> output = fileTypeManager.findIndustry(input);
        logger.info("finished /fileType/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<FileType>> getFileTypeList(){
        List<FileType> industryList = fileTypeManager.selectAll();
        return JSONResult.success(industryList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<FileType> getFileTypeDetail(@PathVariable String id) {
        logger.info("/fileType/detail");
        FileType fileType = fileTypeManager.selectById(id);
        logger.info("finished /fileType/detail");
        return JSONResult.success(fileType);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertFileType(@RequestParam("id") String id,
                                     @RequestParam("fileTypeName") String fileTypeName,
                                     @RequestParam("imgUrl") String imgUrl) {
        logger.info("/fileType/save");

        try {
            FileType fileType = new FileType(Long.parseLong(id), fileTypeName, imgUrl);
            fileTypeManager.saveFileType(fileType);
            logger.info("finished /fileType/save");
        } catch (Exception e) {
            logger.info("Insert fileType error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteFileType(@PathVariable String ids) {
        logger.info("/fileType/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                fileTypeManager.deleteFileType(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
