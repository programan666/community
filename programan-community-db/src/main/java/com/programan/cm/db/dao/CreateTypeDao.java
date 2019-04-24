package com.programan.cm.db.dao;

import com.programan.cm.db.model.CreateType;
import com.programan.cm.db.model.Topic;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreateTypeDao extends DataTablesRepository<CreateType, Long> {

    @Query(value = "select c.name from CreateType c where id = ?1")
    String selectNameById(Long id);

    @Query(value = "select c from CreateType c where name = ?1")
    CreateType selectByName(String name);

    @Query(value = "select c from CreateType c where id = ?1")
    CreateType selectById(Long id);

    @Query(value = "select c from CreateType c")
    List<CreateType> selectAll();


}
