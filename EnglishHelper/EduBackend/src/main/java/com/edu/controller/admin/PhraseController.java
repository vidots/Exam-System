package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Phrase;
import com.edu.service.PhraseService;
import com.edu.vo.req.PhraseQuery;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class PhraseController {
    private final PhraseService phraseService;

    public PhraseController(PhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @GetMapping("/phrase/list/{current}/{size}")
    public RestResponse listPhrase(@PathVariable("current") int current, @PathVariable("size") int size, PhraseQuery phraseQuery) {
        PageResponse pageResponse = phraseService.listPhrases(current, size, phraseQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/phrase/exist/{phrase}")
    public RestResponse getPhraseExists(@PathVariable("phrase") String phrase) {
        return RestResponse.ok(phraseService.getPhraseExist(phrase));
    }

    @GetMapping("/phrase/{id}")
    public RestResponse getPhrase(@PathVariable("id") Integer id) {
        return RestResponse.ok(phraseService.getPhrase(id));
    }

    @PostMapping("/phrase/add")
    public RestResponse addPhrase(@RequestBody Phrase phrase) {
        Phrase savedPhrase = phraseService.addPhrase(phrase);
        return RestResponse.ok(savedPhrase);
    }

    @PostMapping("/phrase/edit/{id}")
    public RestResponse editPhrase(@PathVariable("id")Integer id, @RequestBody Phrase phrase) {
        Phrase editedPhrase = phraseService.editPhrase(phrase, id);
        return RestResponse.ok(editedPhrase);
    }

    @PostMapping("/phrase/delete/{id}")
    public RestResponse deletePhrase(@PathVariable Integer id) {
        phraseService.deletePhrase(id);
        return RestResponse.ok();
    }
    
}
