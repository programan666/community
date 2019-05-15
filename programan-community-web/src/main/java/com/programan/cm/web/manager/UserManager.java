package com.programan.cm.web.manager;

import com.programan.cm.db.dao.UserDao;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserRole;
import com.programan.cm.repository.db.RoleRepository;
import com.programan.cm.repository.db.UserRepository;
import com.programan.cm.repository.db.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.stream.Collectors;

@Component
public class UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User selectByUserName(String userName) {
        return userRepository.selectByUserName(userName);
    }

    public User selectById(String id) {
        return userRepository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<User> findUser(DataTablesInput input) {
        DataTablesOutput<User> dataTablesOutput = userRepository.findUser(input);
        return dataTablesOutput;
    }

    public void deleteUser(String id){
        userRepository.deleteUser(id);
    }

//    public void insertUser(User user) {
//        userRepository.insertUser(user);
//    }

    public void saveUser(User user){
        if(userRepository.selectById(user.getId()) != null) {
            //修改
            user.setPwd(user.getPwd().length() > 20 ? user.getPwd() : passwordEncoder.encode(user.getPwd().trim()));
            user.setCreateDate(userRepository.selectById(user.getId()).getCreateDate());
            userRepository.saveUser(user);
        } else {
            //增加
            user.setCreateDate(new Date(System.currentTimeMillis()));
            User flushUser = userRepository.insertUser(user);
            UserRole userRole = new UserRole(0L, flushUser, roleRepository.selectByName("USER"));
            userRoleRepository.saveUserRole(userRole);
        }
    }

}
