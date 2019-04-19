package com.programan.cm.repository.db;

import com.programan.cm.db.dao.UserDao;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public User insertUser(User user) {
        user.setPwd(passwordEncoder.encode(user.getPwd().trim()));
        return userDao.save(user);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public DataTablesOutput<User> findUser(DataTablesInput input) {
        return userDao.findAll(input);
    }

    public void deleteUser(String id) {
        userDao.deleteById(Long.parseLong(id));
    }

    public User selectById(String id) {
        return userDao.selectById(Long.parseLong(id));
    }

    public User selectById(Long id) {
        return userDao.selectById(id);
    }

}
