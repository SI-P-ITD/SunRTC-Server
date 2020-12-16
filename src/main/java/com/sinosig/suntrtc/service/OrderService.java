package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.OrderInfo;

public interface OrderService {

    OrderInfo getOrderInfoByBusinessNo(String businessNo);
}
