package com.programan.cm.web.manager;


import com.programan.cm.db.model.Role;
import com.programan.cm.repository.db.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoleManager {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role selectById(String id) {
        return roleRepository.selectById(id);
    }

    public Role selectByName(String name) {
        return roleRepository.selectByName(name);
    }

    @Transactional
    public DataTablesOutput<Role> findRole(DataTablesInput input) {
        DataTablesOutput<Role> dataTablesOutput = roleRepository.findRole(input);
        return dataTablesOutput;
    }

    public List<Role> selectAll() {
        return roleRepository.selectAll();
    }

    public void saveRole(Role role){
        if(roleRepository.selectById(role.getId()) != null) {
            //修改
            roleRepository.saveRole(role);
        } else {
            //增加
            roleRepository.saveRole(role);
        }
    }

    public void deleteRole(String id) {
        roleRepository.deleteRole(id);
    }

}
