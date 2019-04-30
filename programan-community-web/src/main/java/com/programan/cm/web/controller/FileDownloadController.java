package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.FileDownload;
import com.programan.cm.db.model.FileType;
import com.programan.cm.db.model.Industry;
import com.programan.cm.web.manager.FileDownloadManager;
import com.programan.cm.web.manager.FileTypeManager;
import com.programan.cm.web.manager.IndustryManager;
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
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/fileDownload")
public class FileDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    private FileDownloadManager fileDownloadManager;

    private FileTypeManager fileTypeManager;

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setFileTypeManager(FileTypeManager fileTypeManager) {
        this.fileTypeManager = fileTypeManager;
    }

    @Autowired
    public void setFileDownloadManager(FileDownloadManager fileDownloadManager) {
        this.fileDownloadManager = fileDownloadManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<FileDownload> getFileDownloadList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/fileDownload/list");
        DataTablesOutput<FileDownload> output = fileDownloadManager.findFileDownload(input);
        logger.info("finished /fileDownload/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<FileDownload>> getFileDownloadList(){
        List<FileDownload> fileDownloadList = fileDownloadManager.selectAll();
        return JSONResult.success(fileDownloadList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByTypeAndKeyWord")
    public JSONResult<List<FileDownload>> getFileDownloadListByTypeAndKeyWord(@RequestParam("typeId") String typeId,
                                                                              @RequestParam("keyWord") String keyWord){
        List<FileDownload> fileDownloadList = fileDownloadManager.selectAllByTypeAndKeyWord(fileTypeManager.selectById(typeId), keyWord);
        return JSONResult.success(fileDownloadList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<FileDownload> getFileDownloadDetail(@PathVariable String id) {
        logger.info("/fileDownload/detail");
        FileDownload fileDownload = fileDownloadManager.selectById(id);
        logger.info("finished /fileDownload/detail");
        return JSONResult.success(fileDownload);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertFileDownload(@RequestParam("id") String id,
                                         @RequestParam("fileTitle") String fileTitle,
                                         @RequestParam("fileTypeId") String fileTypeId,
                                         @RequestParam("price") String price,
                                         @RequestParam("downloadNum") String downloadNum,
                                         @RequestParam("introduction") String introduction,
                                         @RequestParam("url") String url) {
        logger.info("/fileDownload/save");
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            FileDownload fileDownload = new FileDownload(Long.parseLong(id), fileTitle,
                    fileTypeManager.selectById(fileTypeId), userManager.selectByUserName(userDetails.getUsername()),
                    new Date(System.currentTimeMillis()), Long.parseLong(price), introduction, downloadNum == "" ? 0L:Long.parseLong(downloadNum), url);
            fileDownloadManager.saveFileDownload(fileDownload);
            logger.info("finished /fileDownload/save");
        } catch (Exception e) {
            logger.info("Insert fileDownload error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteFileDownload(@PathVariable String ids) {
        logger.info("/fileDownload/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                fileDownloadManager.deleteFileDownload(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
