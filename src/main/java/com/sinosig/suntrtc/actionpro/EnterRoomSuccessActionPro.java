package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.DateUtil;
import com.sinosig.global.utils.StringUtil;
import com.sinosig.suntrtc.consts.SdkType;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.*;
import com.sinosig.suntrtc.service.InvitationRecordService;
import com.sinosig.suntrtc.service.OrderService;
import com.sinosig.suntrtc.service.RoomService;
import com.sinosig.suntrtc.service.VideoCallVideoInfoService;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 加入房间成功回写接口
 * @author: lukas
 */
@Service("enterRoomSuccessPro")
@Slf4j
public class EnterRoomSuccessActionPro extends BaseAction {

    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;
    @Autowired
    private RoomService roomService;
    @Autowired
    InvitationRecordService invitationRecordService;
    @Autowired
    OrderService orderService;
    @Autowired
    private VideoCallVideoInfoService videoCallVideoInfoService;
    @Autowired
    private SdkType sdkType;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        JSONObject resultContent = new JSONObject();
        try {
            log.info("EnterRoomSuccessActionPro接口入参: {}", in);
            JSONObject paramJson = JSON.parseObject(in);
            String orderId = paramJson.getString("orderId");
            String userName = paramJson.getString("userName");
            String surName = paramJson.getString("surName");
            String roomNo = paramJson.getString("roomNo");
            String streamId = paramJson.getString("streamId");
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(roomNo) || StringUtils.isBlank(streamId) || StringUtils.isBlank(surName) || StringUtils.isEmpty(orderId)) {
                return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }
            if (roomService.getRoomInfoByRoomNo(roomNo) == null) {
                return new ResultBean(Constants.RESCODE_VIDEO_END, Constants.RESMSG_VIDEO_END);
            }
            SDKParams sdkParams = sdkType;
            log.info("enterRoomSuccessPro获取参数[{}]", sdkParams.toString());
            BusinessInfo businessInfo = new BusinessInfo();
            businessInfo.setRoomNo(roomNo);
            // 根据房间好查询 流水号
            String currentBusinessNo = roomService.getCurrentBusinessNo(roomNo);
            if (StringUtils.isBlank(currentBusinessNo)) {
                businessInfo.setStartSource(enginBean.getRequestSource());
                businessInfo.setStartUser(userName);
                businessInfo.setBusinessNo(StringUtil.getUUID());
                roomService.createBusiness(businessInfo);
                roomService.updateRoomInfo(businessInfo.getBusinessNo(), businessInfo.getRoomNo());
            } else {
                businessInfo.setBusinessNo(currentBusinessNo);
            }


            String date2String = DateUtil.date2String(new Date());
            // 插入进入房间记录
            EventInfo eventInfo = new EventInfo();
            eventInfo.setBusinessNo(businessInfo.getBusinessNo());
            eventInfo.setEventOwner(userName);
            eventInfo.setEventType("进入房间");
            eventInfo.setEventTimestamp(date2String);
            roomService.occurEvent(eventInfo);

            BusinessVideoInfo businessVideoInfo = new BusinessVideoInfo();
            businessVideoInfo.setBusinessNo(businessInfo.getBusinessNo());
            businessVideoInfo.setUsername(userName);
            businessVideoInfo.setSurName(surName);
            businessVideoInfo.setStreamId(sdkParams.getAppId() + "_" + streamId);
            businessVideoInfo.setUserSource(enginBean.getRequestSource());
            businessVideoInfo.setOnlineStatus("0");
            businessVideoInfo.setSystemVersion(enginBean.getSystemVersion());
            VideoCallVideoInfo videoInfo = videoCallVideoInfoService.selectByBusinessNoAndUserName(businessInfo.getBusinessNo(), userName.substring(9));
            if (null != videoInfo) {
                // 如果房间中有这个人，更新房间人员数据
                videoInfo.setUserName(userName);
                videoCallVideoInfoService.updateByPrimaryKeySelective(videoInfo);
            } else {
                boolean result = roomService.startVideoRecord(businessVideoInfo);
                if (result) {
                    log.info("----- {} 进入房间 进入房间成功 ----- ", userName);
                }
            }

