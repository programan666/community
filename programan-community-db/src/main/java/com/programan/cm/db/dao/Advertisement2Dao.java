package com.programan.cm.db.dao;

import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Advertisement2;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Advertisement2Dao extends DataTablesRepository<Advertisement2, Long> {

    @Query(value = "select c from Advertisement2 c where c.id = ?1")
    Advertisement2 selectById(Long id);

    @Query(value = "select c from Advertisement2 c")
    List<Advertisement2> selectAll();

    @Query(value = "select c from Advertisement2 c where c.location = ?1")
    List<Advertisement2> selectByLocation(String location);

}
