package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Phrase;
import com.edu.vo.req.PhraseQuery;

public interface PhraseService {
    Phrase addPhrase(Phrase phrase);
    Phrase editPhrase(Phrase phrase, Integer id);
    int deletePhrase(Integer id);
    Phrase getPhrase(Integer id);
    Boolean getPhraseExist(String phrase);
    PageResponse<Phrase> listPhrases(Integer current, Integer size, PhraseQuery phraseQuery);
}
