package com.example.bookshop.service.impl;

import com.example.bookshop.bean.User;
import com.example.bookshop.dao.UserDao;
import com.example.bookshop.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    public UserDao userDao;

    @Override
    public User LoginService(String userName, String userPassword){
        return userDao.getUserByMessage(userName,userPassword);
    }

    @Override
    public int SignUpService(User user) {
        int code;
        //如果账号已存在则令代码为1
        if (userDao.getUserByUsername(user.getUserName()) != null){
            code = 1;
        } else {
            //注册成功返回代码0
            if (userDao.UserSignUp(user)){
                code = 0;
            } else {
                code = 2;    //注册失败返回代码2
            }
        }
        return code;
    }

    @Override
    public User GetUserService(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public int UpdateUserService(User user) {
        return userDao.updateUser(user);
    }
}
