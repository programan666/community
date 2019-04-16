package com.programan.cm.repository.db;

import com.programan.cm.db.dao.IndustryDao;
import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class IndustryRepository {

    private IndustryDao industryDao;

    @Autowired
    public void setIndustryDao(IndustryDao industryDao) {
        this.industryDao = industryDao;
    }

    public Industry selectById(String id){
        return industryDao.selectById(Long.parseLong(id));
    }

    public Industry selectById(Long id){
        return industryDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Industry> findIndustry(DataTablesInput input) {
        return industryDao.findAll(input);
    }

    public List<Industry> selectAll(){
        return industryDao.selectAll();
    }

    public void saveIndustry(Industry industry) {
        industryDao.save(industry);
    }

    public void deleteIndustry(String id) {
        industryDao.deleteById(Long.parseLong(id));
    }

}
