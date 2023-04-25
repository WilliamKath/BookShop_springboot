package com.example.bookshop.controller;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.Order;
import com.example.bookshop.bean.OrderHistory;
import com.example.bookshop.service.DealService;
import com.example.bookshop.util.SnowFlake;
import com.example.bookshop.vo.OrderDetailVo;
import com.example.bookshop.vo.OrderHistoryVo;
import com.example.bookshop.vo.ResMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Api(tags = {"交易相关接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class DealController {
    @Autowired
    private DealService dealService;
    //雪花算法生成唯一的订单ID
    private SnowFlake snowFlake=new SnowFlake(2,3);

    /**
     * 加入购物车
     * 可通过buyerId与bookId判断是否已存在购物车内
     * 同时通过buyerId与sellerId判断买卖是否为同一人
     * @param order
     * @return
     */
    @ApiOperation(value = "加入购物车")
    @PutMapping("addOrder")
    public ResMsg addOrder(Order order){
        ResMsg resMsg = new ResMsg();
        String oid=String.valueOf(snowFlake.nextId());
        order.setOrderId(oid);
        if( order.getBuyerId() == order.getSellerId()){
            resMsg.setErrcode("3");
            resMsg.setErrmsg("您无法添加自己的图书！");
            return resMsg;
        }
        int flag = dealService.AddOrderService(order);
        if (flag == 0){
            //flag为0，加入购物车失败
            resMsg.setErrcode("1");
            resMsg.setErrmsg("加入购物车失败！");
        } else if ( flag == 2 ) {
            //flag为2，图书已在购物车
            resMsg.setErrcode("2");
            resMsg.setErrmsg("该图书已加入购物车！");
        } else {
            //flag为1，加入购物车成功
            resMsg.setErrcode("0");
            resMsg.setErrmsg("成功加入购物车");
            System.out.println("成功加入购物车，数据为："+order);
        }
        return resMsg;
    }

    /**
     * 购物车内商品的详情
     * 通过buyerId（买家ID）进行查询
     * @param buyerId
     * @return
     */
    @ApiOperation(value = "查询购物车详情列表")
    @GetMapping("getOrderList")
    public ResMsg getOrderList(@RequestParam(value = "buyerId") int buyerId){
        ResMsg resMsg = new ResMsg();
        List<OrderDetailVo> orderDtlList = dealService.GetOrderDtlService(buyerId);
        if (orderDtlList.size() == 0){
            resMsg.setErrmsg("您的购物车为空！");
            resMsg.setErrcode("1");
        } else {
            resMsg.setErrmsg("查询成功！");
            resMsg.setErrcode("0");
            resMsg.setResult(orderDtlList);
        }
        return resMsg;
    }

    /**
     * 通过orderId订单ID删除订单
     * @param orderId
     * @return
     */
    @ApiOperation(value = "删除购物车订单")
    @DeleteMapping("/deleteOrder")
    public ResMsg deleteOrder(@RequestParam(value = "orderId")String orderId){
        ResMsg resMsg = new ResMsg();
        System.out.println("本次删除的收藏记录ID："+orderId);
        int flag = dealService.DeleteOrderService(orderId);
        if (flag == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("删除失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("删除成功！");
        }
        return resMsg;
    }

    @ApiOperation(value = "支付成功后回调")
    @PostMapping("/payReturn")
    public ResMsg addOrderHx(@RequestParam(value = "out_trade_no") String orderId, @RequestParam(value = "trade_no") String payId,
                             @RequestParam(value = "total_amount") String totalAmount, @RequestParam(value = "timestamp")Timestamp createTime){
        ResMsg resMsg = new ResMsg();
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrderId(orderId);
        orderHistory.setPayId(payId);
        orderHistory.setTotalAmount(totalAmount);
        orderHistory.setGoodsState(0); //订单默认设置为 0-> 待发货
        orderHistory.setCreateTime(createTime.toString());
        if (dealService.AddOrderHxService(orderHistory) != 1){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("支付后插入失败！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("支付后插入成功！");
        }
        return resMsg;
    }

    @ApiOperation(value = "查询该用户的购买记录")
    @GetMapping("getBuyList")
    public ResMsg getBuyOrderList(@RequestParam(value = "buyerId")int buyerId){
        ResMsg resMsg = new ResMsg();
        List<OrderHistoryVo> BuyOrderList = dealService.GetOrderBuyService(buyerId);
        if ( BuyOrderList == null || BuyOrderList.size() == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("记录查询失败或为空！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("记录查询成功！");
        }
        resMsg.setResult(BuyOrderList);
        System.out.println("查询到的购买记录共有:"+BuyOrderList.size());
        System.out.println(BuyOrderList);
        return resMsg;
    }

    @ApiOperation(value = "查询该用户的出售记录")
    @GetMapping("getSellList")
    public ResMsg getSellOrderList(@RequestParam(value = "sellerId")int sellerId){
        ResMsg resMsg = new ResMsg();
        List<OrderHistoryVo> SellOrderList = dealService.GetOrderSellerService(sellerId);
        if ( SellOrderList == null || SellOrderList.size() == 0){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("记录查询失败或为空！");
        } else {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("记录查询成功！");
        }
        resMsg.setResult(SellOrderList);
        System.out.println("查询到的出售记录共有:"+SellOrderList.size());
        System.out.println(SellOrderList);
        return resMsg;
    }

    @ApiOperation(value = "更改订单状态")
    @PutMapping("setState")
    public ResMsg setGoodsState(@RequestParam(value = "goodsState") int goodsState, @RequestParam(value = "orderhxId") int orderhxId,
                                @RequestParam(value = "totalAmount") String totalAmount, @RequestParam(value = "sellerId") int sellerId){
        ResMsg resMsg = new ResMsg();
        if (!dealService.SetStateService(goodsState, orderhxId)){
            resMsg.setErrcode("1");
            resMsg.setErrmsg("更新状态失败");
        } else {
            //如果拿到的状态为已收货，则将余额加入卖家钱包
            if (goodsState == 2){
                if (dealService.IncreaseWalletService(sellerId,totalAmount)==1){
                    System.out.println("钱包余额已更新！");
                }
            }
            resMsg.setErrcode("0");
            resMsg.setErrmsg("更新状态成功");
        }
        System.out.println("更新一条订单状态：orderhxId"+orderhxId+"，更新为："+goodsState);
        return resMsg;
    }


}
