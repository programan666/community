package com.programan.cm.db.dao;

import com.programan.cm.db.model.Advertisement;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Topic;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementDao extends DataTablesRepository<Advertisement, Long> {

    @Query(value = "select c from Advertisement c where c.id = ?1")
    Advertisement selectById(Long id);

    @Query(value = "select c from Advertisement c")
    List<Advertisement> selectAll();

}
