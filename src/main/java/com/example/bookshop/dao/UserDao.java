package com.example.bookshop.dao;

import com.example.bookshop.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    public User getUserByMessage(String userName,String userPassword);

    public List<User> findAll();

    //注册
    public boolean UserSignUp(User user);

    //通过用户名查找用户（用于实现防止同一用户名重复注册）
    public User getUserByUsername(String userName);

    public User getUserById(int userId);

    public int updateUser(User user);
}
