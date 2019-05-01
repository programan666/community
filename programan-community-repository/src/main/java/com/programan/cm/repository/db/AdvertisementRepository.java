package com.programan.cm.repository.db;

import com.programan.cm.db.dao.AdvertisementDao;
import com.programan.cm.db.dao.CourseDao;
import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AdvertisementRepository {

    private AdvertisementDao advertisementDao;

    @Autowired
    public void setAdvertisementDao(AdvertisementDao advertisementDao) {
        this.advertisementDao = advertisementDao;
    }

    public Advertisement selectById(String id){
        return advertisementDao.selectById(Long.parseLong(id));
    }

    public Advertisement selectById(Long id){
        return advertisementDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Advertisement> findAdvertisement(DataTablesInput input) {
        return advertisementDao.findAll(input);
    }

    public List<Advertisement> selectAll(){
        return advertisementDao.selectAll();
    }

    public void saveAdvertisement(Advertisement advertisement) {
        advertisementDao.save(advertisement);
    }

    public void deleteAdvertisement(String id) {
        advertisementDao.deleteById(Long.parseLong(id));
    }

    public List<Advertisement> selectByLocation(String location) {
        return advertisementDao.selectByLocation(location);
    }

}
