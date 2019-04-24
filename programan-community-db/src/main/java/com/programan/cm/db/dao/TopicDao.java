package com.programan.cm.db.dao;

import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.Topic;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDao extends DataTablesRepository<Topic, Long> {

    @Query(value = "select t.name from Topic t where id = ?1")
    String selectNameById(Long id);

    @Query(value = "select t from Topic t where name = ?1")
    Topic selectByName(String name);

    @Query(value = "select t from Topic t where id = ?1")
    Topic selectById(Long id);

    @Query(value = "select t from Topic t")
    List<Topic> selectAll();

}
