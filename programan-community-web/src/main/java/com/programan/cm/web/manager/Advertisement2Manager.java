package com.programan.cm.web.manager;


import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Advertisement2;
import com.programan.cm.repository.db.Advertisement2Repository;
import com.programan.cm.repository.db.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Advertisement2Manager {

    private Advertisement2Repository advertisement2Repository;

    @Autowired
    public void setAdvertisement2Repository(Advertisement2Repository advertisement2Repository) {
        this.advertisement2Repository = advertisement2Repository;
    }

    public Advertisement2 selectById(String id) {
        return advertisement2Repository.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Advertisement2> findAdvertisement2(DataTablesInput input) {
        DataTablesOutput<Advertisement2> dataTablesOutput = advertisement2Repository.findAdvertisement2(input);
        return dataTablesOutput;
    }

    public List<Advertisement2> selectAll() {
        return advertisement2Repository.selectAll();
    }

    public void saveAdvertisement2(Advertisement2 advertisement2){
        if(advertisement2Repository.selectById(advertisement2.getId()) != null) {
            //修改
            advertisement2Repository.saveAdvertisement2(advertisement2);
        } else {
            //增加
            advertisement2Repository.saveAdvertisement2(advertisement2);
        }
    }

    public void deleteAdvertisement2(String id) {
        advertisement2Repository.deleteAdvertisement2(id);
    }

    public List<Advertisement2> selectByLocation(String location) {
        return advertisement2Repository.selectByLocation(location);
    }

}
