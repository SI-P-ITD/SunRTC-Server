package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 呼叫查勘员取消接口
 * @author: lukas
 */
@Service("callSurveyorCancleActionPro")
@Slf4j
public class CallSurveyorCancleActionPro extends BaseAction {

    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("CallSurveyorCancleActionPro接口入参: {}", in);
        JSONObject paramJson = JSON.parseObject(in);
        JSONObject messageObj = new JSONObject();
        ResultBean resultBean = new ResultBean();
        String reqUserName = paramJson.getString("reqUserName");
        String reportName = paramJson.getString("reportName");
        JSONArray userGroup = paramJson.getJSONArray("respUserGroup");
        if (null == userGroup || StringUtils.isEmpty(reqUserName) || StringUtils.isEmpty(reportName)) {
            return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
        }

        try {
            RedisUtil.srem("userCallStatus:" + reqUserName, "calling");
            RedisUtil.sadd("userCallStatus:" + reqUserName, "cancle");
            messageObj.put("reqUserName", reqUserName);
            messageObj.put("callStatus", Constants.VEDIO_CANCEL_POPUP_CODE);
            messageObj.put("callStatusMsg", reportName + "（" + reqUserName.split("-")[0] + "）" + Constants.VEDIO_CANCEL_POPUP_MSG);
            JSONObject result = imSendmsgServiceImpl.imSendmsg(messageObj.toJSONString(), userGroup);
            if ("OK".equals(result.getString("ActionStatus"))) {
                resultBean.setResCode(Constants.RESCODE_SUCCESS);
                resultBean.setResMsg(Constants.RESMSG_SUCCESS);
            } else {
                resultBean.setResCode(Constants.RESCODE_SURVEYOR_PROCESS);
                resultBean.setResMsg(Constants.RESMSG_SURVEYOR_PROCESS);
                resultBean.setResultContent(result);
            }
        } catch (Exception e) {
            log.error("callSurveyorCancleActionPro->error：" + e);
            return new ResultBean(Constants.RESCODE_SURVEYOR_PROCESS, Constants.RESMSG_SURVEYOR_PROCESS);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
    }

}
