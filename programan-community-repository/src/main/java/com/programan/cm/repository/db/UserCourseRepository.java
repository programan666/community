package com.programan.cm.repository.db;

import com.programan.cm.db.dao.RoleDao;
import com.programan.cm.db.dao.UserCourseDao;
import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserCourseRepository {

    private UserCourseDao userCourseDao;

    @Autowired
    public void setUserCourseDao(UserCourseDao userCourseDao) {
        this.userCourseDao = userCourseDao;
    }

    public UserCourse selectById(String id){
        return userCourseDao.selectById(Long.parseLong(id));
    }

    public List<UserCourse> selectByUser(User user){
        return userCourseDao.selectByUser(user);
    }

    public List<UserCourse> selectByCourse(Course course){
        return userCourseDao.selectByCourse(course);
    }

    public UserCourse selectByBoth(User user, Course course){
        return userCourseDao.selectByBoth(user, course);
    }

    @Transactional
    public DataTablesOutput<UserCourse> findUserCourse(DataTablesInput input) {
        return userCourseDao.findAll(input);
    }

    public List<UserCourse> selectAll(){
        return userCourseDao.selectAll();
    }

    public void saveUserCourse(UserCourse userCourse) {
        userCourseDao.save(userCourse);
    }

    public void deleteUserCourse(String id) {
        userCourseDao.deleteById(Long.parseLong(id));
    }

}
