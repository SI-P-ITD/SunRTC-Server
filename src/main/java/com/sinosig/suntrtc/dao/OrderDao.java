package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.OrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {

    OrderInfo selectOrderInfoByBusinessNo(String businessNo);
}
