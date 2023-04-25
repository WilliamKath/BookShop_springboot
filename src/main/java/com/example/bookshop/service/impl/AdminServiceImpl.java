package com.example.bookshop.service.impl;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.User;
import com.example.bookshop.dao.AdminDao;
import com.example.bookshop.service.AdminService;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.OrderListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminDao adminDao;

    @Override
    public List<BookDetailVo> BookListService() {
        return adminDao.getBookList();
    }

    @Override
    public int ReleaseService(Book book) {
        return adminDao.updateRelease(book);
    }

    @Override
    public List<User> UserListService() {
        return adminDao.getUserList();
    }

    @Override
    public int UserRoleService(User user) {
        return adminDao.setUserRole(user);
    }

    @Override
    public List<OrderListVo> OrderListService() {
        return adminDao.getOrderList();
    }
}
