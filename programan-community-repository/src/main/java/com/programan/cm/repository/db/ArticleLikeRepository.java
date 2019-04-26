package com.programan.cm.repository.db;

import com.programan.cm.db.dao.ArticleLikeDao;
import com.programan.cm.db.dao.UserFollowDao;
import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleLike;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ArticleLikeRepository {

    private ArticleLikeDao articleLikeDao;

    @Autowired
    public void setArticleLikeDao(ArticleLikeDao articleLikeDao) {
        this.articleLikeDao = articleLikeDao;
    }

    public List<ArticleLike> selectByArticle(Article article){
        return articleLikeDao.selectByArticle(article);
    }

    public List<ArticleLike> selectByUser(User user){
        return articleLikeDao.selectByUser(user);
    }

    public Integer selectCountByArticle(Article article){
        return articleLikeDao.selectCountByArticle(article);
    }

    public Integer selectCountByUser(User user){
        return articleLikeDao.selectCountByUser(user);
    }

    @Transactional
    public DataTablesOutput<ArticleLike> findArticleLike(DataTablesInput input) {
        return articleLikeDao.findAll(input);
    }

    public void saveArticleLike(ArticleLike articleLike) {
        articleLikeDao.save(articleLike);
    }

    public void deleteArticleLike(String id) {
        articleLikeDao.deleteById(Long.parseLong(id));
    }

    public ArticleLike selectByBoth(Article article, User user){
        return articleLikeDao.selectByBoth(article, user);
    }


}
