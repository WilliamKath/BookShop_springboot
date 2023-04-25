package com.example.bookshop.service.impl;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.BookImg;
import com.example.bookshop.bean.Category;
import com.example.bookshop.dao.BookDao;
import com.example.bookshop.service.BookService;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.BookVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;

    @Override
    public List<BookDetailVo> BookDtlService(int bookId) {
        return bookDao.getBookDtl(bookId);
    }

    @Override
    public List<BookVo> AllBookService(int categoryId, String bookName) {
        return bookDao.getAllBook(categoryId, bookName);
    }

    @Override
    public List<Category> AllCategoryService() {
        return bookDao.getCategoryList();
    }

    @Override
    public boolean InsertBookService(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public boolean InsertBookImgService(BookImg bookImg) {
        return bookDao.addBookImg(bookImg);
    }

    @Override
    public int ChangeBookService(Book book) {
        return bookDao.changeBook(book);
    }

    @Override
    public Book GetBookByIdService(int bookId) {
        return bookDao.findBookById(bookId);
    }
}
