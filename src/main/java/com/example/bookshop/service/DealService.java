package com.example.bookshop.service;

import com.example.bookshop.bean.Order;
import com.example.bookshop.bean.OrderHistory;
import com.example.bookshop.vo.OrderDetailVo;
import com.example.bookshop.vo.OrderHistoryVo;

import java.util.List;

public interface DealService {
    //新增订单（加入购物车
    public int AddOrderService(Order order);
    //查询购物车内商品详情
    public List<OrderDetailVo> GetOrderDtlService(int buyerId);
    //删除订单根据订单ID
    public int DeleteOrderService(String orderId);

    //新增一条历史订单数据
    public int AddOrderHxService(OrderHistory orderHistory);

    //查找购买历史交易记录
    public List<OrderHistoryVo> GetOrderBuyService(int buyerId);

    //查找出售历史交易记录
    public List<OrderHistoryVo> GetOrderSellerService(int sellerId);

    //更新订单状态
    public boolean SetStateService(int goodsState, int orderhxId);

    //增加用户钱包内的余额
    public int IncreaseWalletService(int userId, String totalAmount);
}
