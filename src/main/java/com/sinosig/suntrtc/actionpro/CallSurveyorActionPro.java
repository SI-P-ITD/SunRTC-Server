package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.DateUtil;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.suntrtc.dao.VideoTimeMapper;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoTimeInfo;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 呼叫查勘员接口
 * @author: lukas
 */
@Service("callSurveyorPro")
@Slf4j
public class CallSurveyorActionPro extends BaseAction {

    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;

    @Autowired
    private VideoTimeMapper videoTimeMapper;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("CallSurveyorActionPro接口入参: {}", in);
        JSONObject paramJson = JSON.parseObject(in);
        JSONObject messageObj = new JSONObject();
        ResultBean resultBean = new ResultBean();
        // 请求者
        String orderId = paramJson.getString("orderId");
        String reportName = paramJson.getString("reportName");
        String roomNo = paramJson.getString("roomNo");
        String reqUserName = paramJson.getString("reqUserName");
        String flowNo = paramJson.getString("flowNo");
        JSONArray userGroup = paramJson.getJSONArray("userGroup");

        if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(reportName) || StringUtils.isEmpty(roomNo) || StringUtils.isEmpty(reqUserName) || null == userGroup) {
            return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
        }

        try {
            //视频通话累计时间判断
            VideoTimeInfo videoTimeInfo = videoTimeMapper.selectVideoTime(DateUtil.getNowDate(""));
            if (null != videoTimeInfo && videoTimeInfo.getCumulativeDuration() > (20000 * 60)) {
                resultBean.setResCode(Constants.VIDEO_TIME_CODE);
                resultBean.setResMsg(Constants.VIDEO_TIME_MSG);
                return resultBean;
            }

            RedisUtil.del(RedisUtil.getJedis(), "Im_userCallStatus:" + reqUserName);
            RedisUtil.sadd("Im_userCallStatus:" + reqUserName, "calling");
            JSONArray offlineArr = new JSONArray();
            for (Object value : userGroup) {
                JSONObject userObj = (JSONObject) value;
                String respUserName = userObj.getString("respUserName");
                Set<String> onlineApp = RedisUtil.smembers("Im_online:Eclaim:" + "APP");
                Set<String> onlinePc = RedisUtil.smembers("Im_online:Eclaim:" + "PC");
                Set<String> onlineMiniProgram = RedisUtil.smembers("Im_online:Eclaim:" + "MINIPROGRAM");
                if (!onlineApp.contains(respUserName.substring(0, respUserName.lastIndexOf("-")))
                        && !onlinePc.contains(respUserName.substring(0, respUserName.lastIndexOf("-")))
                        && !onlineMiniProgram.contains(respUserName.substring(0, respUserName.lastIndexOf("-")))) {
                    offlineArr.add(userObj);
                }
            }
            JSONObject result = null;
            // 去除不在线人员
            JSONArray jsonArray = userGroup.fluentRemoveAll(offlineArr);
            if (!jsonArray.isEmpty()) {
                JSONArray resultArr = new JSONArray();
                for (Object o : jsonArray) {
                    JSONObject jsonObject = (JSONObject) o;
                    messageObj.put("reqUserName", reqUserName);
                    messageObj.put("respUserName", jsonObject.get("respUserName"));
                    messageObj.put("callStatus", Constants.VEDIO_NOTICE_CODE);
                    messageObj.put("callStatusMsg", reportName + "（" + reqUserName.split("-")[0] + "）" + Constants.VEDIO_NOTICE_MSG + ",案件号：" + orderId + "，是否接受？");
                    messageObj.put("orderId", orderId);
                    messageObj.put("roomNo", roomNo);
                    messageObj.put("deviceType", enginBean.getDeviceType());
                    messageObj.put("reportName", reportName);
                    messageObj.put("flowNo", flowNo);
                    resultArr.add(jsonObject.get("respUserName"));
                }
                result = imSendmsgServiceImpl.imSendmsg(messageObj.toJSONString(), resultArr);
            }
            // 存在不在线人员，通知呼叫人
            if (!offlineArr.isEmpty()) {
                for (Object o : offlineArr) {
                    JSONObject jsonObject = (JSONObject) o;
                    String respUserName = jsonObject.getString("respUserName");
                    String receiverName = jsonObject.getString("receiverName");
                    JSONObject offlineObj = new JSONObject();
                    offlineObj.put("callStatus", Constants.VEDIO_NORESP_ONETIPS_CODE);
                    offlineObj.put("callStatusMsg", receiverName + "（" + respUserName.split("-")[0] + "）" + Constants.VEDIO_NORESP_ONETIPS_MSG);
                    offlineObj.put("reqUserName", reqUserName);
                    offlineObj.put("respUserName", respUserName);
                    JSONArray reqArr = new JSONArray();
                    reqArr.add(reqUserName);
                    imSendmsgServiceImpl.imSendmsg(offlineObj.toJSONString(), reqArr);
                }
            }

            if (null != result && Constants.STATUS_OK.equals(result.getString("ActionStatus"))) {
                resultBean.setResCode(Constants.RESCODE_SUCCESS);
                resultBean.setResMsg(Constants.RESMSG_SUCCESS);
            } else {
                resultBean.setResCode(Constants.RESCODE_VIDEOKNOWS_PROCESS);
                resultBean.setResMsg(Constants.RESMSG_VIDEOKNOWS_PROCESS);
                resultBean.setResultContent(result);
            }
        } catch (Exception e) {
            log.error("CallSurveyorActionPro->error：" + e);
            resultBean.setResCode(Constants.RESCODE_FAIL);
            resultBean.setResMsg(Constants.RESMSG_FAIL);
            return resultBean;
        }
        return resultBean;
    }


}
