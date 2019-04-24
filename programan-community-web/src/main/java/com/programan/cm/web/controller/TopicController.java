package com.programan.cm.web.controller;


import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.Topic;
import com.programan.cm.web.manager.CreateTypeManager;
import com.programan.cm.web.manager.TopicManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private TopicManager topicManager;

    @Autowired
    public void setTopicManager(TopicManager topicManager) {
        this.topicManager = topicManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<Topic> getTopicList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/topic/list");
        DataTablesOutput<Topic> output = topicManager.findTopic(input);
        logger.info("finished /topic/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Topic>> getTopicList(){
        List<Topic> topicList = topicManager.selectAll();
        return JSONResult.success(topicList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Topic> getTOpicDetail(@PathVariable String id) {
        logger.info("/topic/detail");
        Topic topic = topicManager.selectById(id);
        logger.info("finished /topic/detail");
        return JSONResult.success(topic);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertTopic(@RequestParam("id") String id,
                                 @RequestParam("topicName") String topicName) {
        logger.info("/topic/save");

        try {
            Topic topic = new Topic(Long.parseLong(id), topicName);
            topicManager.saveTopic(topic);
            logger.info("finished /topic/save");
        } catch (Exception e) {
            logger.info("Insert topic error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteTopic(@PathVariable String ids) {
        logger.info("/topic/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                topicManager.deleteTopic(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
