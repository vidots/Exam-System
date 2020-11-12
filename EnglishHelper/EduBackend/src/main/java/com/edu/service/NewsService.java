package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.News;
import com.edu.vo.req.NewsQuery;

import java.util.List;

public interface NewsService {
    News addNews(News news);
    News editNews(News news, Integer id);
    int deleteNews(Integer id);
    News getNews(Integer id);
    PageResponse<News> listNews(Integer current, Integer size, NewsQuery newsQuery);

}
