package com.programan.cm.web.manager;

import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.User;
import com.programan.cm.repository.db.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class IndustryManager {

    private IndustryRepository industryRepository;

    @Autowired
    public void setIndustryRepository(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    public Industry selectById(String id) {
        return industryRepository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Industry> findIndustry(DataTablesInput input) {
        DataTablesOutput<Industry> dataTablesOutput = industryRepository.findIndustry(input);
        return dataTablesOutput;
    }

    public List<Industry> selectAll() {
        return industryRepository.selectAll();
    }

    public void saveIndustry(Industry industry){
        if(industryRepository.selectById(industry.getId()) != null) {
            //修改
            industryRepository.saveIndustry(industry);
        } else {
            //增加
            industryRepository.saveIndustry(industry);
        }
    }

    public void deleteIndustry(String id) {
        industryRepository.deleteIndustry(id);
    }

}
