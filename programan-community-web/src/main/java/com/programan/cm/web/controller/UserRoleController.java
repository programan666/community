package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.UserRole;
import com.programan.cm.web.manager.RoleManager;
import com.programan.cm.web.manager.UserManager;
import com.programan.cm.web.manager.UserRoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/userrole")
public class UserRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRoleManager userRoleManager;

    private UserManager userManager;

    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setUserRoleManager(UserRoleManager userRoleManager) {
        this.userRoleManager = userRoleManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tablelist")
    public DataTablesOutput<UserRole> getRoleList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/userrole/tablelist");
        DataTablesOutput<UserRole> output = userRoleManager.findUserRole(input);
        logger.info("finished /userrole/tablelist");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<UserRole> getRoleDetail(@PathVariable String id) {
        logger.info("/userrole/detail");
        UserRole userRole = userRoleManager.selectById(id);
        logger.info("finished /userrole/detail");
        return JSONResult.success(userRole);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteRole(@PathVariable String ids) {
        logger.info("/role/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                userRoleManager.deleteUserRole(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertRole(@RequestParam("id") String id,
                                 @RequestParam("userName") String userName,
                                 @RequestParam("roleId") String roleId) {
        logger.info("/userrole/save");

        try {
            UserRole userRole = new UserRole(Long.parseLong(id), userManager.selectByUserName(userName), roleManager.selectById(roleId));
            userRoleManager.saveUserRole(userRole);
            logger.info("finished /userrole/save");
        } catch (Exception e) {
            logger.info("Insert userrole error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

}
