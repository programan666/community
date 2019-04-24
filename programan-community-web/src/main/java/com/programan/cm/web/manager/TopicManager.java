package com.programan.cm.web.manager;


import com.programan.cm.db.model.Topic;
import com.programan.cm.repository.db.CreateTypeRepository;
import com.programan.cm.repository.db.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class TopicManager {

    private TopicRepository topicRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic selectById(String id) {
        return topicRepository.selectById(id);
    }

    public Topic selectByName(String name) {
        return topicRepository.selectByName(name);
    }

    @Transactional
    public DataTablesOutput<Topic> findTopic(DataTablesInput input) {
        DataTablesOutput<Topic> dataTablesOutput = topicRepository.findRole(input);
        return dataTablesOutput;
    }

    public List<Topic> selectAll() {
        return topicRepository.selectAll();
    }

    public void saveTopic(Topic topic){
        if(topicRepository.selectById(topic.getId()) != null) {
            //修改
            topicRepository.saveRole(topic);
        } else {
            //增加
            topicRepository.saveRole(topic);
        }
    }

    public void deleteTopic(String id) {
        topicRepository.deleteRole(id);
    }


}
