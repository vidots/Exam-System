package com.edu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeController {
    @GetMapping("/")
    public String index() {
        return "您好，欢迎使用微信小程序：艾学习考研英语。我们是一家专注于应用人工智能技术来提高考研学习的效率";
    }
}
