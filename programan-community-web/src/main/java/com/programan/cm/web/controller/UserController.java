package com.programan.cm.web.controller;

import com.programan.cm.common.utils.JSONResult;
import com.programan.cm.db.dao.UserDao;
import com.programan.cm.db.model.User;
//import com.programan.cm.repository.manager.UserManager;
import com.programan.cm.web.manager.UserManager;
import com.programan.cm.web.security.CmUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserManager userManager;

    private CmUserDetailService cmUserDetailService;

    @Autowired
    public void setCmUserDetailService(CmUserDetailService cmUserDetailService) {
        this.cmUserDetailService = cmUserDetailService;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password) {
        UserDetails userDetails = cmUserDetailService.loadUserByUsername(username);
        if(userDetails == null){
            return "redirect:/login.html";
        }
        if(userDetails.getPassword().equals(password)) {
            return "redirect:/index.html";
        } else {
            return "redirect:/login.html";
        }
    }

}
