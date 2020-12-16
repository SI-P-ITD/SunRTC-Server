package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.OrderDao;
import com.sinosig.suntrtc.entity.OrderInfo;
import com.sinosig.suntrtc.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Override
    public OrderInfo getOrderInfoByBusinessNo(String businessNo) {
        return orderDao.selectOrderInfoByBusinessNo(businessNo);
    }
}
