package com.programan.cm.web.manager;


import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserCourse;
import com.programan.cm.repository.db.RoleRepository;
import com.programan.cm.repository.db.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserCourseManager {

    private UserCourseRepository userCourseRepository;

    @Autowired
    public void setUserCourseRepository(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    public UserCourse selectById(String id) {
        return userCourseRepository.selectById(id);
    }

    public List<UserCourse> selectByUser(User user) {
        return userCourseRepository.selectByUser(user);
    }

    public List<UserCourse> selectByCourse(Course course) {
        return userCourseRepository.selectByCourse(course);
    }

    public UserCourse selectByBoth(User user, Course course) {
        return userCourseRepository.selectByBoth(user, course);
    }

    @Transactional
    public DataTablesOutput<UserCourse> findUserCourse(DataTablesInput input) {
        DataTablesOutput<UserCourse> dataTablesOutput = userCourseRepository.findUserCourse(input);
        return dataTablesOutput;
    }

    public List<UserCourse> selectAll() {
        return userCourseRepository.selectAll();
    }

    public void saveUserCourse(UserCourse userCourse){
        if(userCourseRepository.selectById(userCourse.getId().toString()) != null) {
            //修改
            userCourseRepository.saveUserCourse(userCourse);
        } else {
            //增加
            userCourseRepository.saveUserCourse(userCourse);
        }
    }

    public void deleteUserCourse(String id) {
        userCourseRepository.deleteUserCourse(id);
    }

}
