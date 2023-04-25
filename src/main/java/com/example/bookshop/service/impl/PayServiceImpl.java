package com.example.bookshop.service.impl;

import com.alipay.api.AlipayApiException;
import com.example.bookshop.bean.AlipayBean;
import com.example.bookshop.service.PayService;
import com.example.bookshop.util.AlipayUtile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private AlipayUtile alipayUtile;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipayUtile.pay(alipayBean);
    }
}
