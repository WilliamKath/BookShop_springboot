package com.example.bookshop.dao;

import com.example.bookshop.bean.Favorites;
import com.example.bookshop.bean.Order;
import com.example.bookshop.bean.OrderHistory;
import com.example.bookshop.bean.User;
import com.example.bookshop.vo.OrderDetailVo;
import com.example.bookshop.vo.OrderHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DealDao {
    //新增购物订单（即购物车内订单）
    public int addOrder(Order order);
    //查询是否已加入购物车
    public Order checkIsOrder(int bookId, int buyerId);
    //查询该用户购物车内商品详情
    public List<OrderDetailVo> getOrderDtlList(int buyerId);
    //通过orderId删除订单
    public int deleteOrderById(String orderId);

    //通过orderId查找订单
    public Order getOrderById(String orderId);

    //新增历史订单记录
    public int insertOrderHx(OrderHistory orderHistory);

    //查找 购买历史 交易订单
    public List<OrderHistoryVo> getOrderBuyHxList(int buyerId);

    //查找 出售历史 交易订单
    public List<OrderHistoryVo> getOrderSellHxList(int sellerId);

    //把该图书规则改为已出售 -> 3
    public void DownBook(int bookId);

    //更新订单状态
    public boolean setGoodsState(int goodsState, int orderhxId);

    //查找用户钱包内的余额
    public User findUserWallet(int userId);
    //增加用户钱包内的余额
    public int increaseWallet(User user);
}
