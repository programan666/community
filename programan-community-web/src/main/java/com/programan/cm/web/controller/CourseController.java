package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.web.manager.CourseManager;
import com.programan.cm.web.manager.RoleManager;
import com.programan.cm.web.manager.TopicManager;
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
@RequestMapping("/course")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private CourseManager courseManager;

    private TopicManager topicManager;

    @Autowired
    public void setTopicManager(TopicManager topicManager) {
        this.topicManager = topicManager;
    }

    @Autowired
    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<Course> getCourseList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/course/list");
        DataTablesOutput<Course> output = courseManager.findCourse(input);
        logger.info("finished /course/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Course>> getCourseList(){
        List<Course> courseList = courseManager.selectAll();
        return JSONResult.success(courseList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByTopic/{id}")
    public JSONResult<List<Course>> getCourseListByTopic(@PathVariable String id){
        List<Course> courseList = courseManager.selectByTopic(topicManager.selectById(id));
        return JSONResult.success(courseList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Course> getCourseDetail(@PathVariable String id) {
        logger.info("/course/detail");
        Course course = courseManager.selectById(id);
        logger.info("finished /course/detail");
        return JSONResult.success(course);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertCourse(@RequestParam("id") String id,
                                   @RequestParam("courseTitle") String courseTitle,
                                   @RequestParam("teacherName") String teacherName,
                                   @RequestParam("teacherJob") String teacherJob,
                                   @RequestParam("introduction") String introduction,
                                   @RequestParam("coursePrice") String coursePrice,
                                   @RequestParam("courseImgUrl") String courseImgUrl,
                                   @RequestParam("courseVideoUrl") String courseVideoUrl,
                                   @RequestParam("topicId") String topicId) {
        logger.info("/course/save");

        try {
            Course course = new Course(Long.parseLong(id), courseTitle, teacherName, teacherJob, introduction,
                    Long.parseLong(coursePrice), courseImgUrl, courseVideoUrl, topicManager.selectById(topicId));
            courseManager.saveCourse(course);
            logger.info("finished /course/save");
        } catch (Exception e) {
            logger.info("Insert course error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteCourse(@PathVariable String ids) {
        logger.info("/course/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                courseManager.deleteCourse(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
