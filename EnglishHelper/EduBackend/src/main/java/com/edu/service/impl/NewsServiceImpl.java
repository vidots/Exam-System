package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.NewsMapper;
import com.edu.model.News;
import com.edu.service.NewsService;
import com.edu.vo.req.NewsQuery;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public News addNews(News news) {
        news.setId(null);
        newsMapper.insert(news);
        return news;
    }

    @Override
    public News editNews(News news, Integer id) {
        newsMapper.updateById(news);
        return news;
    }

    @Override
    public int deleteNews(Integer id) {
        return newsMapper.deleteById(id);
    }

    @Override
    public News getNews(Integer id) {
        return newsMapper.selectById(id);
    }

    @Override
    public PageResponse<News> listNews(Integer current, Integer size, NewsQuery newsQuery) {
        IPage<News> page = new Page<>(current, size);
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        if (newsQuery.getQuery() != null) {
            wrapper.like("title", newsQuery.getQuery());
        }
        wrapper.orderByDesc("update_time");
        newsMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }
}
