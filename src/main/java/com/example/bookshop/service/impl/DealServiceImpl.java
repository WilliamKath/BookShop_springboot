package com.example.bookshop.service.impl;

import com.example.bookshop.bean.Order;
import com.example.bookshop.bean.OrderHistory;
import com.example.bookshop.bean.User;
import com.example.bookshop.dao.AdminDao;
import com.example.bookshop.dao.DealDao;
import com.example.bookshop.service.DealService;
import com.example.bookshop.vo.OrderDetailVo;
import com.example.bookshop.vo.OrderHistoryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DealServiceImpl implements DealService {
    @Resource
    private DealDao dealDao;

    @Override
    public int AddOrderService(Order order) {
        int flag = 0;
        if ( dealDao.checkIsOrder(order.getBookId(), order.getBuyerId()) != null){
            //您已收藏数据
            flag = 2;
        } else {
            //新增收藏 -> 成功flag=1 失败flag=0
            flag = dealDao.addOrder(order);
        }
        return flag;
    }

    @Override
    public List<OrderDetailVo> GetOrderDtlService(int buyerId) {
        return dealDao.getOrderDtlList(buyerId);
    }

    @Override
    public int DeleteOrderService(String orderId) {
        return dealDao.deleteOrderById(orderId);
    }

    @Override
    public int AddOrderHxService(OrderHistory orderHistory) {
        String orderId = orderHistory.getOrderId();
        Order order = dealDao.getOrderById(orderId);    //根据orderId拿到了bookID sellerId buyerId
        //将orderHistory补充完整
        orderHistory.setBuyerId(order.getBuyerId());
        orderHistory.setSellerId(order.getSellerId());
        orderHistory.setBookId(order.getBookId());
        int flag = 0;
        flag = dealDao.insertOrderHx(orderHistory);
        if ( flag == 1){
            dealDao.DownBook(order.getBookId());    //下架图书
            if (dealDao.deleteOrderById(orderId) != 1){
                System.out.println("删除失败！订单号："+orderId);
            } else {
                System.out.println("删除成功！订单号："+orderId);
            }
        }
        return flag;
    }

    @Override
    public List<OrderHistoryVo> GetOrderBuyService(int buyerId) {
        return dealDao.getOrderBuyHxList(buyerId);
    }

    @Override
    public List<OrderHistoryVo> GetOrderSellerService(int sellerId) {
        return dealDao.getOrderSellHxList(sellerId);
    }

    @Override
    public boolean SetStateService(int goodsState, int orderhxId) {
        return dealDao.setGoodsState(goodsState, orderhxId);
    }

    @Override
    public int IncreaseWalletService(int userId, String totalAmount) {
        User user = dealDao.findUserWallet(userId);
        String wallet = user.getUserWallet();
        //转化为BigDecimal计算可不丢失精度
        BigDecimal bigDecimalWallet = new BigDecimal(wallet);
        BigDecimal bigDecimalAmount = new BigDecimal(totalAmount);
        String amountResult = bigDecimalWallet.add(bigDecimalAmount).toString();
        user.setUserWallet(amountResult);
        int flag = 0;
        flag = dealDao.increaseWallet(user);
        if (flag != 0){
            System.out.println("钱包余额由："+wallet+"，增加到："+amountResult);
        } else {
            System.out.println("增加钱包余额失败！");
        }
        return flag;
    }
}
