package com.programan.cm.web.manager;


import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import com.programan.cm.repository.db.RoleRepository;
import com.programan.cm.repository.db.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserFollowManager {

    private UserFollowRepository userFollowRepository;

    @Autowired
    public void setUserFollowRepository(UserFollowRepository userFollowRepository) {
        this.userFollowRepository = userFollowRepository;
    }

    public List<UserFollow> selectByFocus(User user) {
        return userFollowRepository.selectByFocus(user);
    }

    public List<UserFollow> selectByFans(User user) {
        return userFollowRepository.selectByFans(user);
    }

    public Integer selectCountByFocus(User user) {
        return userFollowRepository.selectCountByFocus(user);
    }

    public Integer selectCountByFans(User user) {
        return userFollowRepository.selectCountByFans(user);
    }

    @Transactional
    public DataTablesOutput<UserFollow> findUserFollow(DataTablesInput input) {
        DataTablesOutput<UserFollow> dataTablesOutput = userFollowRepository.findUserFollow(input);
        return dataTablesOutput;
    }

    public void saveUserFollow(UserFollow userFollow){
        userFollowRepository.saveUserFollow(userFollow);
    }

    public void deleteUserFollow(String id) {
        userFollowRepository.deleteUserFollow(id);
    }

    public UserFollow selectByBoth(User focus, User fans) {
        return userFollowRepository.selectByBoth(focus,fans);
    }

    public void deleteByBoth(String focusId, String fansId) {
        userFollowRepository.deleteByBoth(focusId, fansId);
    }

}
