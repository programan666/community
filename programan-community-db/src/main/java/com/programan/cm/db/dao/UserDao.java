package com.programan.cm.db.dao;

import com.programan.cm.db.model.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends DataTablesRepository<User, Long> {

    @Query(value = "select u from User u where userName=?1")
    User selectByUserName(String userName);

    @Query(value = "select u from User u where id=?1")
    User selectById(Long id);

}
