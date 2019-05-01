package com.programan.cm.repository.db;

import com.programan.cm.db.dao.Advertisement2Dao;
import com.programan.cm.db.dao.AdvertisementDao;
import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Advertisement2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class Advertisement2Repository {

    private Advertisement2Dao advertisement2Dao;

    @Autowired
    public void setAdvertisement2Dao(Advertisement2Dao advertisement2Dao) {
        this.advertisement2Dao = advertisement2Dao;
    }

    public Advertisement2 selectById(String id){
        return advertisement2Dao.selectById(Long.parseLong(id));
    }

    public Advertisement2 selectById(Long id){
        return advertisement2Dao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Advertisement2> findAdvertisement2(DataTablesInput input) {
        return advertisement2Dao.findAll(input);
    }

    public List<Advertisement2> selectAll(){
        return advertisement2Dao.selectAll();
    }

    public void saveAdvertisement2(Advertisement2 advertisement2) {
        advertisement2Dao.save(advertisement2);
    }

    public void deleteAdvertisement2(String id) {
        advertisement2Dao.deleteById(Long.parseLong(id));
    }

    public List<Advertisement2> selectByLocation(String location) {
        return advertisement2Dao.selectByLocation(location);
    }

}
