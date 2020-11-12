package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Article;
import com.edu.model.ArticleType;
import com.edu.vo.req.ArticleQuery;

import java.util.List;

public interface ArticleService {
    ArticleType addArticleType(ArticleType articleType);
    ArticleType editArticleType(ArticleType articleType);
    int deleteArticleType(Integer id);
    List<ArticleType> listArticleTypes();

    Article addArticle(Article article);
    Article editArticle(Article article, Integer id);
    int deleteArticle(Integer id);
    Article getArticle(Integer id);
    PageResponse<Article> listArticle(Integer current, Integer size, ArticleQuery articleQuery);
    List<Article> listAllArticles(Integer articleType);
}
