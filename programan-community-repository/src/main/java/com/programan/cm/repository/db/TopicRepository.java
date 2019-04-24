package com.programan.cm.repository.db;

import com.programan.cm.db.dao.TopicDao;
import com.programan.cm.db.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class TopicRepository {

    private TopicDao topicDao;

    @Autowired
    public void setTopicDao(TopicDao topicDao){
        this.topicDao = topicDao;
    }


    public Topic selectById(String id){
        return topicDao.selectById(Long.parseLong(id));
    }

    public Topic selectById(Long id){
        return topicDao.selectById(id);
    }

    @Transactional
    public DataTablesOutput<Topic> findRole(DataTablesInput input) {
        return topicDao.findAll(input);
    }

    public List<Topic> selectAll(){
        return topicDao.selectAll();
    }

    public void saveRole(Topic topic) {
        topicDao.save(topic);
    }

    public void deleteRole(String id) {
        topicDao.deleteById(Long.parseLong(id));
    }

    public Topic selectByName(String name) {
        return topicDao.selectByName(name);
    }

}
