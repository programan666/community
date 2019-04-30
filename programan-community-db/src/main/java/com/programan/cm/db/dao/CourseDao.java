package com.programan.cm.db.dao;

import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.Topic;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao extends DataTablesRepository<Course, Long> {

    @Query(value = "select c from Course c where c.topic = ?1")
    List<Course> selectByTopic(Topic topic);

    @Query(value = "select c from Course c where c.id = ?1")
    Course selectById(Long id);

    @Query(value = "select c from Course c")
    List<Course> selectAll();

}
