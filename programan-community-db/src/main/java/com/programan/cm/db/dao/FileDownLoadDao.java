package com.programan.cm.db.dao;

import com.programan.cm.db.model.FileDownload;
import com.programan.cm.db.model.FileType;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDownLoadDao extends DataTablesRepository<FileDownload, Long> {

    @Query(value = "select fd from FileDownload fd where fd.id = ?1")
    FileDownload selectById(Long id);

    @Query(value = "select fd from FileDownload fd")
    List<FileDownload> selectAll();

    @Query(value = "select fd from FileDownload fd order by fd.uploadDate desc")
    List<FileDownload> selectAllOrderByDate();

    @Query(value = "select fd from FileDownload fd order by fd.downloadNum desc")
    List<FileDownload> selectAllOrderByNum();

    @Query(value = "select fd from FileDownload fd where fd.fileType = ?1 and fd.title like CONCAT('%',?2,'%')")
    List<FileDownload> selectAllByTypeAndKeyWord(FileType fileType, String title);

}
