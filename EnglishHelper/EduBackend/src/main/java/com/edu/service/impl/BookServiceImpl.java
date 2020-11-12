package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.BookMapper;
import com.edu.mapper.ChapterMapper;
import com.edu.mapper.ParagraphMapper;
import com.edu.model.Book;
import com.edu.model.Chapter;
import com.edu.model.Paragraph;
import com.edu.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final ChapterMapper chapterMapper;
    private final ParagraphMapper paragraphMapper;

    public BookServiceImpl(BookMapper bookMapper, ChapterMapper chapterMapper, ParagraphMapper paragraphMapper) {
        this.bookMapper = bookMapper;
        this.chapterMapper = chapterMapper;
        this.paragraphMapper = paragraphMapper;
    }

    @Override
    public PageResponse<Book> listBooks(Integer current, Integer size) {
        IPage<Book> page = new Page<>(current, size);
        bookMapper.selectPage(page, null);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Book> listAllBooks() {
        return bookMapper.selectList(null);
    }

    @Override
    public Book addBook(Book book) {
        book.setId(null);
        bookMapper.insert(book);
        return book;
    }

    @Override
    public Book editBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    public Book getBook(Integer id) {
        return bookMapper.selectById(id);
    }

    @Override
    public Integer deleteBook(Integer id) {
        return bookMapper.deleteById(id);
    }

    @Override
    public PageResponse<Chapter> listChapters(Integer current, Integer size, Integer bookId) {
        IPage<Chapter> page = new Page<>(current, size);
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id", bookId);
        chapterMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Chapter> listBookChapters(Integer bookId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id", bookId);
        return chapterMapper.selectList(wrapper);
    }

    @Override
    public Chapter addChapter(Chapter chapter) {
        chapter.setId(null);
        chapterMapper.insert(chapter);
        return chapter;
    }

    @Override
    public Chapter editChapter(Chapter chapter) {
        chapterMapper.updateById(chapter);
        return chapter;
    }

    @Override
    public Chapter getChapter(Integer id) {
        return chapterMapper.selectById(id);
    }

    @Override
    public Integer deleteChapter(Integer id) {
        return chapterMapper.deleteById(id);
    }

    @Override
    public PageResponse<Paragraph> listParagraphs(Integer current, Integer size, Integer chapterId) {
        IPage<Paragraph> page = new Page<>(current, size);
        QueryWrapper<Paragraph> wrapper = new QueryWrapper();
        wrapper.eq("chapter_id", chapterId);
        paragraphMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Paragraph> listChapterParagraphs(Integer chapterId) {
        QueryWrapper<Paragraph> wrapper = new QueryWrapper();
        wrapper.eq("chapter_id", chapterId);
        return paragraphMapper.selectList(wrapper);
    }

    @Override
    public Paragraph addParagraph(Paragraph paragraph) {
        paragraph.setId(null);
        paragraphMapper.insert(paragraph);
        return paragraph;
    }

    @Override
    public Paragraph editParagraph(Paragraph paragraph) {
        paragraphMapper.updateById(paragraph);
        return paragraph;
    }

    @Override
    public Integer deleteParagraph(Integer id) {
        return paragraphMapper.deleteById(id);
    }
}
