package com.example.bookshop.service;
import com.example.bookshop.bean.User;


public interface UserService {

    public User LoginService(String userName, String userPassword);

    //注册
    public int SignUpService(User user);

    public User GetUserService(int userId);

    public int UpdateUserService(User user);
}
