package com.example.bookshop.service;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.BookImg;
import com.example.bookshop.bean.Category;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.BookVo;

import java.util.List;

public interface BookService {
    //查找图书详情
    public List<BookDetailVo> BookDtlService(int bookId);

    //查找所有图书
    public List<BookVo> AllBookService(int categoryId, String bookName);

    //查找所有图书种类
    public List<Category> AllCategoryService();

    //添加图书信息
    public boolean InsertBookService(Book book);

    //添加图书图片
    public boolean InsertBookImgService(BookImg bookImg);
    
    //修改图书
    public int ChangeBookService(Book book);
    
    //查找图书通过图书ID
    public Book GetBookByIdService(int bookId);
}
