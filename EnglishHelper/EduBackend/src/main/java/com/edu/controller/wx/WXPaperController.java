package com.edu.controller.wx;

import com.edu.base.RestResponse;
import com.edu.base.WxContext;
import com.edu.model.Answer;
import com.edu.model.Article;
import com.edu.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
public class WXPaperController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ArticleService articleService;
    private final SeriesService seriesService;
    private final PaperService paperService;
    private final WxContext wxContext;

    public WXPaperController(QuestionService questionService, AnswerService answerService, ArticleService articleService, SeriesService seriesService, PaperService paperService, WxContext wxContext) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.articleService = articleService;
        this.seriesService = seriesService;
        this.paperService = paperService;
        this.wxContext = wxContext;
    }

    @PostMapping("/paper/{paper_id}")
    public RestResponse getPaperSheet(@PathVariable("paper_id") Integer paperId) {
        Map<String, Object> map = new HashMap<>();
        map.put("questions", questionService.listQuestions(paperId));
        map.put("userAnswers", answerService.getAnswers(wxContext.getCurrentAuth().getUserId(), paperId));
        return RestResponse.ok(map);
    }

    @PostMapping("/paper/{paper_id}/submit")
    public RestResponse submitAnswers(@PathVariable("paper_id") Integer paperId, @RequestBody List<Answer> answerList) {
        answerService.insertAnswers(wxContext.getCurrentAuth().getUserId(), answerList);
        return RestResponse.ok();
    }

    @PostMapping("/series/list")
    public RestResponse getSeries() {
        return RestResponse.ok(seriesService.listSeries());
    }

    @PostMapping("/paper/list")
    public RestResponse getPapers(Integer seriesId) {
        return RestResponse.ok(paperService.listShowPaper(seriesId, wxContext.getCurrentAuth().getUserId()));
    }
}
