package com.programan.cm.db.dao;

import com.programan.cm.db.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    @Query(value = "select u from User u where userName=?1")
    User selectByUserName(String userName);

}
