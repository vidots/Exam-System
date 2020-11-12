package com.edu.controller.wx;

import com.edu.base.RestResponse;
import com.edu.service.ArticleService;
import com.edu.service.DyContentService;
import com.edu.service.NewsService;
import com.edu.vo.req.NewsQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx")
public class WXArticleController {
    private final ArticleService articleService;
    private final NewsService newsService;
    private final DyContentService dyContentService;

    public WXArticleController(ArticleService articleService, NewsService newsService, DyContentService dyContentService) {
        this.articleService = articleService;
        this.newsService = newsService;
        this.dyContentService = dyContentService;
    }

    @PostMapping("/article-type/list")
    public RestResponse getArticleTypes() {
        return RestResponse.ok(articleService.listArticleTypes());
    }

    @PostMapping("/article/list")
    public RestResponse getArticles(Integer articleType) {
        return RestResponse.ok(articleService.listAllArticles(articleType));
    }

    @PostMapping("/article/{id}")
    public RestResponse getArticle(@PathVariable("id") Integer id) {
        return RestResponse.ok(articleService.getArticle(id));
    }

    @PostMapping("/news/list")
    public RestResponse getArticles() {
        return RestResponse.ok(newsService.listNews(1, 100, new NewsQuery()));
    }

    @PostMapping("/news/{id}")
    public RestResponse getNews(@PathVariable("id") Integer id) {
        return RestResponse.ok(newsService.getNews(id));
    }

    @PostMapping("/dycontent/subject")
    public RestResponse getDyContent(String subject) {
        return RestResponse.ok(dyContentService.getDyContentBy(subject));
    }
}
