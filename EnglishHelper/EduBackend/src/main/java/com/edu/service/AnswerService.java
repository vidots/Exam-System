package com.edu.service;

import com.edu.model.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAnswers(Integer userId, Integer paperId);
    Integer insertAnswers(Integer userId, List<Answer> answerList);
}
