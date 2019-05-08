package com.programan.cm.repository.db;

import com.programan.cm.db.dao.ArticleDao;
import com.programan.cm.db.dao.TopicDao;
import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.Topic;
import com.programan.cm.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ArticleRepository {

    private ArticleDao articleDao;

    @Autowired
    public void setArticleDao(ArticleDao articleDao){
        this.articleDao = articleDao;
    }


    public Article selectById(String id){
        return articleDao.selectById(Long.parseLong(id));
    }

    public Article selectById(Long id){
        return articleDao.selectById(id);
    }

    public List<Article> selectAllByTitle(String title) {
        return articleDao.selectAllByTitle(title);
    }

    @Transactional
    public DataTablesOutput<Article> findArticle(DataTablesInput input) {
        return articleDao.findAll(input);
    }

    public List<Article> selectAll(){
        return articleDao.selectAll();
    }

    public void saveArticle(Article article) {
        articleDao.save(article);
    }

    public void deleteArticle(String id) {
        articleDao.deleteById(Long.parseLong(id));
    }

    public List<Article> selectAllByUser(User user){
        return articleDao.selectAllByUser(user);
    }

    public List<Article> selectByTopicPage(Topic topic, int fromIndex, int num) {
        return articleDao.selectByTopicPage(topic == null ? null : topic.getId(), fromIndex, num);
    }

    public List<Article> selectAll(String createTypeId, String topicId, String title, String userId) {
        return articleDao.selectAll(createTypeId.equals("0") ? null : Long.parseLong(createTypeId),
                topicId.equals("0") ? null : Long.parseLong(topicId), title == null ? "" : title, userId);
    }

}
