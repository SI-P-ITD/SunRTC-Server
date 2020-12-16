package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 发送IM消息工具类（单方发送）
 * @author: wangzi
 */
@Service("sendImActionPro")
@Slf4j
public class SendImActionPro extends BaseAction {

    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("sendImActionPro接口入参: {}", in);
        JSONObject paramJson = JSON.parseObject(in);
        String userCode = paramJson.getString("userCode");
        String callStatus = paramJson.getString("callStatus");
        String data = paramJson.getString("data");
        String terminal = paramJson.getString("terminal");
        JSONArray userGroup = new JSONArray();
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("callStatus", callStatus);
        jsonParam.put("data", data);
        userGroup.add(userCode + "-" + terminal);
        imSendmsgServiceImpl.imSendmsg(jsonParam.toJSONString(), userGroup);
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
    }
}
