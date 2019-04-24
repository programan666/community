package com.programan.cm.repository.db;

import com.programan.cm.db.dao.CreateTypeDao;
import com.programan.cm.db.dao.TopicDao;
import com.programan.cm.db.model.CreateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class CreateTypeRepository {

    private CreateTypeDao createTypeDao;

    @Autowired
    public void setCreateTypeDao(CreateTypeDao createTypeDao){
        this.createTypeDao = createTypeDao;
    }

    public CreateType selectById(String id){
        return createTypeDao.selectById(Long.parseLong(id));
    }

    public CreateType selectById(Long id){
        return createTypeDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<CreateType> findCreateType(DataTablesInput input) {
        return createTypeDao.findAll(input);
    }

    public List<CreateType> selectAll(){
        return createTypeDao.selectAll();
    }

    public void saveCreateType(CreateType createType) {
        createTypeDao.save(createType);
    }

    public void deleteCreateType(String id) {
        createTypeDao.deleteById(Long.parseLong(id));
    }

    public CreateType selectByName(String name) {
        return createTypeDao.selectByName(name);
    }


}
