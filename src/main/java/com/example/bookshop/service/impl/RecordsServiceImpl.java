package com.example.bookshop.service.impl;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.Favorites;
import com.example.bookshop.dao.RecordsDao;
import com.example.bookshop.service.RecordsService;
import com.example.bookshop.vo.FavoritesListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordsServiceImpl implements RecordsService {
    @Resource
    private RecordsDao recordsDao;

    @Override
    public List<Book> PublishRecordService(int sellerId) {
        return recordsDao.getPublishRecordList(sellerId);
    }

    @Override
    public List<FavoritesListVo> FavoritesListService(int userId) {
        return recordsDao.getFavoritesListByUserId(userId);
    }

    @Override
    public int DeleteFavoriteService(int favoriteId) {
        return recordsDao.deleteFavoriteById(favoriteId);
    }

    //当flag为1时表示收藏表中无该记录且插入成功，flag为0时表示表中有该数据或插入失败
    @Override
    public int AddFavoriteService(int userId, int bookId) {
        int flag = 0;
        Favorites favorites = recordsDao.findIsFavorite(userId, bookId);
        if ( favorites == null){
            flag = recordsDao.addFavorite(userId, bookId);
            if (flag != 1){
                flag = 0;
            }
        }
        return flag;
    }
}
