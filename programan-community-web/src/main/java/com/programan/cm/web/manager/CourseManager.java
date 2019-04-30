package com.programan.cm.web.manager;


import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.Topic;
import com.programan.cm.repository.db.CourseRepository;
import com.programan.cm.repository.db.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CourseManager {

    private CourseRepository courseRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course selectById(String id) {
        return courseRepository.selectById(id);
    }

    public List<Course> selectByTopic(Topic topic) {
        return courseRepository.selectByTopic(topic);
    }

    @Transactional
    public DataTablesOutput<Course> findCourse(DataTablesInput input) {
        DataTablesOutput<Course> dataTablesOutput = courseRepository.findCourse(input);
        return dataTablesOutput;
    }

    public List<Course> selectAll() {
        return courseRepository.selectAll();
    }

    public void saveCourse(Course course){
        if(courseRepository.selectById(course.getId()) != null) {
            //修改
            courseRepository.saveCourse(course);
        } else {
            //增加
            courseRepository.saveCourse(course);
        }
    }

    public void deleteCourse(String id) {
        courseRepository.deleteCourse(id);
    }

}
