package com.programan.cm.db.dao;

import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleLike;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleLikeDao extends DataTablesRepository<ArticleLike, Long> {

    @Query(value = "select al from ArticleLike al where al.article = ?1")
    List<ArticleLike> selectByArticle(Article article);

    @Query(value = "select al from ArticleLike al where al.user = ?1")
    List<ArticleLike> selectByUser(User user);

    @Query(value = "select count(al) from ArticleLike al where al.article = ?1")
    Integer selectCountByArticle(Article article);

    @Query(value = "select count(al) from ArticleLike al where al.user = ?1")
    Integer selectCountByUser(User user);

    @Query(value = "select al from ArticleLike al where al.article = ?1 and al.user = ?2")
    ArticleLike selectByBoth(Article article, User user);

}
