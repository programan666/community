package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserCourse;
import com.programan.cm.web.manager.CourseManager;
import com.programan.cm.web.manager.RoleManager;
import com.programan.cm.web.manager.UserCourseManager;
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
import java.util.List;

@Controller
@RequestMapping("/userCourse")
public class UserCourseController {

    private static final Logger logger = LoggerFactory.getLogger(UserCourseController.class);

    private UserCourseManager userCourseManager;

    private UserManager userManager;

    private CourseManager courseManager;

    @Autowired
    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setUserCourseManager(UserCourseManager userCourseManager) {
        this.userCourseManager = userCourseManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<UserCourse> getUserCourseList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/userCourse/list");
        DataTablesOutput<UserCourse> output = userCourseManager.findUserCourse(input);
        logger.info("finished /userCourse/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<UserCourse>> getUserCourseList(){
        List<UserCourse> userCourseList = userCourseManager.selectAll();
        return JSONResult.success(userCourseList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByUser")
    public JSONResult<List<UserCourse>> getMyUserCourseList(){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userManager.selectByUserName(userDetails.getUsername());
            List<UserCourse> userCourseList = userCourseManager.selectByUser(user);
            return JSONResult.success(userCourseList);
        } catch (Exception e) {
            return JSONResult.failed("error");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getByBoth/{courseId}", method = RequestMethod.GET)
    public JSONResult getUserCourseByBoth(@PathVariable String courseId){
        UserCourse userCourse = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userManager.selectByUserName(userDetails.getUsername());
            Course course = courseManager.selectById(courseId);
            userCourse = userCourseManager.selectByBoth(user, course);
            if(userCourse == null) {
                return JSONResult.failed("error", "null", null);
            }
        } catch (Exception e) {
            return JSONResult.failed("error", e.getMessage(), "noUser");
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<UserCourse> getUserCourseDetail(@PathVariable String id) {
        logger.info("/userCourse/detail");
        UserCourse userCourse = userCourseManager.selectById(id);
        logger.info("finished /userCourse/detail");
        return JSONResult.success(userCourse);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertUserCourse(@RequestParam("id") String id,
                                       @RequestParam("userId") String userId,
                                       @RequestParam("courseId") String courseId) {
        logger.info("/userCourse/save");
        try {
            User user;
            if(userId.equals("0")) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                user = userManager.selectByUserName(userDetails.getUsername());
            } else {
                user = userManager.selectById(userId);
            }

            Course course = courseManager.selectById(courseId);
            UserCourse userCourse = new UserCourse(Long.parseLong(id), user, course);
            user.setPnum(user.getPnum() - course.getPrice());
            if(user.getPnum() < 0) {
                return JSONResult.failed("error", "P豆余额不足", null);
            }
            userCourseManager.saveUserCourse(userCourse);
            userManager.saveUser(user);          //购买课程扣除相应P豆
            logger.info("finished /userCourse/save");
        } catch (Exception e) {
            logger.info("Insert userCourse error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUserCourse(@PathVariable String ids) {
        logger.info("/userCourse/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                userCourseManager.deleteUserCourse(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBoth", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUserCourse(@RequestParam("userId") String userId,
                                               @RequestParam("courseId") String course_id) {
        logger.info("/deleteByBoth");
        try {
            UserCourse userCourse = userCourseManager.selectByBoth(userManager.selectById(userId), courseManager.selectById(course_id));
            userCourseManager.deleteUserCourse(userCourse.getId().toString());
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
