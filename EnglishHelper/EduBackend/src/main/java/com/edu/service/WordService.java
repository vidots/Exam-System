package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Word;
import com.edu.vo.req.WordQuery;

public interface WordService {
    Word addWord(Word word);
    Word editWord(Word word, Integer id);
    int deleteWord(Integer id);
    Word getWord(Integer id);
    Boolean getWordExist(String word);
    PageResponse<Word> listWords(Integer current, Integer size, WordQuery wordQuery);
    String getWordDetail(String word);
}
