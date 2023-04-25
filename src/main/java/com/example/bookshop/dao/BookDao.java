package com.example.bookshop.dao;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.BookImg;
import com.example.bookshop.bean.Category;
import com.example.bookshop.bean.User;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.BookVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookDao {
    //查询图书详情
    public List<BookDetailVo> getBookDtl(int bookId);
    //查询所有图书
    public List<BookVo> getAllBook(int categoryId, String bookName);
    //获取图书种类列表
    public List<Category> getCategoryList();
    //新增对应图书图片
    public boolean addBookImg(BookImg bookImg);
    //新增图书信息
    public boolean addBook(Book book);
    //重新图书信息
    public int changeBook(Book book);
    //通过图书ID查找图书
    public Book findBookById(int bookId);
}
