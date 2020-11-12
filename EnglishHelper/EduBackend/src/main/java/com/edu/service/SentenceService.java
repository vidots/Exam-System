package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Sentence;
import com.edu.vo.req.SentenceQuery;

public interface SentenceService {
    Sentence addSentence(Sentence sentence);
    Sentence editSentence(Sentence sentence, Integer id);
    int deleteSentence(Integer id);
    Sentence getSentence(Integer id);
    PageResponse<Sentence> listSentences(Integer current, Integer size, SentenceQuery sentenceQuery);
}
