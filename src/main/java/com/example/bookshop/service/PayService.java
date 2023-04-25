package com.example.bookshop.service;

import com.alipay.api.AlipayApiException;
import com.example.bookshop.bean.AlipayBean;
import org.springframework.stereotype.Component;

public interface PayService {
    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}
