package com.programan.cm.web.manager;


import com.programan.cm.db.model.CreateType;
import com.programan.cm.db.model.Role;
import com.programan.cm.repository.db.CreateTypeRepository;
import com.programan.cm.repository.db.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreateTypeManager {

    private CreateTypeRepository createTypeRepository;

    @Autowired
    public void setCreateTypeRepository(CreateTypeRepository createTypeRepository) {
        this.createTypeRepository = createTypeRepository;
    }

    public CreateType selectById(String id) {
        return createTypeRepository.selectById(id);
    }

    public CreateType selectByName(String name) {
        return createTypeRepository.selectByName(name);
    }

    @Transactional
    public DataTablesOutput<CreateType> findCreateType(DataTablesInput input) {
        DataTablesOutput<CreateType> dataTablesOutput = createTypeRepository.findCreateType(input);
        return dataTablesOutput;
    }

    public List<CreateType> selectAll() {
        return createTypeRepository.selectAll();
    }

    public void saveCreateType(CreateType createType){
        if(createTypeRepository.selectById(createType.getId()) != null) {
            //修改
            createTypeRepository.saveCreateType(createType);
        } else {
            //增加
            createTypeRepository.saveCreateType(createType);
        }
    }

    public void deleteCreateType(String id) {
        createTypeRepository.deleteCreateType(id);
    }


}
