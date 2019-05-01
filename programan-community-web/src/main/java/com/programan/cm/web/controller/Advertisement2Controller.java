package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Advertisement2;
import com.programan.cm.web.manager.Advertisement2Manager;
import com.programan.cm.web.manager.AdvertisementManager;
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
@RequestMapping("/advertisement2")
public class Advertisement2Controller {

    private static final Logger logger = LoggerFactory.getLogger(Advertisement2Controller.class);

    private Advertisement2Manager advertisement2Manager;

    @Autowired
    public void setAdvertisement2Manager(Advertisement2Manager advertisement2Manager) {
        this.advertisement2Manager = advertisement2Manager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<Advertisement2> getAdvertisement2List(@Valid @RequestBody DataTablesInput input) {
        logger.info("/advertisement2/list");
        DataTablesOutput<Advertisement2> output = advertisement2Manager.findAdvertisement2(input);
        logger.info("finished /advertisement2/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Advertisement2>> getAdvertisement2List(){
        List<Advertisement2> advertisement2List = advertisement2Manager.selectAll();
        return JSONResult.success(advertisement2List);
    }

    @ResponseBody
    @RequestMapping(value = "/listByLocation/{location}")
    public JSONResult<List<Advertisement2>> getAdvertisement2ListByLocation(@PathVariable String location){
        List<Advertisement2> advertisement2List = advertisement2Manager.selectByLocation(location);
        return JSONResult.success(advertisement2List);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Advertisement2> getAdvertisement2Detail(@PathVariable String id) {
        logger.info("/advertisement2/detail");
        Advertisement2 advertisement2 = advertisement2Manager.selectById(id);
        logger.info("finished /advertisement2/detail");
        return JSONResult.success(advertisement2);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertAdvertisement2(@RequestParam("id") String id,
                                           @RequestParam("imgUrl") String imgUrl,
                                           @RequestParam("textH") String textH,
                                           @RequestParam("textP") String textP,
                                           @RequestParam("location") String location) {
        logger.info("/advertisement2/save");

        try {
            Advertisement2 advertisement2 = new Advertisement2(Long.parseLong(id), imgUrl, textH, textP,
                    new Date(System.currentTimeMillis()), location);
            advertisement2Manager.saveAdvertisement2(advertisement2);
            logger.info("finished /advertisement2/save");
        } catch (Exception e) {
            logger.info("Insert advertisement2 error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteAdvertisement2(@PathVariable String ids) {
        logger.info("/advertisement2/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                advertisement2Manager.deleteAdvertisement2(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
