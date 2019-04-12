package com.programan.cm.db.dao;

import com.programan.cm.db.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {

    @Query(value = "select r.name from Role r where id = ?1")
    String selectNameById(Long userId);

}
