package com.programan.cm.web.manager;

import com.programan.cm.db.model.FileType;
import com.programan.cm.db.model.Industry;
import com.programan.cm.repository.db.FileTypeRepository;
import com.programan.cm.repository.db.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FileTypeManager {

    private FileTypeRepository fileTypeRepository;

    @Autowired
    public void setFileTypeRepository(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;
    }

    public FileType selectById(String id) {
        return fileTypeRepository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<FileType> findIndustry(DataTablesInput input) {
        DataTablesOutput<FileType> dataTablesOutput = fileTypeRepository.findFileType(input);
        return dataTablesOutput;
    }

    public List<FileType> selectAll() {
        return fileTypeRepository.selectAll();
    }

    public void saveFileType(FileType fileType){
        if(fileTypeRepository.selectById(fileType.getId()) != null) {
            //修改
            fileTypeRepository.saveFileType(fileType);
        } else {
            //增加
            fileTypeRepository.saveFileType(fileType);
        }
    }

    public void deleteFileType(String id) {
        fileTypeRepository.deleteFileType(id);
    }

}
