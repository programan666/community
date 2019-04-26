package com.programan.cm.repository.db;

import com.programan.cm.db.dao.ArticleCommentDao;
import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleComment;
import com.programan.cm.db.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ArticleCommentRepository {

    private ArticleCommentDao articleCommentDao;

    @Autowired
    public void setArticleCommentDao(ArticleCommentDao articleCommentDao){
        this.articleCommentDao = articleCommentDao;
    }

    public List<ArticleComment> selectByArticle(Article article) {
        return articleCommentDao.selectByArticle(article);
    }

    public Integer selectCountByArticle(Article article) {
        return articleCommentDao.selectCountByArticle(article);
    }

    @Transactional
    public DataTablesOutput<ArticleComment> findArticleComment(DataTablesInput input) {
        return articleCommentDao.findAll(input);
    }

    public void saveArticleComment(ArticleComment articleComment) {
        articleCommentDao.save(articleComment);
    }

    public void deleteArticleComment(String id){
        articleCommentDao.deleteById(Long.parseLong(id));
    }

}
