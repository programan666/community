package com.programan.cm.db.dao;

import com.programan.cm.db.model.Role;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowDao extends DataTablesRepository<UserFollow, Long> {

    @Query(value = "select uf from UserFollow uf where uf.focusUser = ?1")
    List<UserFollow> selectByFocus(User user);

    @Query(value = "select uf from UserFollow uf where uf.fansUser = ?1")
    List<UserFollow> selectByFans(User user);

    @Query(value = "select count(uf) from UserFollow uf where uf.focusUser = ?1")
    Integer selectCountByFocus(User user);

    @Query(value = "select count(uf) from UserFollow uf where uf.fansUser = ?1")
    Integer selectCountByFans(User user);

    @Query(value = "select uf from UserFollow uf where uf.focusUser = ?1 and uf.fansUser = ?2")
    UserFollow selectByBoth(User focus, User fans);

    @Query(value = "delete from user_follow where focus_user_id = :focusId and fans_user_id = :fansId", nativeQuery = true)
    @Modifying
    void deleteByBoth(@Param("focusId") Long focusId, @Param("fansId") Long fansId);

}
