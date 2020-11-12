package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.mapper.AnswerMapper;
import com.edu.model.Answer;
import com.edu.service.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Override
    public List<Answer> getAnswers(Integer userId, Integer paperId) {
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("paper_id", paperId);
        return answerMapper.selectList(wrapper);
    }

    @Override
    public Integer insertAnswers(Integer userId, List<Answer> answerList) {
        for (Answer answer : answerList) {
            answer.setUserId(userId);
            if (answer.getId() == null) {
                answerMapper.insert(answer);
            } else {
                answerMapper.updateById(answer);
            }
        }
        return 1;
    }
}
