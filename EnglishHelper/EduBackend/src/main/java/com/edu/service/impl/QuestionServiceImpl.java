package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.QuestionMapper;
import com.edu.model.Question;
import com.edu.service.QuestionService;
import com.edu.vo.req.QuestionQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public PageResponse<Question> listQuestion(Integer current, Integer size, QuestionQuery questionQuery) {
        IPage<Question> page = new Page<>(current, size);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (questionQuery.getPaperId() != null) {
            wrapper.eq("paper_id", questionQuery.getPaperId());
        }
        if (questionQuery.getQuery() != null) {
            wrapper.like("name", questionQuery.getQuery());
        }
        questionMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public Question addQuestion(Question question) {
        question.setId(null);
        questionMapper.insert(question);
        return question;
    }

    @Override
    public Question getQuestion(Integer id) {
        return questionMapper.selectById(id);
    }

    @Override
    public Question editQuestion(Question question, Integer id) {
        questionMapper.updateById(question);
        return question;
    }

    @Override
    public int deleteQuestion(Integer id) {
        return questionMapper.deleteById(id);
    }

    @Override
    public List<Question> listQuestions(Integer paperId) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        List<Question> questions = questionMapper.selectList(wrapper);
        return questions;
    }
}
