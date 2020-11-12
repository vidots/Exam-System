package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.BusinessException;
import com.edu.base.PageResponse;
import com.edu.mapper.WordDictMapper;
import com.edu.mapper.WordMapper;
import com.edu.model.Word;
import com.edu.model.WordDict;
import com.edu.service.WordService;
import com.edu.vo.req.WordQuery;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService {

    private final WordMapper wordMapper;
    private final WordDictMapper wordDictMapper;

    public WordServiceImpl(WordMapper wordMapper, WordDictMapper wordDictMapper) {
        this.wordMapper = wordMapper;
        this.wordDictMapper = wordDictMapper;
    }

    @Override
    public Word addWord(Word word) {
        word.setId(null);
        wordMapper.insert(word);
        return word;
    }

    @Override
    public Word editWord(Word word, Integer id) {
        wordMapper.updateById(word);
        return word;
    }

    @Override
    public int deleteWord(Integer id) {
        return wordMapper.deleteById(id);
    }

    @Override
    public Word getWord(Integer id) {
        return wordMapper.selectById(id);
    }

    @Override
    public Boolean getWordExist(String word) {
        QueryWrapper<Word> query = new QueryWrapper<>();
        query.eq("word", word);
        Word result = wordMapper.selectOne(query);
        return result != null;
    }

    @Override
    public PageResponse<Word> listWords(Integer current, Integer size, WordQuery wordQuery) {
        IPage<Word> page = new Page<>(current, size);
        QueryWrapper<Word> wrapper = new QueryWrapper<>();
        String first_letter = wordQuery.getFirst_letter();
        if (first_letter != "" && first_letter != null) {
            wrapper.likeRight("word", first_letter.charAt(0));
        }
        String query = wordQuery.getQuery();
        if (query != null && query != "") {
            wrapper.likeRight("word", query.length() > 6 ? query.substring(0, query.length() - 3) : query);
        }
        wrapper.orderByDesc("id");
        wordMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public String getWordDetail(String word) {
        QueryWrapper<WordDict> wrapper = new QueryWrapper<>();
        wrapper.eq("word", word);
        WordDict dict = wordDictMapper.selectOne(wrapper);
        if (dict == null) {
            throw new BusinessException("单词未找到");
        }
        return dict.getDetail();
    }
}
