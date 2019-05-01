package com.programan.cm.db.dao;

import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.Topic;
import com.programan.cm.db.model.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends DataTablesRepository<Article, Long> {

    @Query(value = "select a from Article a where id = ?1")
    Article selectById(Long id);

    @Query(value = "select a from Article a")
    List<Article> selectAll();

    @Query(value = "select a from Article a where a.title like concat('%', ?1, '%')")
    List<Article> selectAllByTitle(String title);

    @Query(value = "select a from Article a where a.user = ?1")
    List<Article> selectAllByUser(User user);

    @Query(value = "select * from article where IF (:createType is not null, create_type_id=:createType , 1 = 1)  and IF (:topic is not null, topic_id=:topic , 1 = 1) and title like CONCAT('%',:title,'%') and auth_id = :userId", nativeQuery = true)
    List<Article> selectAll(@Param("createType") Long createTypeId, @Param("topic") Long topicId, @Param("title") String title, @Param("userId") String userId);

    @Query(value = "select a from Article a where a.topic = ?1")
    List<Article> selectByTopic(Topic topic);

    @Query(value = "select * from article where IF (:topicId is not null, topic_id = :topicId , 1 = 1) order by create_time desc limit :fromIndex,:num",nativeQuery = true)
    List<Article> selectByTopicPage(@Param("topicId") Long topicId, @Param("fromIndex") int fromIndex, @Param("num") int num);

}