            EventInfo eventInfo2 = new EventInfo();
            eventInfo2.setBusinessNo(businessInfo.getBusinessNo());
            eventInfo2.setEventOwner(userName);
            eventInfo2.setEventType("开始录制");
            eventInfo2.setEventTimestamp(date2String);
            roomService.occurEvent(eventInfo2);
            RoomInfo roomInfoByBusinessNo = roomService.getRoomInfoByBusinessNo(businessInfo.getBusinessNo());
            List<VideoCallVideoInfo> videoCallVideoInfos = videoCallVideoInfoService.selectByBusinessNo(businessInfo.getBusinessNo());

            log.info("------查询的人员信息为：{}------", videoCallVideoInfos);
            JSONArray wsObject = new JSONArray();
            Map<String, Object> map;
            if (null != videoCallVideoInfos) {
                for (VideoCallVideoInfo videoCallVideoInfo : videoCallVideoInfos) {
                    map = new HashMap<>();
                    String userName1 = videoCallVideoInfo.getUserName();
                    map.put("userFullName", userName1);
                    map.put("userCode", userName1.substring(userName1.indexOf("-") + 1));
                    map.put("surName", videoCallVideoInfo.getSurName());
                    map.put("deviceType", videoCallVideoInfo.getUserSource());
                    map.put("sysVersion", videoCallVideoInfo.getSystemVersion());
                    wsObject.add(map);
                }

                JSONArray userGroup = new JSONArray();
                for (VideoCallVideoInfo videoCallVideoInfo : videoCallVideoInfos) {
                    String userName1 = videoCallVideoInfo.getUserName();
                    userGroup.add(userName1.split("-")[1] + "-" + userName1.split("-")[2]);
                }
                JSONObject res = new JSONObject();
                res.put("callStatus", Constants.RESCODE_DEVICE_PUBLICEINFO);
                res.put("callStatusMsg", wsObject);
                JSONObject result = imSendmsgServiceImpl.imSendmsg(res.toJSONString(), userGroup);
                if (Constants.STATUS_OK.equals(result.getString("ActionStatus"))) {
                    log.info("---------------------------用户公共信息发送成功！-------------------------");
                }
            }


            resultContent.put("businessNo", businessInfo.getBusinessNo());
            resultContent.put("homeowner", roomInfoByBusinessNo.getCreateUser());


            RequestVideoSysEntity requestVideoSysEntity = new RequestVideoSysEntity();
            OrderInfo orderInfo = orderService.getOrderInfoByBusinessNo(businessInfo.getBusinessNo());
            //add by XHH 2020-08-06 针对与工作台改造，增加返回环节标识
            resultContent.put("link", orderInfo.getLink());

            String resource = "1";
            String videoRole = "1";
            if (!orderInfo.getSurveyorName().equals(userName.substring(userName.indexOf("-") + 1, userName.length()))) {
                //如果创建房间和当前工号相同说明是发起者,不同说明是协作者
                videoRole = "2";
            } else {
                //modify by XHH 增加混流视频存储
                log.info("当前房间号->" + roomNo + "->>>>>开始混流");
                BusinessVideoInfo businessVideoInfoForVideoLeave = new BusinessVideoInfo();
                businessVideoInfoForVideoLeave.setBusinessNo(businessInfo.getBusinessNo());
                businessVideoInfoForVideoLeave.setUsername("MCUMix_" + userName);
                businessVideoInfoForVideoLeave.setSurName(surName);
                String mixStreamId = "MCUMix_" + roomNo + "_" + userName + "_stream";
                businessVideoInfoForVideoLeave.setStreamId(mixStreamId);
                businessVideoInfoForVideoLeave.setUserSource(enginBean.getRequestSource());
                businessVideoInfoForVideoLeave.setOnlineStatus("");
                businessVideoInfoForVideoLeave.setSystemVersion(enginBean.getSystemVersion());
                roomService.startVideoRecord(businessVideoInfoForVideoLeave);
                //TODO 开始混流
                // TencentCloudUtil.StartMCUMixTranscode(roomNo, sdkParams.getAk(), sdkParams.getSk(), sdkParams.getRegion(), sdkParams.getAppId(), mixStreamId);
            }
        } catch (Exception e) {
            log.error("enterRoomSuccessPro->error：", e);
            return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, resultContent);
    }


}
