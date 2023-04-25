package com.example.bookshop.dao;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.Favorites;
import com.example.bookshop.vo.FavoritesListVo;
import com.example.bookshop.vo.OrderHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordsDao {
    //通过book表中的seller_id字段，查找对应user_id发布的所有闲置图书
    public List<Book> getPublishRecordList(int bookSeller);
    //通过userId查找收藏列表
    public List<FavoritesListVo> getFavoritesListByUserId(int userId);
    //通过favoriteId删除收藏记录
    public int deleteFavoriteById(int favoriteId);
    //新增收藏记录
    public int addFavorite(int userId, int bookId);
    //查询是否已收藏
    public Favorites findIsFavorite(int userId, int bookId);

    /*以下为订单交易记录*/
}
