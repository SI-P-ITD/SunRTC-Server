package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.*;
import com.sinosig.suntrtc.service.OrderService;
import com.sinosig.suntrtc.service.PictureInfoService;
import com.sinosig.suntrtc.service.VideoCallVideoInfoService;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 前端上传阿里云图片回调接口
 * @author: lukas
 */
@Service("uploadPicCallbackPro")
@Slf4j
public class UploadPicCallbackActionPro extends BaseAction {

    @Autowired
    private PictureInfoService pictureInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private VideoCallVideoInfoService videoCallVideoInfoService;
    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;


    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("uploadPicCallbackPro接口入参: {}", in);
        try {
            JSONObject paramJson = JSON.parseObject(in);
            String sdkType = paramJson.getString("sdkType");
            PictureInfo pictureInfo = JSON.parseObject(in, PictureInfo.class);
            pictureInfo.setPictureSource(enginBean.getRequestSource());
            pictureInfoService.savePictureInfo(pictureInfo);
            OrderInfo orderInfo = orderService.getOrderInfoByBusinessNo(pictureInfo.getBusinessNo());
            log.info("当前房间信息为->orderInfo:" + JSON.toJSONString(orderInfo));
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("orderId", orderInfo.getOrderId());
            jsonParam.put("surveyorNo", orderInfo.getSurveyorName());
            jsonParam.put("callStatus", Constants.PICTURE_SHOW_CODE);
            List<String> getlistPicture = pictureInfoService.getlistPicture(pictureInfo.getBusinessNo());
            List<String> listRes = new ArrayList<>();
            //去反斜杠
            if (null != getlistPicture) {
                for (String str : getlistPicture) {
                    listRes.add(str.replace("\\\\", ""));
                }
            }
            jsonParam.put("fileUrl", pictureInfo.getPicturePath());
            jsonParam.put("count", listRes.size());
            // 根据business number查询房间号，根据房间号查询，房间中所有人根据用户名查询，根据用户名拿到session推送所有信息。
            List<VideoCallVideoInfo> videoCallVideoInfoList = videoCallVideoInfoService.selectByBusinessNo(pictureInfo.getBusinessNo());
            if (videoCallVideoInfoList != null) {
                log.info("推送图片人员 sendPictureInfo：结果：{}", videoCallVideoInfoList.toString());
                for (int i = 0; i < videoCallVideoInfoList.size(); i++) {
                    VideoCallVideoInfo videoCallVideoInfo = videoCallVideoInfoList.get(i);
                    String subUserName = videoCallVideoInfo.getUserName().substring(9);
                    this.sendMQMsg(sdkType, subUserName, jsonParam);
                    log.info("向其他人员推送  " + i + " ：" + subUserName + " ----  sendPictureInfo：结果：{}", jsonParam.toJSONString());
                }
            }
        } catch (Exception e) {
            log.error("UploadPicCallbackActionPro->error：" + e);
            return new ResultBean(Constants.RESCODE_ANALYZE_PARM_FAILED, Constants.RESMSG_ANALYZE_PARM_FAILED);
        }

        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
    }

    /**
     * 发送 IM 信息
     */
    private void sendMQMsg(String sdkType, String userCode, JSONObject jsonParam) {
        // 向房间中的人推送 图片
        JSONArray userGroup = new JSONArray();
        userGroup.add(userCode);
        imSendmsgServiceImpl.imSendmsg(jsonParam.toJSONString(), userGroup);
    }


}
