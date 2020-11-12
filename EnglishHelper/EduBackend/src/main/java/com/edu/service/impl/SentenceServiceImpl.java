package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.SentenceMapper;
import com.edu.model.Sentence;
import com.edu.service.SentenceService;
import com.edu.vo.req.SentenceQuery;
import org.springframework.stereotype.Service;

@Service
public class SentenceServiceImpl implements SentenceService {

    private final SentenceMapper sentenceMapper;

    public SentenceServiceImpl(SentenceMapper sentenceMapper) {
        this.sentenceMapper = sentenceMapper;
    }

    @Override
    public Sentence addSentence(Sentence sentence) {
        sentence.setId(null);
        sentenceMapper.insert(sentence);
        return sentence;
    }

    @Override
    public Sentence editSentence(Sentence sentence, Integer id) {
        sentenceMapper.updateById(sentence);
        return null;
    }

    @Override
    public int deleteSentence(Integer id) {
        return sentenceMapper.deleteById(id);
    }

    @Override
    public Sentence getSentence(Integer id) {
        return sentenceMapper.selectById(id);
    }

    @Override
    public PageResponse<Sentence> listSentences(Integer current, Integer size, SentenceQuery sentenceQuery) {
        IPage<Sentence> page = new Page<>(current, size);
        QueryWrapper<Sentence> wrapper = new QueryWrapper<>();
        String query = sentenceQuery.getQuery();
        Integer category = sentenceQuery.getCategory();
        if (query != "" && query != null) {
            wrapper.like("sentence", query.length() > 6 ? query.substring(0, query.length() - 3) : query);
        }
        if (category != null && category == 1) {
            wrapper.eq("category", category);
        }
        wrapper.orderByDesc("id");
        sentenceMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }
}
