package com.edu.controller.wx;

import com.edu.base.RestResponse;
import com.edu.service.WordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx")
public class WXWordController {
    private final WordService wordService;

    public WXWordController(WordService wordService) {
        this.wordService = wordService;
    }


    @PostMapping("/word")
    public RestResponse getWordDict(String word) {
        return RestResponse.ok(wordService.getWordDetail(word));
    }
}
