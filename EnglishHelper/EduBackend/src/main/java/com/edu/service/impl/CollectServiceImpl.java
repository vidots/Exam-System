package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.base.BusinessException;
import com.edu.mapper.CollectMapper;
import com.edu.model.Collect;
import com.edu.model.Question;
import com.edu.service.CollectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    private final CollectMapper collectMapper;

    public CollectServiceImpl(CollectMapper collectMapper) {
        this.collectMapper = collectMapper;
    }

    @Override
    public List<Question> listCollects(Integer userId) {
        return collectMapper.listCollects(userId);
    }

    @Override
    public Collect addCollect(Integer questionId, Integer userId) {
        if (isCollected(questionId, userId)) {
            throw new BusinessException("该题目已经被收藏了");
        }
        Collect collect = new Collect();
        collect.setQuestionId(questionId);
        collect.setUserId(userId);
        collectMapper.insert(collect);
        return collect;
    }

    @Override
    public Integer cancelCollect(Integer questionId, Integer userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        wrapper.eq("user_id", userId);
        Collect collect = collectMapper.selectOne(wrapper);
        if (collect == null) {
            throw new BusinessException("该题目还未被收藏");
        }
        return collectMapper.deleteById(collect.getId());
    }

    @Override
    public Boolean isCollected(Integer questionId, Integer userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        wrapper.eq("user_id", userId);
        Collect collect = collectMapper.selectOne(wrapper);
        return collect != null;
    }
}
