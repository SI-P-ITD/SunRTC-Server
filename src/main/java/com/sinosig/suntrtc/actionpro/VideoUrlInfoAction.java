package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.HttpPostUtil;
import com.sinosig.suntrtc.dao.VideoCallBusinessMapper;
import com.sinosig.suntrtc.dao.VideoCallOrderInfoMapper;
import com.sinosig.suntrtc.dao.VideoCallVideoInfoMapper;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoCallBusiness;
import com.sinosig.suntrtc.entity.VideoCallOrderInfo;
import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 前端视频回调接口
 * @author: lukas
 */
@Service("VideoUrlInfoAction")
public class VideoUrlInfoAction extends BaseAction {
    private static Logger log = LoggerFactory.getLogger(VideoUrlInfoAction.class);

    @Autowired
    VideoCallVideoInfoMapper videoCallVideoInfoMapper;

    @Autowired
    private VideoCallBusinessMapper businessMapper;

    @Autowired
    private VideoCallOrderInfoMapper orderInfoMapper;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {

        log.info("VideoUrlInfoAction->data{}", in);
        ResultBean resultBean = new ResultBean();
        try {
            JSONObject jsonObject = JSON.parseObject(in);
            if (jsonObject != null) {
                String stream_id = jsonObject.getString("stream_id");
                String video_url = jsonObject.getString("video_url");
                String end_time = jsonObject.getString("end_time");
                String start_time = jsonObject.getString("start_time");
                //根据视频流查出整个单方视频的整个信息
                List<VideoCallVideoInfo> videoInfos = videoCallVideoInfoMapper.selectByStreamId(stream_id);
                if (null == videoInfos || videoInfos.size() < 1) {
                    log.info("当前视频流信息没有数据videoInfos:stream_id={}", stream_id);
                    resultBean.setResCode(Constants.RESCODE_ANALYZE_PARM_FAILED);
                    resultBean.setResMsg(Constants.RESMSG_ANALYZE_PARM_FAILED);
                }
                //更新视频信息
                assert videoInfos != null;
                VideoCallVideoInfo videoInfo = videoInfos.get(0);
                videoInfo.setVideoPath(video_url);
                videoInfo.setUpdateTime(new Date());
                videoCallVideoInfoMapper.updateByPrimaryKeySelective(videoInfo);
                String videoInfoName = videoInfo.getUserName();
                if (videoInfoName.contains("MCUMix")) {
                    //获取事故号
                    JSONObject orderInfoJson = getOrderInfo(videoInfo.getBusinessNo(), resultBean);
                    log.info("VideoUrlInfoAction->VideoCallOrderInfo:房间事故号绑定信息为{}", orderInfoJson);
                    //给videosys发送视频地址用于音视频分离
                    JSONObject videoJson = new JSONObject();
                    videoJson.put("streamId", stream_id);
                    videoJson.put("videoUrl", video_url);
                    videoJson.put("link", orderInfoJson.getString("link"));
                    videoJson.put("estimateNo", orderInfoJson.getString("estimateNo"));

                    videoJson.put("caseNo", orderInfoJson.getString("caseNo"));
                    String usecode = orderInfoJson.getString("userCode");
                    if (usecode.contains("-")) {
                        usecode = orderInfoJson.getString("userCode").substring(0, usecode.indexOf("-"));
                    }
                    videoJson.put("userCode", usecode);
                    videoJson.put("videoTime", String.valueOf(Long.parseLong(end_time) - Long.parseLong(start_time)));
                    String url = "http://" + "第三方地址" + "sendVideoUrlInfo.do";
                    JSONObject respResult = HttpPostUtil.httpPost(url, videoJson);
                    log.info("视频地址发送接口返回结果sendVideoUrlInfo：结果:{}", respResult == null ? null : respResult.toString());
                    resultBean.setResCode(Constants.RESCODE_SUCCESS);
                    resultBean.setResMsg(Constants.RESMSG_SUCCESS);
                } else {
                    log.info("不是混流不推送->" + videoInfo.getStreamId());
                }
            } else {
                resultBean.setResCode(Constants.RESCODE_REQUEST_PARAM_IS_NULL);
                resultBean.setResMsg(Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }
        } catch (Exception e) {
            resultBean.setResCode(Constants.RESCODE_ANALYZE_PARM_FAILED);
            resultBean.setResMsg(Constants.RESMSG_ANALYZE_PARM_FAILED);
            log.error("VideoUrlInfoAction->error", e);
        }
        return resultBean;
    }

    /**
     * 根据businessNo获取orderId
     */
    private JSONObject getOrderInfo(String businessNo, ResultBean resultBean) {
        JSONObject jsonObject = new JSONObject();
        VideoCallBusiness videoCallBusiness = getRoomNo(businessNo, resultBean);
        List<VideoCallOrderInfo> infoList = orderInfoMapper.selectByRoomNo(videoCallBusiness.getRoomNo());
        if (null == infoList || infoList.size() == 0) {
            resultBean.setResCode(Constants.RESCODE_ANALYZE_PARM_FAILED);
            resultBean.setResMsg(Constants.RESMSG_ANALYZE_PARM_FAILED);
        }
        jsonObject.put("caseNo", infoList.get(0).getOrderId());
        jsonObject.put("userCode", infoList.get(0).getSurveyorName());
        jsonObject.put("link", infoList.get(0).getLink());
        jsonObject.put("estimateNo", infoList.get(0).getEstimateNo());
        return jsonObject;
    }

    /**
     * 根据businessNo获取orderId
     */
    private VideoCallBusiness getRoomNo(String businessNo, ResultBean resultBean) {
        VideoCallBusiness business = businessMapper.selectByPrimaryKey(businessNo);
        if (null == business) {
            resultBean.setResCode(Constants.RESCODE_ANALYZE_PARM_FAILED);
            resultBean.setResMsg(Constants.RESMSG_ANALYZE_PARM_FAILED);
        }
        return business;
    }

}
