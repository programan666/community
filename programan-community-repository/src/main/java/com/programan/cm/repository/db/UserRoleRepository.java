package com.programan.cm.repository.db;

import com.programan.cm.db.dao.UserRoleDao;
import com.programan.cm.db.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRoleRepository {

    private UserRoleDao userRoleDao;

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public void saveUserRole(UserRole userRole) {
        userRoleDao.save(userRole);
    }

    @Transactional
    public DataTablesOutput<UserRole> findUserRole(DataTablesInput input) {
        return userRoleDao.findAll(input);
    }

    public UserRole selectById(String id) {
        return userRoleDao.selectById(Long.parseLong(id));
    }

    public void deleteUserRole(String id) {
        userRoleDao.deleteById(Long.parseLong(id));
    }

    public UserRole selectById(Long id) {
        return userRoleDao.selectById(id);
    }


}
