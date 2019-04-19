package com.programan.cm.web.manager;

import com.programan.cm.db.model.UserRole;
import com.programan.cm.repository.db.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserRoleManager {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void saveUserRole(UserRole userRole) {
        userRoleRepository.saveUserRole(userRole);
    }

    @Transactional
    public DataTablesOutput<UserRole> findUserRole(DataTablesInput input) {
        DataTablesOutput<UserRole> dataTablesOutput = userRoleRepository.findUserRole(input);
        return dataTablesOutput;
    }

    public UserRole selectById(String id) {
        return userRoleRepository.selectById(id);
    }

    public void deleteUserRole(String id) {
        userRoleRepository.deleteUserRole(id);
    }

}
