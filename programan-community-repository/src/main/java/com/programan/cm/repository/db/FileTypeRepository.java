package com.programan.cm.repository.db;

import com.programan.cm.db.dao.FileTypeDao;
import com.programan.cm.db.dao.IndustryDao;
import com.programan.cm.db.model.FileType;
import com.programan.cm.db.model.Industry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FileTypeRepository {

    private FileTypeDao fileTypeDao;

    @Autowired
    public void setFileTypeDao(FileTypeDao fileTypeDao) {
        this.fileTypeDao = fileTypeDao;
    }

    public FileType selectById(String id){
        return fileTypeDao.selectById(Long.parseLong(id));
    }

    public FileType selectById(Long id){
        return fileTypeDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<FileType> findFileType(DataTablesInput input) {
        return fileTypeDao.findAll(input);
    }

    public List<FileType> selectAll(){
        return fileTypeDao.selectAll();
    }

    public void saveFileType(FileType fileType) {
        fileTypeDao.save(fileType);
    }

    public void deleteFileType(String id) {
        fileTypeDao.deleteById(Long.parseLong(id));
    }

}
