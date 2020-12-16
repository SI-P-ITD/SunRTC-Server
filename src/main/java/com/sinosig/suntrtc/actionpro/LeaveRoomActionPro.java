package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.DateUtil;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.suntrtc.consts.SdkType;
import com.sinosig.suntrtc.dao.VideoTimeMapper;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.*;
import com.sinosig.suntrtc.service.RoomService;
import com.sinosig.suntrtc.service.VideoCallEventsService;
import com.sinosig.suntrtc.service.VideoCallVideoInfoService;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import com.sinosig.tencentCloud.utils.TencentCloudUtil;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 离开房间通知接口
 * @author: lukas
 */
@Service("leaveRoomPro")
@Slf4j
public class LeaveRoomActionPro extends BaseAction {

    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;
    @Autowired
    private RoomService roomService;
    @Autowired
    private VideoCallVideoInfoService videoCallVideoInfoService;
    @Autowired
    private VideoCallEventsService videoCallEventsService;
    @Autowired
    private VideoTimeMapper videoTimevMapper;
    @Autowired
    SdkType sdkType;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("LeaveRoomActionPro接口入参: {}", in);
        JSONObject paramJson = JSON.parseObject(in);
        String orderId = paramJson.getString("orderId");
        String userName = paramJson.getString("userName");
        String businessNo = paramJson.getString("businessNo");
        String roomNo = paramJson.getString("roomNo");
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(roomNo) || StringUtils.isEmpty(orderId)) {
            return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
        }
        try {

            String date2String = DateUtil.date2String(new Date());
            // 判断是否为房主
            RoomInfo roomInfo = null;
            if (StringUtils.isNotBlank(businessNo)) {
                roomInfo = roomService.getRoomInfoByBusinessNo(businessNo);
            }
            if (roomInfo == null) {
                roomInfo = roomService.getRoomInfoByRoomNo(roomNo);
            }
            if (null == roomInfo) {
                return new ResultBean(Constants.RESCODE_VIDEO_END, Constants.RESMSG_VIDEO_END);
            }
            VideoCallVideoInfo videoCallVideoInfo = videoCallVideoInfoService.selectByBusinessNoAndUserName(businessNo, userName);
            if (null == videoCallVideoInfo) {
                return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
            }

            List<VideoCallVideoInfo> videoCallVideoInfos = videoCallVideoInfoService.selectByBusinessNo(businessNo);
            String substringUM = userName.substring(9);
            // 房主
            if (roomInfo.getCreateUser().equals(substringUM)) {
                log.info("----- 解散房间开始 --------");
                // 查询房间中在线人数，更改用户状态
                log.info("----- 更新房间其他人员状态开始 --------");
                RedisUtil.del(RedisUtil.getJedis(), "userCallStatus:" + substringUM);
                log.info("----- 房间人数为 ：{} ------", videoCallVideoInfos.size());
                for (VideoCallVideoInfo videoInfo : videoCallVideoInfos) {
                    String subUserName = videoInfo.getUserName().substring(9);
                    log.info("----- 房间内人员信息为 ：{} ------", videoInfo.toString());
                    updateUserEvent(videoInfo.getUserName(), businessNo, date2String);
                    // 更改其他人员推送房间解散消息
                    leaveRoomUpdateStatus(subUserName);
                }
                dissRoomAndEndMcuMix(roomNo, sdkType);
                dismissRoom(enginBean, userName, date2String, roomInfo);
                log.info("----- 更新房间其他人员状态结束 --------");


                log.info("----- 解散房间结束 --------");
            } else {
                log.info("----- 单人退出房间开始 --------");
                log.info("----- 房间人数为 ：{} ------", videoCallVideoInfos.size());
                if (videoCallVideoInfos.size() <= 2) {
                    for (VideoCallVideoInfo videoInfo : videoCallVideoInfos) {
                        String subUserName = videoInfo.getUserName().substring(videoInfo.getUserName().indexOf("-") + 1);
                        updateUserEvent(videoInfo.getUserName(), businessNo, date2String);
                        leaveRoomUpdateStatus(subUserName);
                        RedisUtil.del(RedisUtil.getJedis(), "Im_userCallStatus:" + subUserName);
                    }

                    RoomInfo roomInfoByRoomNo = roomService.getRoomInfoByRoomNo(roomNo);
                    if (null != roomInfoByRoomNo) {
                        dissRoomAndEndMcuMix(roomNo, sdkType);
                        dismissRoom(enginBean, userName, date2String, roomInfo);
                    }

                    log.info("----- 退出房间中所有人 --------");

                } else {
                    updateUserEvent(userName, businessNo, date2String);
                    // 更改 用户在线状态
                    String subUserName = userName.substring(userName.indexOf("-") + 1);
                    leaveRoomUpdateStatus(subUserName);
                }
                log.info("----- 单人退出房间结束 --------");
            }

        } catch (Exception e) {
            log.error("leaveRoomPro->error：", e);
        }


        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
    }

    /**
     * 解散房间和结束视频混流
     */
    private void dissRoomAndEndMcuMix(String roomNo, SDKParams sdkParams) throws TencentCloudSDKException {
        log.info("当前房间号退出->" + roomNo);
        //TODO 结束混流
        //TencentCloudUtil.StopMCUMixTranscode(roomNo, sdkParams.getAk(), sdkParams.getSk(), sdkParams.getRegion(), sdkParams.getAppId());
        TencentCloudUtil.dismissRoom(roomNo, sdkParams.getAk(), sdkParams.getSk(), sdkParams.getRegion(), sdkParams.getAppId());
    }

    /**
     * 更改 用户在线状态
     */
    private void leaveRoomUpdateStatus(String userName) {
        JSONObject messageObj = new JSONObject();
        messageObj.put("callStatus", Constants.DISMISS_ROOM_CODE);
        messageObj.put("callStatusMsg", Constants.DISMISS_ROOM_MSG);
        JSONArray userGroup = new JSONArray();
        userGroup.add(userName);
        imSendmsgServiceImpl.imSendmsg(messageObj.toJSONString(), userGroup);
    }


    /**
     * 解散房间
     */
    private void dismissRoom(EnginBean enginBean, String userName, String timestamp, RoomInfo roomInfo) {
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setBusinessNo(roomInfo.getCurrentBusinessNo());
        businessInfo.setEndType("手动解散房间");
        businessInfo.setDismissOperator(userName);
        businessInfo.setDismissSource(enginBean.getRequestSource());
        roomService.endBusiness(businessInfo);
        roomService.invaildRoom(roomInfo.getRoomNo());

        EventInfo eventInfo = new EventInfo();
        eventInfo.setBusinessNo(roomInfo.getCurrentBusinessNo());
        eventInfo.setEventOwner(userName);
        eventInfo.setEventType("手动解散房间");
        eventInfo.setEventTimestamp(timestamp);
        roomService.occurEvent(eventInfo);
    }

    /**
     * 记录视频事件
     */
    private void updateUserEvent(String userName, String businessNo, String timestamp) {
        try {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setBusinessNo(businessNo);
            eventInfo.setEventOwner(userName);
            eventInfo.setEventType("退出房间");
            eventInfo.setEventTimestamp(timestamp);
            roomService.occurEvent(eventInfo);
            // 离线状态更新
            videoCallVideoInfoService.updateOnlineStatus(businessNo, userName);
            if (cumulativeDuration(businessNo, userName)) {
                log.info("视频通话累计时长处理成功：userName：{}，businessNo：{}", userName, businessNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算视频通话累计时长
     */
    public boolean cumulativeDuration(String businessNo, String username) {
        boolean result = false;
        try {
            EventInfo eventInfo = videoCallEventsService.selectEventInfo(businessNo, username, "进入房间");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (null != eventInfo) {
                long times = (System.currentTimeMillis() - sdf.parse(eventInfo.getCreateTime()).getTime()) / 1000;
                String pastDate = DateUtil.getNowDate("");
                VideoTimeInfo videoTimeInfo = videoTimevMapper.selectVideoTime(pastDate);
                if (videoTimeInfo == null) {
                    result = videoTimevMapper.insertVideoTime(pastDate, times);
                } else {
                    result = videoTimevMapper.updateVideoTime(pastDate, times);
                }
            }
        } catch (Exception e) {
            log.error("视频通话累计时长处理异常-->{}", e.getMessage());
        }
        return result;
    }
}
