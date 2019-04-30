package com.programan.cm.db.dao;

import com.programan.cm.db.model.Course;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserCourse;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseDao extends DataTablesRepository<UserCourse, Long> {

    @Query(value = "select uc from UserCourse uc where uc.user = ?1")
    List<UserCourse> selectByUser(User user);

    @Query(value = "select uc from UserCourse uc where uc.course = ?1")
    List<UserCourse> selectByCourse(Course course);

    @Query(value = "select uc from UserCourse uc where uc.user = ?1 and uc.course = ?2")
    UserCourse selectByBoth(User user, Course course);

    @Query(value = "select uc from UserCourse uc where uc.id = ?1")
    UserCourse selectById(Long id);

    @Query(value = "select r from UserCourse r")
    List<UserCourse> selectAll();

}
