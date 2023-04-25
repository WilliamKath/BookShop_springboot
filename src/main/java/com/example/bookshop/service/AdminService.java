package com.example.bookshop.service;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.User;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.OrderListVo;

import java.util.List;

public interface AdminService {
    public List<BookDetailVo> BookListService();

    public int ReleaseService(Book book);

    public List<User> UserListService();

    public int UserRoleService(User user);

    public List<OrderListVo> OrderListService();
}
