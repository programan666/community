package com.programan.cm.web.manager;


import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleLike;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserFollow;
import com.programan.cm.repository.db.ArticleLikeRepository;
import com.programan.cm.repository.db.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ArticleLikeManager {

    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    public void setArticleLikeRepository(ArticleLikeRepository articleLikeRepository) {
        this.articleLikeRepository = articleLikeRepository;
    }

    public List<ArticleLike> selectByArticle(Article article) {
        return articleLikeRepository.selectByArticle(article);
    }

    public List<ArticleLike> selectByUser(User user) {
        return articleLikeRepository.selectByUser(user);
    }

    public Integer selectCountByArticle(Article article) {
        return articleLikeRepository.selectCountByArticle(article);
    }

    public Integer selectCountByUser(User user) {
        return articleLikeRepository.selectCountByUser(user);
    }

    @Transactional
    public DataTablesOutput<ArticleLike> findArticleLike(DataTablesInput input) {
        DataTablesOutput<ArticleLike> dataTablesOutput = articleLikeRepository.findArticleLike(input);
        return dataTablesOutput;
    }

    public void saveArticleLike(ArticleLike articleLike){
        articleLikeRepository.saveArticleLike(articleLike);
    }

    public void deleteArticleLike(String id) {
        articleLikeRepository.deleteArticleLike(id);
    }

    public ArticleLike selectByBoth(Article article, User user) {
        return articleLikeRepository.selectByBoth(article,user);
    }

}
