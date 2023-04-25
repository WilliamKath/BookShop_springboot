package com.example.bookshop.controller;

import com.example.bookshop.vo.ResMsg;
import com.example.bookshop.bean.User;
import com.example.bookshop.dao.UserDao;
import com.example.bookshop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags={"用户接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class UserController {
    @Autowired
    UserDao userDao;

    @Autowired
    private UserService userService;

    /**
     * 登录方法
     * @param user
     * @return
     */
    @ApiOperation(value="用户登录")
    @PostMapping("/login")
    public ResMsg login(@RequestBody User user){
        ResMsg resMsg = new ResMsg();
        User us = userService.LoginService(user.getUserName(), user.getUserPassword());
        //User us = userDao.getUserByMessage(user.getUserName(), user.getUserPassword());
        if(us == null){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("账号或密码错误！");
        }else{
            resMsg.setErrmsg("登陆成功！");
            resMsg.setResult(us);
        }
        return resMsg;
    }

    /**
     * 注册方法
     * @param user
     * @return
     */
    @ApiOperation(value="用户注册")
    @PostMapping("/signup")
    public ResMsg signup(@RequestBody User user){
        ResMsg resMsg = new ResMsg();
        int flag = userService.SignUpService(user);
        if ( flag == 1){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("用户已存在，请登录！");
            resMsg.setResult(null);
        } else if ( flag == 2){
            resMsg.setErrcode("2");
            resMsg.setErrmsg("注册失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("注册成功，请登录！");
        }
        return resMsg;
    }

    /**
     * 通过用户ID查找用户相关信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "查找用户信息")
    @GetMapping("getuser")
    public ResMsg getUserById(@RequestParam(value = "userId") int userId){
        ResMsg resMsg = new ResMsg();
        User user = userService.GetUserService(userId);
        if (user == null){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("查询用户失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("成功查询该用户！");
        }
        resMsg.setResult(user);
        System.out.println("userId:"+userId);
        System.out.println(user);
        return resMsg;
    }

    /**
     * 通过用户ID修改用户信息
     */
    @ApiOperation(value = "修改用户信息")
    @PutMapping("updateuser")
    public ResMsg updateUserById(@RequestBody User user){
        ResMsg resMsg = new ResMsg();
        System.out.println("本次修改的信息为："+user);
        int flag = userService.UpdateUserService(user);
        if (flag == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("修改失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("修改成功！");
        }
        User newUser = userService.GetUserService(user.getUserId());
        resMsg.setResult(newUser);
        return resMsg;
    }
}
