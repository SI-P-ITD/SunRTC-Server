package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.OrderInfo;
import com.sinosig.suntrtc.entity.RoomInfo;
import com.sinosig.suntrtc.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 房间创建接口
 * @author: lukas
 */
@Service("createRoomPro")
@Slf4j
public class CreateRoomActionPro extends BaseAction {

    @Autowired
    private RoomService roomService;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        RoomInfo roomInfo;
        try {
            log.info("CreateRoomActionPro接口入参: {}", in);
            JSONObject paraJson = JSON.parseObject(in);
            String password = paraJson.getString("password");
            String needPassword = paraJson.getString("needPassword");
            if (!"Y".equals(needPassword)) {
                password = null;
            }
            String userName = paraJson.getString("userName");
            String orderId = paraJson.getString("orderId");
            String reportName = paraJson.getString("reportName");
            String phone = paraJson.getString("phone");
            String licenceNo = paraJson.getString("licenceNo");
            String damageAddress = paraJson.getString("damageAddress");
            String link = paraJson.getString("link");
            String estimateNo = paraJson.getString("estimateNo");

            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(reportName)) {
                return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }

            if (StringUtils.isBlank(userName) || StringUtils.isBlank(needPassword) || ("Y".equals(needPassword) && StringUtils.isBlank(password))) {
                return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }

            roomInfo = new RoomInfo();
            roomInfo.setCreateUser(userName);
            roomInfo.setNeedPassword(needPassword);
            roomInfo.setPassword(password);
            roomInfo.setCreateSource(enginBean.getRequestSource());
            roomInfo.setStatus("有效");
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo.setCreateUser(userName);
            orderInfo.setReportName(reportName);
            orderInfo.setPhone(phone);
            orderInfo.setLicenceNo(licenceNo);
            orderInfo.setDamageAddress(damageAddress);
            orderInfo.setLink(link);
            orderInfo.setEstimateNo(estimateNo);
            orderInfo.setSurveyorName(userName);
            roomService.createRoom(roomInfo);
            roomService.relatedCases(roomInfo, orderInfo);
        } catch (Exception e) {
            log.error("createRoomPro->error：" + e);
            return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, roomInfo);
    }


}
