package com.programan.cm.web.manager;


import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Topic;
import com.programan.cm.repository.db.AdvertisementRepository;
import com.programan.cm.repository.db.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AdvertisementManager {

    private AdvertisementRepository advertisementRepository;

    @Autowired
    public void setAdvertisementRepository(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public Advertisement selectById(String id) {
        return advertisementRepository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Advertisement> findAdvertisement(DataTablesInput input) {
        DataTablesOutput<Advertisement> dataTablesOutput = advertisementRepository.findAdvertisement(input);
        return dataTablesOutput;
    }

    public List<Advertisement> selectAll() {
        return advertisementRepository.selectAll();
    }

    public void saveAdvertisement(Advertisement advertisement){
        if(advertisementRepository.selectById(advertisement.getId()) != null) {
            //修改
            advertisementRepository.saveAdvertisement(advertisement);
        } else {
            //增加
            advertisementRepository.saveAdvertisement(advertisement);
        }
    }

    public void deleteAdvertisement(String id) {
        advertisementRepository.deleteAdvertisement(id);
    }

    public List<Advertisement> selectByLocation(String location) {
        return advertisementRepository.selectByLocation(location);
    }

}
