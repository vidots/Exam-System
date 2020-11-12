package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.PhraseMapper;
import com.edu.model.Phrase;
import com.edu.service.PhraseService;
import com.edu.vo.req.PhraseQuery;
import org.springframework.stereotype.Service;

@Service
public class PhraseServiceImpl implements PhraseService {

    private final PhraseMapper phraseMapper;

    public PhraseServiceImpl(PhraseMapper phraseMapper) {
        this.phraseMapper = phraseMapper;
    }

    @Override
    public Phrase addPhrase(Phrase phrase) {
        phrase.setId(null);
        phraseMapper.insert(phrase);
        return phrase;
    }

    @Override
    public Phrase editPhrase(Phrase phrase, Integer id) {
        phraseMapper.updateById(phrase);
        return phrase;
    }

    @Override
    public int deletePhrase(Integer id) {
        return phraseMapper.deleteById(id);
    }

    @Override
    public Phrase getPhrase(Integer id) {
        return phraseMapper.selectById(id);
    }

    @Override
    public Boolean getPhraseExist(String phrase) {
        QueryWrapper<Phrase> query = new QueryWrapper<>();
        query.eq("phrase", phrase);
        Phrase result = phraseMapper.selectOne(query);
        return result != null;
    }

    @Override
    public PageResponse<Phrase> listPhrases(Integer current, Integer size, PhraseQuery phraseQuery) {
        IPage<Phrase> page = new Page<>(current, size);
        QueryWrapper<Phrase> wrapper = new QueryWrapper<>();
        String query = phraseQuery.getQuery();
        if (query != null && !"".equals(query)) {
            wrapper.like("phrase", query);
        }
        wrapper.orderByDesc("id");
        phraseMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }
}
