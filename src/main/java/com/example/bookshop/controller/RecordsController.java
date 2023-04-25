package com.example.bookshop.controller;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.Favorites;
import com.example.bookshop.bean.User;
import com.example.bookshop.service.RecordsService;
import com.example.bookshop.vo.FavoritesListVo;
import com.example.bookshop.vo.ResMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"查询记录接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class RecordsController {
    @Autowired
    private RecordsService recordsService;

    /**
     * 通过sellerId查询该用户发布的所有闲置图书
     * @param sellerId
     * @return
     */
    @ApiOperation(value = "查询该用户发布图书记录")
    @GetMapping("publishRecord")
    public ResMsg getPublishRecord(@RequestParam(value = "sellerId")int sellerId){
        ResMsg resMsg = new ResMsg();
        List<Book> records = recordsService.PublishRecordService(sellerId);
        if ( records == null || records.size() == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("记录查询失败或为空！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("记录查询成功！");
        }
        resMsg.setResult(records);
        System.out.println("size:"+records.size());
        System.out.println(records);
        return resMsg;
    }

    /**
     * 通过userId查询该用户收藏图书列表
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询该用户收藏图书列表")
    @GetMapping("favoritesList")
    public ResMsg getFavoritesList(@RequestParam(value = "userId")int userId){
        ResMsg resMsg = new ResMsg();
        List<FavoritesListVo> favoritesList = recordsService.FavoritesListService(userId);
        if ( favoritesList == null || favoritesList.size() == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("收藏查询失败或为空！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("收藏查询成功！");
        }
        resMsg.setResult(favoritesList);
        System.out.println("size:"+favoritesList.size());
        System.out.println(favoritesList);
        return resMsg;
    }

    /**
     * 通过收藏ID删除收藏记录
     * @param favoritesId
     * @return
     */
    @ApiOperation(value = "删除收藏记录")
    @DeleteMapping("/deleteFavorite")
    public ResMsg deleteFavoriteById(@RequestParam(value = "favoritesId")int favoritesId){
        ResMsg resMsg = new ResMsg();
        System.out.println("本次删除的收藏记录ID："+favoritesId);
        int flag = recordsService.DeleteFavoriteService(favoritesId);
        if (flag == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("删除失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("删除成功！");
        }
        return resMsg;
    }

    /**
     * 查询并插入收藏记录
     * @param userId
     * @param bookId
     * @return
     */
    @ApiOperation(value = "插入收藏记录")
    @PostMapping("/addfavorite")
    public ResMsg addFavorite(@RequestParam(value = "userId") int userId, @RequestParam(value = "bookId") int bookId){
        ResMsg resMsg = new ResMsg();
        System.out.println("userId="+userId+"bookId="+bookId);
        int flag = recordsService.AddFavoriteService(userId, bookId);
        if (flag == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("您已收藏！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("收藏成功！");
        }
        return resMsg;
    }

}
