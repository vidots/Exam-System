package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.ArticleMapper;
import com.edu.mapper.ArticleTypeMapper;
import com.edu.model.Article;
import com.edu.model.ArticleType;
import com.edu.service.ArticleService;
import com.edu.vo.req.ArticleQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleTypeMapper articleTypeMapper;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleTypeMapper articleTypeMapper, ArticleMapper articleMapper) {
        this.articleTypeMapper = articleTypeMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public ArticleType addArticleType(ArticleType articleType) {
        articleType.setId(null);
        articleTypeMapper.insert(articleType);
        return articleType;
    }

    @Override
    public ArticleType editArticleType(ArticleType articleType) {
        articleTypeMapper.updateById(articleType);
        return articleType;
    }

    @Override
    public int deleteArticleType(Integer id) {
        return articleTypeMapper.deleteById(id);
    }

    @Override
    public List<ArticleType> listArticleTypes() {
        return articleTypeMapper.selectList(null);
    }

    @Override
    public Article addArticle(Article article) {
        article.setId(null);
        articleMapper.insert(article);
        return article;
    }

    @Override
    public Article editArticle(Article article, Integer id) {
        articleMapper.updateById(article);
        return article;
    }

    @Override
    public int deleteArticle(Integer id) {
        return articleMapper.deleteById(id);
    }

    @Override
    public Article getArticle(Integer id) {
        return articleMapper.selectById(id);
    }

    @Override
    public PageResponse<Article> listArticle(Integer current, Integer size, ArticleQuery articleQuery) {
        IPage<Article> page = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (articleQuery.getArticleType() != null) {
            wrapper.eq("type_id", articleQuery.getArticleType());
        }
        if (articleQuery.getQuery() != null) {
            wrapper.like("title", articleQuery.getQuery());
        }
        wrapper.orderByDesc("update_time");
        articleMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Article> listAllArticles(Integer articleType) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type_id", articleType);
        return articleMapper.selectList(wrapper);
    }
}
