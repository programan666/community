package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.model.Role;
import com.programan.cm.web.manager.RoleManager;
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
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @ResponseBody
    @RequestMapping(value = "/tablelist")
    public DataTablesOutput<Role> getRoleList(@Valid @RequestBody DataTablesInput input) {
        logger.info("/role/list");
        DataTablesOutput<Role> output = roleManager.findRole(input);
        logger.info("finished /role/list");
        return output;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONResult<List<Role>> getRoleList(){
        List<Role> roleList = roleManager.selectAll();
        return JSONResult.success(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public JSONResult<Role> getRoleDetail(@PathVariable String id) {
        logger.info("/role/detail");
        Role role = roleManager.selectById(id);
        logger.info("finished /role/detail");
        return JSONResult.success(role);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult insertRole(@RequestParam("id") String id,
                                 @RequestParam("roleName") String roleName,
                                 @RequestParam("roleDescribe") String roleDescribe) {
        logger.info("/role/save");

        try {
            Role role = new Role(Long.parseLong(id), roleName, roleDescribe);
            roleManager.saveRole(role);
            logger.info("finished /role/save");
        } catch (Exception e) {
            logger.info("Insert role error:", e);
            return JSONResult.failed("error", e.getMessage(), null);
        }
        return JSONResult.success("ok", "success", null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.POST, consumes = "application/json")
    public JSONResult<String> deleteRole(@PathVariable String ids) {
        logger.info("/role/delete/{}", ids);
        try {
            for (String id : ids.split(",")) {
                roleManager.deleteRole(id);
            }
        } catch (Exception e) {
            return JSONResult.success("error");
        }
        return JSONResult.success("success");
    }

}
