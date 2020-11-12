package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.News;
import com.edu.service.NewsService;
import com.edu.vo.req.NewsQuery;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/list/{current}/{size}")
    public RestResponse listNews(@PathVariable("current") int current, @PathVariable("size") int size, NewsQuery newsQuery) {
        PageResponse pageResponse = newsService.listNews(current, size, newsQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/news/{id}")
    public RestResponse getNews(@PathVariable("id") Integer id) {
        return RestResponse.ok(newsService.getNews(id));
    }

    @PostMapping("/news/add")
    public RestResponse addNewsType(@RequestBody News news) {
        news.setUpdateTime(new Date());
        News savedA = newsService.addNews(news);
        return RestResponse.ok(savedA);
    }

    @PostMapping("/news/edit/{id}")
    public RestResponse editNews(@PathVariable("id")Integer id, @RequestBody News news) {
        news.setUpdateTime(new Date());
        News editedA = newsService.editNews(news, id);
        return RestResponse.ok(editedA);
    }

    @PostMapping("/news/delete/{id}")
    public RestResponse deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
        return RestResponse.ok();
    }

}


















