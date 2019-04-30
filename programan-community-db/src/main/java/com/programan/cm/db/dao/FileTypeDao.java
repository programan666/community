package com.programan.cm.db.dao;

import com.programan.cm.db.model.FileType;
import com.programan.cm.db.model.Industry;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileTypeDao extends DataTablesRepository<FileType, Long>,CrudRepository<FileType, Long> {

    @Query(value = "select i from FileType i where i.id = ?1")
    FileType selectById(Long id);

    @Query(value = "select i from FileType i")
    List<FileType> selectAll();

}
