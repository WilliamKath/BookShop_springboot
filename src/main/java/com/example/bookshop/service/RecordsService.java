package com.example.bookshop.service;

import com.example.bookshop.bean.Book;
import com.example.bookshop.vo.FavoritesListVo;

import java.util.List;

public interface RecordsService {
    //查找该用户发布图书
    public List<Book> PublishRecordService(int sellerId);

    //查找该用户收藏图书列表
    public List<FavoritesListVo> FavoritesListService(int userId);

    //删除收藏记录根据收藏ID
    public int DeleteFavoriteService(int favoriteId);

    //新增收藏记录
    public int AddFavoriteService(int userId, int bookId);
}
