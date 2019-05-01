package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Course;
import com.programan.cm.web.manager.AdvertisementManager;
import com.programan.cm.web.manager.CourseManager;
import com.programan.cm.web.manager.TopicManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    private AdvertisementManager advertisementManager;

    @Autowired
    public void setAdvertisementManager(AdvertisementManager advertisementManager) {
        this.advertisementManager = advertisementManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<Advertisement> getAdvertisementList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/advertisement/list");
        DataTablesOutput<Advertisement> output = advertisementManager.findAdvertisement(input);
        logger.info("finished /advertisement/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Advertisement>> getAdvertisementList(){
        List<Advertisement> advertisementList = advertisementManager.selectAll();
        return JSONResult.success(advertisementList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByLocation/{location}")
    public JSONResult<List<Advertisement>> getAdvertisementListByLocation(@PathVariable String location){
        List<Advertisement> advertisementList = advertisementManager.selectByLocation(location);
        return JSONResult.success(advertisementList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Advertisement> getAdvertisementDetail(@PathVariable String id) {
        logger.info("/advertisement/detail");
        Advertisement advertisement = advertisementManager.selectById(id);
        logger.info("finished /advertisement/detail");
        return JSONResult.success(advertisement);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertAdvertisement(@RequestParam("id") String id,
                                          @RequestParam("imagePath") String imagePath,
                                          @RequestParam("url") String url,
                                          @RequestParam("slideText") String slideText,
                                          @RequestParam("orders") String order,
                                          @RequestParam("location") String location) {
        logger.info("/advertisement/save");

        try {
            Advertisement advertisement = new Advertisement(Long.parseLong(id), imagePath, url, slideText,
                    new Date(System.currentTimeMillis()), Integer.parseInt(order), location);
            advertisementManager.saveAdvertisement(advertisement);
            logger.info("finished /advertisement/save");
        } catch (Exception e) {
            logger.info("Insert advertisement error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteAdvertisement(@PathVariable String ids) {
        logger.info("/advertisement/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                advertisementManager.deleteAdvertisement(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
