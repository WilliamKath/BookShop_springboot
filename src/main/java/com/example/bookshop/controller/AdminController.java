package com.example.bookshop.controller;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.User;
import com.example.bookshop.service.AdminService;
import com.example.bookshop.vo.BookDetailVo;
import com.example.bookshop.vo.OrderListVo;
import com.example.bookshop.vo.ResMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"后台管理接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取所有图书
     * @return
     */
    @ApiOperation(value="获取图书清单")
    @GetMapping("getBookList")
    public ResMsg getBookList(){
        ResMsg resMsg = new ResMsg();
        List<BookDetailVo> bookList = adminService.BookListService();
        if ( bookList != null || bookList.size() != 0){
            resMsg.setErrcode("0");
            resMsg.setErrmsg("成功获取列表！");
        } else {
            resMsg.setErrcode("1");
            resMsg.setErrmsg("获取列表失败！");
        }
        resMsg.setResult(bookList);
        return resMsg;
    }

    /**
     * 通过bookId修改bookRelease审核
     * @param bookId
     * @param bookrelease
     * @return
     */
    @ApiOperation(value = "审核图书")
    @PutMapping("releaseBook")
    public ResMsg updateRelease(@RequestParam(value = "bookId") int bookId, @RequestParam(value = "bookrelease") int bookrelease){
        ResMsg resMsg = new ResMsg();
        Book book = new Book();
        book.setBookId(bookId);
        book.setBookRelease(bookrelease);
        System.out.println("本次修改的图书为："+book.getBookId());
        System.out.println("审核状态修改为："+book.getBookRelease());
        int flag = adminService.ReleaseService(book);
        if (flag != 1){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("审核失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("已修改审核状态");
        }
        return resMsg;
    }

    /**
     * 查询用户列表
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("getUserList")
    public ResMsg getUserList(){
        ResMsg resMsg = new ResMsg();
        List<User> userList = adminService.UserListService();
        if (userList != null || userList.size() != 0){
            resMsg.setErrcode("0");
            resMsg.setErrmsg("查询成功！");
        } else {
            resMsg.setErrcode("1");
            resMsg.setErrmsg("查询失败！");
        }
        resMsg.setResult(userList);
        return resMsg;
    }

    @ApiOperation(value = "修改用户权限")
    @PutMapping("setRole")
    public ResMsg setUserRole(@RequestParam(value = "userId") int userId, @RequestParam(value = "userrole") int userRole){
        ResMsg resMsg = new ResMsg();
        User user = new User();
        user.setUserId(userId);
        user.setUserRole(userRole);
        System.out.println("本次修改的用户为："+user.getUserId());
        System.out.println("权限状态修改为："+user.getUserRole());
        int flag = adminService.UserRoleService(user);
        if (flag != 1){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("修改失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("已修改用户权限");
        }
        return resMsg;
    }

    @ApiOperation(value="获取订单清单")
    @GetMapping("getOrderHxList")
    public ResMsg getOrderHxList(){
        ResMsg resMsg = new ResMsg();
        List<OrderListVo> orderList = adminService.OrderListService();
        if ( orderList != null || orderList.size() != 0){
            resMsg.setErrcode("0");
            resMsg.setErrmsg("成功获取列表！");
        } else {
            resMsg.setErrcode("1");
            resMsg.setErrmsg("获取列表失败！");
        }
        resMsg.setResult(orderList);
        return resMsg;
    }

}
