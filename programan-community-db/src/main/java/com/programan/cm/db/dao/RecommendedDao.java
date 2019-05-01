package com.programan.cm.db.dao;

import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.Recommended;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedDao extends DataTablesRepository<Recommended, Long> {

    @Query(value = "select r from Recommended r where r.id = ?1")
    Recommended selectById(Long id);

    @Query(value = "select r from Recommended r")
    List<Recommended> selectAll();

}
