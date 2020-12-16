package com.sinosig.suntrtc.sendtemplate.thirdpartysend;/**
 * Create by XHH
 * Date 2020-06-04
 */

import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.HttpPostUtil;
import com.sinosig.suntrtc.entity.RequestVideoSysEntity;
import com.sinosig.suntrtc.sendtemplate.SunRtcSendRequestData;
import lombok.extern.slf4j.Slf4j;


/**
 * Create by XHH
 * Date 2020-06-04
 */
@Slf4j
public class VideoSysThirdPartySunRtcSend extends ThirdPartySunRtcSend {


    @Override
    public void pre() {
        log.info("进入给videosys服务推送消息");

    }

    /**
     * @param data
     * @name: 给videosys发送参数接口
     * @description: TODO
     * @date: 2020-06-04 18:13
     * @auther: XHH
     * @return: void
     */
    @Override
    public void sendMessage(SunRtcSendRequestData data) {
        log.info("VideoSysThirdPartySunRtcSend请求参数为->:{}" + JSONObject.toJSONString(data));
        RequestVideoSysEntity requestVideoSysEntity = (RequestVideoSysEntity) data.getObj();
        JSONObject respResult = null;
        JSONObject jsonParam = new JSONObject();
        long startTime = 0L;
        String url = "";
        switch (data.getToUserCode()) {
            case (Constants.SENDCASEINFO):
                //发送案件信息
                jsonParam.put("caseNo", requestVideoSysEntity.getCaseNo());
                jsonParam.put("surveyorNo", requestVideoSysEntity.getSurveyorNo());
                jsonParam.put("licenseNo", "");
                jsonParam.put("videoId", requestVideoSysEntity.getVideoId());
                jsonParam.put("roomId", requestVideoSysEntity.getRoomId());
                jsonParam.put("userPhone", requestVideoSysEntity.getUserPhone());
                jsonParam.put("caseSource", requestVideoSysEntity.getResource());
                jsonParam.put("videoRole", requestVideoSysEntity.getVideoRole());
                jsonParam.put("reporterName", requestVideoSysEntity.getReportName());
                log.info("SENDCASEINFO发送的报文为: {}"+jsonParam.toJSONString());
                url = "http://" + requestVideoSysEntity.getSnedUrl() + Constants.SENDCASEINFO + ".do";
                startTime = System.currentTimeMillis();
                log.info("发送的地址为: {}" + url);
                respResult = HttpPostUtil.httpPost(url, jsonParam);
                log.info(url + ":交互时间为: {}" + (System.currentTimeMillis() - startTime));
                requestVideoSysEntity.setJSONObject(respResult);
                data.setObj(requestVideoSysEntity);
                break;
            case (Constants.SENDPICTUREINFO):
                //发送图片信息
                jsonParam.put("caseNo", requestVideoSysEntity.getCaseNo());
                jsonParam.put("surveyorNo", requestVideoSysEntity.getSurveyorNo());
                jsonParam.put("fileUrl", requestVideoSysEntity.getFileUrl());
                jsonParam.put("link", requestVideoSysEntity.getLink());
                jsonParam.put("estimateNo", requestVideoSysEntity.getEestimateNo());
                // 20200924 wangzi 同步图片的同时增加ID的传递
                jsonParam.put("id", requestVideoSysEntity.getId());
                log.info("发送的同步图片入参为:{}" ,jsonParam.toJSONString());
                startTime = System.currentTimeMillis();
                url = "http://" + requestVideoSysEntity.getSnedUrl() + Constants.SENDPICTUREINFO + ".do";
                log.info("发送的地址为:{}" + url);
                respResult = HttpPostUtil.httpPost(url, jsonParam);
                log.info(url + ":交互时间为:{}" + (System.currentTimeMillis() - startTime));
                requestVideoSysEntity.setJSONObject(respResult);
                data.setObj(requestVideoSysEntity);
                break;
            case (Constants.CASEEND):
                //发送结案信息
                jsonParam.put("caseNo", requestVideoSysEntity.getCaseNo());
                jsonParam.put("surveyorNo", requestVideoSysEntity.getSurveyorNo());
                jsonParam.put("videoId", requestVideoSysEntity.getVideoId());
                jsonParam.put("videoTime", requestVideoSysEntity.getVideTime());
                jsonParam.put("link", requestVideoSysEntity.getLink());
                startTime = System.currentTimeMillis();
                url = "http://" + requestVideoSysEntity.getSnedUrl() + Constants.CASEEND + ".do";
                respResult = HttpPostUtil.httpPost(url, jsonParam);
                log.info(url + ":交互时间为: {}" + (System.currentTimeMillis() - startTime));
                requestVideoSysEntity.setJSONObject(respResult);
                data.setObj(requestVideoSysEntity);
                break;
            default:
                break;
        }

    }
}
