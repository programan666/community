package com.programan.cm.web.manager;


import com.programan.cm.db.model.Article;
import com.programan.cm.db.model.ArticleComment;
import com.programan.cm.db.model.Role;
import com.programan.cm.repository.db.ArticleCommentRepository;
import com.programan.cm.repository.db.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ArticleCommentManager {

    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    public void setArticleCommentRepository(ArticleCommentRepository articleCommentRepository) {
        this.articleCommentRepository = articleCommentRepository;
    }

    @Transactional
    public DataTablesOutput<ArticleComment> findArticleComment(DataTablesInput input) {
        DataTablesOutput<ArticleComment> dataTablesOutput = articleCommentRepository.findArticleComment(input);
        return dataTablesOutput;
    }

    public void saveArticleComment(ArticleComment articleComment) {
        articleCommentRepository.saveArticleComment(articleComment);
    }

    public List<ArticleComment> selectByArticle(Article article) {
        return articleCommentRepository.selectByArticle(article);
    }

    public Integer selectCountByArticle(Article article) {
        return articleCommentRepository.selectCountByArticle(article);
    }

    public void deleteArticleComment(String id) {
        articleCommentRepository.deleteArticleComment(id);
    }

}
