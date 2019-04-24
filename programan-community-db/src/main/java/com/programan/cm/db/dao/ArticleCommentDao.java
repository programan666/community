package com.programan.cm.db.dao;

import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleComment;
import com.programan.cm.db.model.Role;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentDao extends DataTablesRepository<ArticleComment, Long> {

    @Query(value = "select ac from ArticleComment ac where ac.article = ?1")
    List<ArticleComment> selectByArticle(Article article);

}
