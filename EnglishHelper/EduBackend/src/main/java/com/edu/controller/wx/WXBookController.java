package com.edu.controller.wx;

import com.edu.base.RestResponse;
import com.edu.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wx")
public class WXBookController {
    private final BookService bookService;

    public WXBookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 书籍相关
    @PostMapping("/book/list")
    public RestResponse listBook() {
        return RestResponse.ok(bookService.listAllBooks());
    }

    // 章节相关
    @PostMapping("/chapter/list")
    public RestResponse listChapter(Integer bookId) {
        return RestResponse.ok(bookService.listBookChapters(bookId));
    }

    // 段落相关
    @PostMapping("/paragraph/list")
    public RestResponse listParagraph(Integer chapterId) {
        return RestResponse.ok(bookService.listChapterParagraphs(chapterId));
    }
}
