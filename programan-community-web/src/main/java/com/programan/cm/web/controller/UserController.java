package com.programan.cm.web.controller;

//import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.dao.UserDao;
import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.User;
//import com.programan.cm.repository.manager.UserManager;
import com.programan.cm.web.manager.IndustryManager;
import com.programan.cm.web.manager.UserFollowManager;
import com.programan.cm.web.manager.UserManager;
import com.programan.cm.web.manager.UserRoleManager;
import com.programan.cm.web.security.CmUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserManager userManager;

    private IndustryManager industryManager;

    private CmUserDetailService cmUserDetailService;

    private UserFollowManager userFollowManager;

    @Autowired
    public void setUserFollowManager(UserFollowManager userFollowManager) {
        this.userFollowManager = userFollowManager;
    }

    @Autowired
    public void setIndustryManager(IndustryManager industryManager) {
        this.industryManager = industryManager;
    }

    @Autowired
    public void setCmUserDetailService(CmUserDetailService cmUserDetailService) {
        this.cmUserDetailService = cmUserDetailService;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(String username, String password) {
//        UserDetails userDetails = cmUserDetailService.loadUserByUsername(username);
//        cmUserDetailService.loadUserByUsername(username);
//        return "";
//    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public DataTablesOutput<User> getUserList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/user/list");
        DataTablesOutput<User> output = userManager.findUser(input);
        logger.info("finished /user/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUser(@PathVariable String ids) {
        logger.info("/user/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                userManager.deleteUser(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<User> getUserDetailById(@PathVariable String id) {
        logger.info("/user/detail");
        User user = userManager.selectById(id);
        logger.info("finished /user/detail");
        return JSONResult.success(user);
    }

    @ResponseBody
    @RequestMapping(value = "/updatePNum/{pNum}", method = RequestMethod.GET)
    public JSONResult updatePNum(@PathVariable String pNum) {
        User user = null;
        try {
            logger.info("/updatePNum");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userManager.selectByUserName(userDetails.getUsername());
            user.setPnum(user.getPnum() + Long.parseLong(pNum));
            if(user.getPnum() < 0) {
                return JSONResult.failed("error", "P豆余额不足", null);
            }
            userManager.saveUser(user);
            logger.info("finished /updatePNum");
        } catch (NumberFormatException e) {
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/usdetail/{username}", method = RequestMethod.GET)
    public JSONResult<User> getUserDetailByUserName(@PathVariable String username) {
        logger.info("/user/detail");
        User user = userManager.selectByUserName(username);
        logger.info("finished /user/detail");
        return JSONResult.success(user);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertUser(@RequestParam("id") String id,
                                         @RequestParam("userName") String userName,
                                         @RequestParam("pwd") String pwd,
                                         @RequestParam("roleName") String roleName,
                                         @RequestParam("realName") String realName,
                                         @RequestParam("sex") String sex,
                                         @RequestParam("birthday") String sbirthday,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("area") String area,
                                         @RequestParam("industryId") String industryId,
                                         @RequestParam("jobName") String jobName,
                                         @RequestParam("introduction") String introduction,
                                         @RequestParam("headImgUrl") String headImgUrl,
                                         @RequestParam("pNum") String pNum) {
        logger.info("/user/insert");

        try {
            Industry industry = industryManager.selectById(industryId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sbirthday.equals("") ?  null : sdf.parse(sbirthday);
            User user = new User(Long.parseLong(id),userName, pwd, roleName, realName, sex,
                    birthday == null ? null : new java.sql.Date(birthday.getTime()), phone, area, industry, jobName, introduction,
                    headImgUrl.equals("") ? "/imgs/xiaohuangren.png" : headImgUrl, Long.parseLong(pNum));
            userManager.saveUser(user);
            logger.info("finished /user/list");
        } catch (Exception e) {
            logger.info("Insert user error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONResult updateUser(@RequestParam("userName") String userName,
                                 @RequestParam("roleName") String roleName,
                                 @RequestParam("realName") String realName,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("birthday") String sbirthday,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("area") String area,
                                 @RequestParam("industryId") String industryId,
                                 @RequestParam("jobName") String jobName,
                                 @RequestParam("introduction") String introduction) {
        logger.info("/user/update");

        try {
            Industry industry = industryManager.selectById(industryId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse(sbirthday);
            User oldUser = userManager.selectByUserName(userName);
            User user = new User(oldUser.getId(),userName, oldUser.getPwd(), roleName, realName, sex,
                    new java.sql.Date(birthday.getTime()), phone, area, industry, jobName, introduction,
                    oldUser.getHeadImgUrl(), oldUser.getPnum());
            userManager.saveUser(user);
            logger.info("finished /user/update");
        } catch (Exception e) {
            logger.info("Update user error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/updateHeaderImg", method = RequestMethod.POST)
    public JSONResult updateUser(@RequestParam("headImgUrl") String headImgUrl) {
        logger.info("/user/update");
        User user = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userManager.selectByUserName(userDetails.getUsername());
            user.setHeadImgUrl(headImgUrl);
            userManager.saveUser(user);
            logger.info("finished /user/update");
        } catch (Exception e) {
            logger.info("Update user error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", user.getHeadImgUrl());
    }

    @ResponseBody
    @RequestMapping(value = "/updatePNum", method = RequestMethod.POST)
    public JSONResult updateUserPNum(@RequestParam("pNum") String pNum) {
        logger.info("/user/updatePNum");
        User user = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userManager.selectByUserName(userDetails.getUsername());
            user.setPnum(user.getPnum() - Long.parseLong(pNum));
            userManager.saveUser(user);
            logger.info("finished /user/updatePNum");
        } catch (Exception e) {
            logger.info("Update updatePNum error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", user.getHeadImgUrl());
    }

    @ResponseBody
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public JSONResult updateUserPwd(@RequestParam("newPwd") String newPwd,
                                    @RequestParam("phoneNumber") String phoneNumber) {
        logger.info("/user/update");
        User user = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userManager.selectByUserName(userDetails.getUsername());
            if(phoneNumber.equals(user.getPhone())) {
                user.setPwd(passwordEncoder.encode(newPwd.trim()));
                userManager.saveUser(user);
            } else {
                return JSONResult.failed("error", "手机号码不正确", null);
            }
            logger.info("finished /user/update");
        } catch (Exception e) {
            logger.info("Update user error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", user.getHeadImgUrl());
    }

    @ResponseBody
    @RequestMapping(value = "/updatePhoneNumber", method = RequestMethod.POST)
    public JSONResult updateUserPhoneNumber(@RequestParam("oldPhoneNumber") String oldPhoneNumber,
                                    @RequestParam("newPhoneNumber") String newPhoneNumber) {
        logger.info("/user/update");
        User user = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userManager.selectByUserName(userDetails.getUsername());
            if(oldPhoneNumber.equals(user.getPhone())) {
                user.setPhone(newPhoneNumber);
                userManager.saveUser(user);
            } else {
                return JSONResult.failed("error", "原手机号码不正确", null);
            }
            logger.info("finished /user/update");
        } catch (Exception e) {
            logger.info("Update user error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", user.getHeadImgUrl());
    }

    @ResponseBody
    @RequestMapping(value = "/forgetPwd", method = RequestMethod.POST)
    public JSONResult forgetPwd(@RequestParam("username") String username,
                                @RequestParam("newPwd") String newPwd,
                                @RequestParam("phoneNumber") String phoneNumber) {
        logger.info("/user/update");
        try {
            User user = userManager.selectByUserName(username);
            if(user.getPhone().equals(phoneNumber)) {
                user.setPwd(passwordEncoder.encode(newPwd.trim()));
                userManager.saveUser(user);
            } else {
                return JSONResult.failed("error", "手机号码不正确", null);
            }
            logger.info("finished /user/update");
        } catch (Exception e) {
            logger.info("Update user error:", e);
            return JSONResult.failed("error", "用户不存在", null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/getNowPNum", method = RequestMethod.GET)
    public JSONResult<Long> getNowPNum() {
        logger.info("/getNowPNum");
        Long pNum = 0L;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userManager.selectByUserName(userDetails.getUsername());
            pNum = user.getPnum();
        } catch (Exception e) {
            return JSONResult.success(-1L);
        }
        return JSONResult.success(pNum);
    }



}
