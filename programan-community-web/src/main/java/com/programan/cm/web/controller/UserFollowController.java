package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import com.programan.cm.web.manager.RoleManager;
import com.programan.cm.web.manager.UserFollowManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userFollow")
public class UserFollowController {

    private static final Logger logger = LoggerFactory.getLogger(UserFollowController.class);

    private UserFollowManager userFollowManager;

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setUserFollowManager(UserFollowManager userFollowManager) {
        this.userFollowManager = userFollowManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tableList")
    public DataTablesOutput<UserFollow> getUserFollowList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/UserFollow/list");
        DataTablesOutput<UserFollow> output = userFollowManager.findUserFollow(input);
        logger.info("finished /UserFollow/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserFollowCount")
    public JSONResult<Map<String, Integer>> getUserFollowCount(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userManager.selectByUserName(userDetails.getUsername());
        int focusNum = userFollowManager.selectCountByFans(user);
        int fansNum = userFollowManager.selectCountByFocus(user);
        Map<String, Integer> result = new HashMap<>();
        result.put("focusNum", focusNum);
        result.put("fansNum", fansNum);
        return JSONResult.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listByFans/{userId}")
    public JSONResult<List<UserFollow>> getUserFollowListByFans(@PathVariable String userId){
        List<UserFollow> userFollowList;
        if(userId.equals("0")) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userManager.selectByUserName(userDetails.getUsername());
            userFollowList = userFollowManager.selectByFans(user);
        } else {
            userFollowList = userFollowManager.selectByFans(userManager.selectById(userId));
        }
        return JSONResult.success(userFollowList);
    }

    @ResponseBody
    @RequestMapping(value = "/listByFocus/{userId}")
    public JSONResult<List<UserFollow>> getUserFollowListByFocus(@PathVariable String userId){
        List<UserFollow> userFollowList;
        if(userId.equals("0")) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userManager.selectByUserName(userDetails.getUsername());
            userFollowList = userFollowManager.selectByFocus(user);
        } else {
            userFollowList = userFollowManager.selectByFocus(userManager.selectById(userId));
        }
        return JSONResult.success(userFollowList);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertUserFollow(@RequestParam("id") String id,
                                       @RequestParam("focusId") String focusId) {
        logger.info("/userFollow/save");

        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserFollow userFollow = new UserFollow(Long.parseLong(id), userManager.selectById(focusId), userManager.selectByUserName(userDetails.getUsername()));
            userFollowManager.saveUserFollow(userFollow);
            logger.info("finished /userFollow/save");
        } catch (Exception e) {
            logger.info("Insert userFollow error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUserFollow(@PathVariable String ids) {
        logger.info("/UserFollow/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                userFollowManager.deleteUserFollow(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBoth/{focusId}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteUserFollowByBoth(@PathVariable String focusId) {
        logger.info("/deleteByBoth", focusId);
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserFollow userFollow = userFollowManager.selectByBoth(userManager.selectById(focusId), userManager.selectByUserName(userDetails.getUsername()));
            userFollowManager.deleteUserFollow(userFollow.getId().toString());
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
