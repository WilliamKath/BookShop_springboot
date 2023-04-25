package com.example.bookshop.dao;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.User;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.OrderListVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDao {
    public List<BookDetailVo> getBookList();

    public int updateRelease(Book book);

    public List<User> getUserList();

    public int setUserRole(User user);

    //获取订单详情列表
    public List<OrderListVo> getOrderList();
}
