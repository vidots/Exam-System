package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Book;
import com.edu.model.Chapter;
import com.edu.model.Paragraph;
import com.edu.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 书籍相关
    @GetMapping("/book/list/{current}/{size}")
    public RestResponse listBook(@PathVariable("current") int current, @PathVariable("size") int size) {
        PageResponse pageResponse = bookService.listBooks(current, size);
        return RestResponse.ok(pageResponse);
    }
    @PostMapping("/book/add")
    public RestResponse addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return RestResponse.ok(savedBook);
    }
    @PostMapping("/book/edit")
    public RestResponse editBook(@RequestBody Book book) {
        Book updatedBook = bookService.editBook(book);
        return RestResponse.ok(updatedBook);
    }
    @PostMapping("/book/delete/{id}")
    public RestResponse deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return RestResponse.ok();
    }
    @GetMapping("/book/{id}")
    public RestResponse getBook(@PathVariable Integer id) {
        return RestResponse.ok(bookService.getBook(id));
    }

    // 章节相关
    @GetMapping("/chapter/list/{current}/{size}")
    public RestResponse listChapter(@PathVariable("current") int current, @PathVariable("size") int size, Integer bookId) {
        PageResponse pageResponse = bookService.listChapters(current, size, bookId);
        return RestResponse.ok(pageResponse);
    }
    @PostMapping("/chapter/add")
    public RestResponse addChapter(@RequestBody Chapter chapter) {
        Chapter savedChapter = bookService.addChapter(chapter);
        return RestResponse.ok(savedChapter);
    }
    @PostMapping("/chapter/edit")
    public RestResponse editChapter(@RequestBody Chapter chapter) {
        Chapter updatedChapter = bookService.editChapter(chapter);
        return RestResponse.ok(updatedChapter);
    }
    @PostMapping("/chapter/{id}")
    public RestResponse editChapter(@PathVariable("id") Integer id) {
        return RestResponse.ok(bookService.getChapter(id));
    }
    @PostMapping("/chapter/delete/{id}")
    public RestResponse deleteChapter(@PathVariable("id") Integer id) {
        bookService.deleteChapter(id);
        return RestResponse.ok();
    }

    // 段落相关
    @GetMapping("/paragraph/list/{current}/{size}")
    public RestResponse listParagraph(@PathVariable("current") int current, @PathVariable("size") int size, Integer chapterId) {
        PageResponse pageResponse = bookService.listParagraphs(current, size, chapterId);
        return RestResponse.ok(pageResponse);
    }
    @PostMapping("/paragraph/add")
    public RestResponse addParagraph(@RequestBody Paragraph paragraph) {
        Paragraph savedParagraph = bookService.addParagraph(paragraph);
        return RestResponse.ok(savedParagraph);
    }
    @PostMapping("/paragraph/edit")
    public RestResponse editParagraph(@RequestBody Paragraph paragraph) {
        Paragraph updatedParagraph = bookService.editParagraph(paragraph);
        return RestResponse.ok(updatedParagraph);
    }
    @PostMapping("/paragraph/delete/{id}")
    public RestResponse deleteParagraph(@PathVariable Integer id) {
        bookService.deleteParagraph(id);
        return RestResponse.ok();
    }
}
