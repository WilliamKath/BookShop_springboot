package com.example.bookshop.controller;

import com.alipay.api.AlipayApiException;
import com.example.bookshop.bean.AlipayBean;
import com.example.bookshop.service.PayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags={"支付接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class AlipayController {

    @Autowired PayService payService;

    @GetMapping(value = "/alipay")
    public String alipay(String outTradeNo, String subject, String totalAmount, String body) throws AlipayApiException
    {
        System.out.println("outTradeNo"+outTradeNo);
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        return payService.aliPay(alipayBean);
    }
}
