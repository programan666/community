package com.programan.cm.repository.db;

import com.programan.cm.db.dao.RoleDao;
import com.programan.cm.db.dao.UserFollowDao;
import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserFollowRepository {

    private UserFollowDao userFollowDao;

    @Autowired
    public void setUserFollowDao(UserFollowDao userFollowDao) {
        this.userFollowDao = userFollowDao;
    }

    public List<UserFollow> selectByFocus(User user){
        return userFollowDao.selectByFocus(user);
    }

    public List<UserFollow> selectByFans(User user){
        return userFollowDao.selectByFans(user);
    }

    public Integer selectCountByFocus(User user){
        return userFollowDao.selectCountByFocus(user);
    }

    public Integer selectCountByFans(User user){
        return userFollowDao.selectCountByFans(user);
    }

    @Transactional
    public DataTablesOutput<UserFollow> findUserFollow(DataTablesInput input) {
        return userFollowDao.findAll(input);
    }

    public void saveUserFollow(UserFollow userFollow) {
        userFollowDao.save(userFollow);
    }

    public void deleteUserFollow(String id) {
        userFollowDao.deleteById(Long.parseLong(id));
    }

    public UserFollow selectByBoth(User focus, User fans){
        return userFollowDao.selectByBoth(focus, fans);
    }

    public void deleteByBoth(String focusId, String fansId) {
        userFollowDao.deleteByBoth(Long.parseLong(focusId), Long.parseLong(fansId));
    }

}
