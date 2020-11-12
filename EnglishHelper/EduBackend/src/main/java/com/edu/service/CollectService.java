package com.edu.service;

import com.edu.model.Collect;
import com.edu.model.Question;

import java.util.List;

public interface CollectService {
    List<Question> listCollects(Integer userId);
    Collect addCollect(Integer questionId, Integer userId);
    Integer cancelCollect(Integer questionId, Integer userId);
    Boolean isCollected(Integer questionId, Integer userId);
}
