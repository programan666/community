package com.programan.cm.web.manager;

import com.programan.cm.db.model.FileDownload;
import com.programan.cm.db.model.FileType;
import com.programan.cm.repository.db.FileDownloadRepository;
import com.programan.cm.repository.db.FileTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FileDownloadManager {

    private FileDownloadRepository fileDownloadRepository;

    @Autowired
    public void setFileDownloadRepository(FileDownloadRepository fileDownloadRepository) {
        this.fileDownloadRepository = fileDownloadRepository;
    }

    public FileDownload selectById(String id) {
        return fileDownloadRepository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<FileDownload> findFileDownload(DataTablesInput input) {
        DataTablesOutput<FileDownload> dataTablesOutput = fileDownloadRepository.findFileDownload(input);
        return dataTablesOutput;
    }

    public List<FileDownload> selectAll() {
        return fileDownloadRepository.selectAll();
    }

    public List<FileDownload> selectAllOrderByDate() {
        return fileDownloadRepository.selectAllOrderByDate();
    }

    public List<FileDownload> selectAllOrderByNum() {
        return fileDownloadRepository.selectAllOrderByNum();
    }

    public void saveFileDownload(FileDownload fileDownload){
        FileDownload fileDownloadOld = fileDownloadRepository.selectById(fileDownload.getId());
        if(fileDownloadOld != null) {
            //修改
            fileDownloadRepository.saveFileDownload(fileDownload);
        } else {
            //增加
            fileDownload.setDownloadNum(0L);
            fileDownloadRepository.saveFileDownload(fileDownload);
        }
    }

    public void deleteFileDownload(String id) {
        fileDownloadRepository.deleteFileDownload(id);
    }

    public List<FileDownload> selectAllByTypeAndKeyWord(FileType fileType, String title) {
        return fileDownloadRepository.selectAllByTypeAndKeyWord(fileType, title);
    }

}
