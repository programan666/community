package com.programan.cm.db.dao;

import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.Role;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends DataTablesRepository<Role, Long> {

    @Query(value = "select r.name from Role r where id = ?1")
    String selectNameById(Long id);

    @Query(value = "select r from Role r where name = ?1")
    Role selectByName(String name);

    @Query(value = "select r from Role r where id = ?1")
    Role selectById(Long id);

    @Query(value = "select r from Role r")
    List<Role> selectAll();

}
