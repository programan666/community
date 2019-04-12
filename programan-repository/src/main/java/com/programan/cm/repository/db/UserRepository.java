package com.programan.cm.repository.db;

import com.programan.cm.db.dao.UserDao;
import com.programan.cm.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User selectByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }

    public void insertUser(User user) {
        user.setPwd(passwordEncoder.encode(user.getPwd().trim()));
        userDao.save(user);
    }

}
