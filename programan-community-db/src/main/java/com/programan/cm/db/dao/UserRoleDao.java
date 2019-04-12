package com.programan.cm.db.dao;

import com.programan.cm.db.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRoleDao extends CrudRepository<UserRole, Long> {

    @Query(value = "select u from UserRole u where userId = ?1")
    List<UserRole> selectByUserId(Long userId);

}
