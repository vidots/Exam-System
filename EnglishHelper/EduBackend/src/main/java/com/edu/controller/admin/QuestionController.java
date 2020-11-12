package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Question;
import com.edu.service.QuestionService;
import com.edu.vo.req.QuestionQuery;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/list/{current}/{size}")
    public RestResponse listQuestion(@PathVariable("current") int current, @PathVariable("size") int size, QuestionQuery questionQuery) {
        PageResponse pageResponse = questionService.listQuestion(current, size, questionQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/question/{id}")
    public RestResponse getQuestion(@PathVariable("id") Integer id) {
        return RestResponse.ok(questionService.getQuestion(id));
    }

    @PostMapping("/question/add")
    public RestResponse addQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.addQuestion(question);
        return RestResponse.ok(savedQuestion);
    }

    @PostMapping("/question/edit/{id}")
    public RestResponse editQuestion(@PathVariable("id")Integer id, @RequestBody Question question) {
        Question editedQuestion = questionService.editQuestion(question, id);
        return RestResponse.ok(editedQuestion);
    }

    @PostMapping("/question/delete/{id}")
    public RestResponse deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return RestResponse.ok();
    }

}
