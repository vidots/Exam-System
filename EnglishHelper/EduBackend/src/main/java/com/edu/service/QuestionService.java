package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Question;
import com.edu.vo.req.QuestionQuery;

import java.util.List;

public interface QuestionService {
    PageResponse<Question> listQuestion(Integer current, Integer size, QuestionQuery articleQuery);
    Question addQuestion(Question article);
    Question getQuestion(Integer id);
    Question editQuestion(Question article, Integer id);
    int deleteQuestion(Integer id);
    // 获取一张试卷的题目
    List<Question> listQuestions(Integer paperId);
}
