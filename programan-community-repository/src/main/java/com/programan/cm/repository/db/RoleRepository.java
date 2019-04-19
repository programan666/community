package com.programan.cm.repository.db;

import com.programan.cm.db.dao.RoleDao;
import com.programan.cm.db.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class RoleRepository {

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role selectById(String id){
        return roleDao.selectById(Long.parseLong(id));
    }

    public Role selectById(Long id){
        return roleDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Role> findRole(DataTablesInput input) {
        return roleDao.findAll(input);
    }

    public List<Role> selectAll(){
        return roleDao.selectAll();
    }

    public void saveRole(Role role) {
        roleDao.save(role);
    }

    public void deleteRole(String id) {
        roleDao.deleteById(Long.parseLong(id));
    }

    public Role selectByName(String name) {
        return roleDao.selectByName(name);
    }

}
