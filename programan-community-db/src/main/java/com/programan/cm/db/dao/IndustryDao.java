package com.programan.cm.db.dao;

import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.Role;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IndustryDao extends DataTablesRepository<Industry, Long>,CrudRepository<Industry, Long> {

    @Query(value = "select i from Industry i where id = ?1")
    Industry selectById(Long id);

    @Query(value = "select i from Industry i")
    List<Industry> selectAll();

}
