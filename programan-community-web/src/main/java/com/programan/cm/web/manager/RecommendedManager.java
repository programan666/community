package com.programan.cm.web.manager;

import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.Recommended;
import com.programan.cm.repository.db.IndustryRepository;
import com.programan.cm.repository.db.RecommendedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RecommendedManager {

    private RecommendedRepository recommendedRepository;

    @Autowired
    public void setRecommendedRepository(RecommendedRepository recommendedRepository) {
        this.recommendedRepository = recommendedRepository;
    }

    public Recommended selectById(String id) {
        return recommendedRepository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Recommended> findRecommended(DataTablesInput input) {
        DataTablesOutput<Recommended> dataTablesOutput = recommendedRepository.findRecommended(input);
        return dataTablesOutput;
    }

    public List<Recommended> selectAll() {
        return recommendedRepository.selectAll();
    }

    public void saveRecommended(Recommended recommended){
        if(recommendedRepository.selectById(recommended.getId()) != null) {
            //修改
            recommendedRepository.saveRecommended(recommended);
        } else {
            //增加
            recommendedRepository.saveRecommended(recommended);
        }
    }

    public void deleteRecommended(String id) {
        recommendedRepository.deleteRecommended(id);
    }

}
