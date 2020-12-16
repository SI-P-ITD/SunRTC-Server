package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import com.sinosig.suntrtc.service.VideoCallVideoInfoService;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 发送IM消息工具类（向房间内所有人发送）
 * @author: wangzi
 */
@Service("sendImMultiActionPro")
@Slf4j
public class SendImMultiActionPro extends BaseAction {

    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;
    @Autowired
    private VideoCallVideoInfoService videoCallVideoInfoService;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("SendImMultiActionPro接口入参: {}", in);
        JSONObject paramJson = JSON.parseObject(in);
        String businessNo = paramJson.getString("businessNo");
        String message = paramJson.getString("message");
        String sender = paramJson.getString("sender");
        if (StringUtils.isBlank(businessNo) || StringUtils.isBlank(message) || StringUtils.isBlank(sender)) {
            return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
        }
        List<VideoCallVideoInfo> videoCallVideoInfos = videoCallVideoInfoService.selectByBusinessNo(businessNo);
        log.info("------查询的在线人员信息为：{}------", videoCallVideoInfos);
        String userName = null;
        JSONArray userGroup = new JSONArray();
        if (videoCallVideoInfos != null && videoCallVideoInfos.size() > 0) {
            for (VideoCallVideoInfo videoCallVideoInfo : videoCallVideoInfos) {
                userName = videoCallVideoInfo.getUserName();
                userGroup.add(userName.split("-")[1] + "-" + userName.split("-")[2]);
            }
            JSONObject res = new JSONObject();
            res.put("callStatus", Constants.RESCODE_MULTI_MESSAGE_SEND);
            res.put("data", message);
            res.put("sender", sender);
            JSONObject result = imSendmsgServiceImpl.imSendmsg(res.toJSONString(), userGroup);
            if (Constants.STATUS_OK.equals(result.getString("ActionStatus"))) {
                log.info("---------------------------多方发送成功！-------------------------");
            }
            return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
        }
        return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL);
    }
}
