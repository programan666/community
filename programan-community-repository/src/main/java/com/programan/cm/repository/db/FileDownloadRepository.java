package com.programan.cm.repository.db;

import com.programan.cm.db.dao.FileDownLoadDao;
import com.programan.cm.db.dao.FileTypeDao;
import com.programan.cm.db.model.FileDownload;
import com.programan.cm.db.model.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FileDownloadRepository {

    private FileDownLoadDao fileDownLoadDao;

    @Autowired
    public void setFileDownLoadDao(FileDownLoadDao fileDownLoadDao) {
        this.fileDownLoadDao = fileDownLoadDao;
    }

    public FileDownload selectById(String id){
        return fileDownLoadDao.selectById(Long.parseLong(id));
    }

    public FileDownload selectById(Long id){
        return fileDownLoadDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<FileDownload> findFileDownload(DataTablesInput input) {
        return fileDownLoadDao.findAll(input);
    }

    public List<FileDownload> selectAll(){
        return fileDownLoadDao.selectAll();
    }

    public List<FileDownload> selectAllOrderByDate(){
        return fileDownLoadDao.selectAllOrderByDate();
    }

    public List<FileDownload> selectAllOrderByNum(){
        return fileDownLoadDao.selectAllOrderByNum();
    }

    public void saveFileDownload(FileDownload fileDownload) {
        fileDownLoadDao.save(fileDownload);
    }

    public void deleteFileDownload(String id) {
        fileDownLoadDao.deleteById(Long.parseLong(id));
    }

    public List<FileDownload> selectAllByTypeAndKeyWord(FileType fileType, String title) {
        return fileDownLoadDao.selectAllByTypeAndKeyWord(fileType, title);
    }

}
