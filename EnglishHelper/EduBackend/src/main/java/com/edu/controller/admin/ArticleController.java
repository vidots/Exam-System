package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Article;
import com.edu.model.ArticleType;
import com.edu.service.ArticleService;
import com.edu.vo.req.ArticleQuery;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article-type/list")
    public RestResponse listArticleTypes() {
        List<ArticleType> articleTypes = articleService.listArticleTypes();
        return RestResponse.ok(articleTypes);
    }

    @PostMapping("/article-type/add")
    public RestResponse addArticleType(@RequestBody ArticleType articleType) {
        ArticleType savedAT = articleService.addArticleType(articleType);
        return RestResponse.ok(savedAT);
    }

    @PostMapping("/article-type/edit")
    public RestResponse editArticleType(@RequestBody ArticleType articleType) {
        ArticleType editedAT = articleService.editArticleType(articleType);
        return RestResponse.ok(editedAT);
    }

    @PostMapping("/article-type/delete/{id}")
    public RestResponse deleteArticleType(@PathVariable Integer id) {
        articleService.deleteArticleType(id);
        return RestResponse.ok();
    }

    @GetMapping("/article/list/{current}/{size}")
    public RestResponse listArticle(@PathVariable("current") int current, @PathVariable("size") int size, ArticleQuery articleQuery) {
        PageResponse pageResponse = articleService.listArticle(current, size, articleQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/article/{id}")
    public RestResponse getArticle(@PathVariable("id") Integer id) {
        return RestResponse.ok(articleService.getArticle(id));
    }

    @PostMapping("/article/add")
    public RestResponse addArticleType(@RequestBody Article article) {
        article.setUpdateTime(new Date());
        Article savedA = articleService.addArticle(article);
        return RestResponse.ok(savedA);
    }

    @PostMapping("/article/edit/{id}")
    public RestResponse editArticle(@PathVariable("id")Integer id, @RequestBody Article article) {
        article.setUpdateTime(new Date());
        Article editedA = articleService.editArticle(article, id);
        return RestResponse.ok(editedA);
    }

    @PostMapping("/article/delete/{id}")
    public RestResponse deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return RestResponse.ok();
    }

}


















