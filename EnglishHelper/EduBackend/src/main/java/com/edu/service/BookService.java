package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Book;
import com.edu.model.Chapter;
import com.edu.model.Paragraph;

import java.util.List;

public interface BookService {
    // 列出所有的书籍
    PageResponse<Book> listBooks(Integer current, Integer size);
    List<Book> listAllBooks();
    Book addBook(Book book);
    Book editBook(Book book);
    Book getBook(Integer id);
    Integer deleteBook(Integer id);

    // 列出一本书的所有章节
    PageResponse<Chapter> listChapters(Integer current, Integer size, Integer bookId);
    List<Chapter> listBookChapters(Integer bookId);
    Chapter addChapter(Chapter chapter);
    Chapter editChapter(Chapter chapter);
    Chapter getChapter(Integer id);
    Integer deleteChapter(Integer id);

    // 列出一个章节的所有段落
    PageResponse<Paragraph> listParagraphs(Integer current, Integer size, Integer chapterId);
    List<Paragraph> listChapterParagraphs(Integer chapterId);
    Paragraph addParagraph(Paragraph paragraph);
    Paragraph editParagraph(Paragraph paragraph);
    Integer deleteParagraph(Integer id);
}
