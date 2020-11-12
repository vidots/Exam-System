package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Sentence;
import com.edu.service.SentenceService;
import com.edu.vo.req.SentenceQuery;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class SentenceController {

    private final SentenceService sentenceService;

    public SentenceController(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    @GetMapping("/sentence/list/{current}/{size}")
    public RestResponse listSentence(@PathVariable("current") int current, @PathVariable("size") int size, SentenceQuery sentenceQuery) {
        PageResponse pageResponse = sentenceService.listSentences(current, size, sentenceQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/sentence/{id}")
    public RestResponse getSentence(@PathVariable("id") Integer id) {
        return RestResponse.ok(sentenceService.getSentence(id));
    }

    @PostMapping("/sentence/add")
    public RestResponse addSentenceType(@RequestBody Sentence sentence) {
        Sentence savedSentence = sentenceService.addSentence(sentence);
        return RestResponse.ok(savedSentence);
    }

    @PostMapping("/sentence/edit/{id}")
    public RestResponse editSentence(@PathVariable("id")Integer id, @RequestBody Sentence sentence) {
        Sentence editedSentence = sentenceService.editSentence(sentence, id);
        return RestResponse.ok(editedSentence);
    }

    @PostMapping("/sentence/delete/{id}")
    public RestResponse deleteSentence(@PathVariable Integer id) {
        sentenceService.deleteSentence(id);
        return RestResponse.ok();
    }
    
}
