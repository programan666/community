package com.programan.cm.repository.db;

import com.programan.cm.db.dao.IndustryDao;
import com.programan.cm.db.dao.RecommendedDao;
import com.programan.cm.db.model.Industry;
import com.programan.cm.db.model.Recommended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RecommendedRepository {

    private RecommendedDao recommendedDao;

    @Autowired
    public void setRecommendedDao(RecommendedDao recommendedDao) {
        this.recommendedDao = recommendedDao;
    }

    public Recommended selectById(String id){
        return recommendedDao.selectById(Long.parseLong(id));
    }

    public Recommended selectById(Long id){
        return recommendedDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Recommended> findRecommended(DataTablesInput input) {
        return recommendedDao.findAll(input);
    }

    public List<Recommended> selectAll(){
        return recommendedDao.selectAll();
    }

    public void saveRecommended(Recommended recommended) {
        recommendedDao.save(recommended);
    }

    public void deleteRecommended(String id) {
        recommendedDao.deleteById(Long.parseLong(id));
    }

}
