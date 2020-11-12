package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Word;
import com.edu.service.WordService;
import com.edu.vo.req.WordQuery;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/word/list/{current}/{size}")
    public RestResponse listWord(@PathVariable("current") int current, @PathVariable("size") int size, WordQuery wordQuery) {
        PageResponse pageResponse = wordService.listWords(current, size, wordQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/word/exist/{word}")
    public RestResponse getWordExists(@PathVariable("word") String word) {
        return RestResponse.ok(wordService.getWordExist(word));
    }

    @GetMapping("/word/{id}")
    public RestResponse getWord(@PathVariable("id") Integer id) {
        return RestResponse.ok(wordService.getWord(id));
    }

    @PostMapping("/word/add")
    public RestResponse addWordType(@RequestBody Word word) {
        Word savedWord = wordService.addWord(word);
        return RestResponse.ok(savedWord);
    }

    @PostMapping("/word/edit/{id}")
    public RestResponse editWord(@PathVariable("id")Integer id, @RequestBody Word word) {
        Word editedWord = wordService.editWord(word, id);
        return RestResponse.ok(editedWord);
    }

    @PostMapping("/word/delete/{id}")
    public RestResponse deleteWord(@PathVariable Integer id) {
        wordService.deleteWord(id);
        return RestResponse.ok();
    }
    
}
