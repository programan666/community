package com.programan.cm.db.dao;

import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserRole;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRoleDao extends DataTablesRepository<UserRole, Long> {

    @Query(value = "select u from UserRole u where user = ?1")
    List<UserRole> selectByUserId(User user);

    @Query(value = "select r from UserRole r where id = ?1")
    UserRole selectById(Long id);

}
