package com.programan.cm.web.manager;


import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.Topic;
import com.programan.cm.db.model.User;
import com.programan.cm.repository.db.ArticleRepository;
import com.programan.cm.repository.db.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ArticleManager {

    private ArticleRepository articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article selectById(String id) {
        return articleRepository.selectById(id);
    }

    public Article selectById(String id, boolean read) {
        Article article = articleRepository.selectById(id);
        if(article == null) {
            return null;
        }
        if(read) {
            article.setReadNum(article.getReadNum() + 1);
            articleRepository.saveArticle(article);
        }
        return article;
    }

    @Transactional
    public DataTablesOutput<Article> findArticle(DataTablesInput input) {
        DataTablesOutput<Article> dataTablesOutput = articleRepository.findArticle(input);
        return dataTablesOutput;
    }

    public List<Article> selectAll() {
        return articleRepository.selectAll();
    }

    public List<Article> selectByTopicPage(Topic topic, int fromIndex, int num) {
        return articleRepository.selectByTopicPage(topic, fromIndex, num);
    }

    public void saveArticle(Article article){
        if(articleRepository.selectById(article.getId()) != null) {
            //修改
            articleRepository.saveArticle(article);
        } else {
            //增加
            articleRepository.saveArticle(article);
        }
    }

    public void deleteArticle(String id) {
        articleRepository.deleteArticle(id);
    }

    public List<Article> selectAllByUser(User user) {
        return articleRepository.selectAllByUser(user);
    }

    public List<Article> selectAll (String createTypeId, String topicId, String title, String userId){
        return articleRepository.selectAll(createTypeId, topicId, title, userId);
    }


}
