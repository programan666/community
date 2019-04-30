package com.programan.cm.repository.db;

import com.programan.cm.db.dao.CourseDao;
import com.programan.cm.db.dao.RoleDao;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CourseRepository {

    private CourseDao courseDao;

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Course selectById(String id){
        return courseDao.selectById(Long.parseLong(id));
    }

    public Course selectById(Long id){
        return courseDao.selectById(id);
    }

    public List<Course> selectByTopic(Topic topic){
        return courseDao.selectByTopic(topic);
    }

    @Transactional
    public DataTablesOutput<Course> findCourse(DataTablesInput input) {
        return courseDao.findAll(input);
    }

    public List<Course> selectAll(){
        return courseDao.selectAll();
    }

    public void saveCourse(Course course) {
        courseDao.save(course);
    }

    public void deleteCourse(String id) {
        courseDao.deleteById(Long.parseLong(id));
    }

}
