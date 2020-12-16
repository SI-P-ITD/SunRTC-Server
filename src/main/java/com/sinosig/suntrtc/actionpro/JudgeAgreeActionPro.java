package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.global.utils.UUIDUtil;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoInvitationRecord;
import com.sinosig.suntrtc.service.CommonParameterService;
import com.sinosig.suntrtc.service.InvitationRecordService;
import com.sinosig.suntrtc.service.serviceImpl.ImSendmsgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import static com.sinosig.suntrtc.enums.ConnectionTerminalEnums.allTerminalList;
import static com.sinosig.suntrtc.enums.ConnectionTerminalEnums.remainsTerminalList;
import static com.sinosig.suntrtc.enums.ProjectIdentificationEnum.R;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 判断是否点击同意接口
 * @detail: 如果点击同意则根据案件号查询房间或创建房间并关联案件号接口；如果点击关闭则停止呼叫。
 * @author: lukas
 */
@Service("judgeAgreePro")
@Slf4j
public class JudgeAgreeActionPro extends BaseAction {

    @Autowired
    private InvitationRecordService invitationRecordService;
    @Autowired
    private ImSendmsgServiceImpl imSendmsgServiceImpl;
    @Autowired
    CommonParameterService commonParameterService;


    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("judgeAgreePro接口入参: {}", in);
        JSONObject paramJson = JSON.parseObject(in);
        JSONObject resultContent = new JSONObject();
        JSONObject messageObj = new JSONObject();
        ResultBean resultBean;
        JSONArray userGroup = null;
        try {
            String orderId = paramJson.getString("orderId");
            String clickAgree = paramJson.getString("clickAgree");
            String roomNo = paramJson.getString("roomNo");
            String reqUserName = paramJson.getString("reqUserName");
            String respUserName = paramJson.getString("respUserName");
            String receiverName = paramJson.getString("receiverName");

            if (StringUtils.isBlank(orderId) || StringUtils.isBlank(clickAgree) || StringUtils.isBlank(reqUserName) || StringUtils.isBlank(respUserName)) {
                return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }

            VideoInvitationRecord invitationRecord = new VideoInvitationRecord();
            String callee = "";
            String respPlatform = "";
            // 被呼叫人
            if (StringUtils.isNotBlank(respUserName)) {
                callee = respUserName.split("-")[0];
                respPlatform = respUserName.split("-")[1];
            }
            Boolean manyExis = false;
            //通过事故号首字母控制是否支持多人视频
            String type = orderId.substring(0, 1);
            if (R.getCode().equals(type)) {
                manyExis = isManyTerminalConnection(callee);
            }

            if ("N".equals(clickAgree)) {
                log.info("进入拒绝");
                invitationRecord.setId(UUIDUtil.getUUID());
                invitationRecord.setOrderId(orderId);
                invitationRecord.setState("不同意");
                //是表示失效，否表示未失效
                invitationRecord.setInvalid("否");
                invitationRecord.setCallee(respUserName);
                //保存邀请记录
                invitationRecordService.saveInvitationRecord(invitationRecord);

                // 多端登录，其中一端拒绝
                if (manyExis) {
                    // 向被呼叫方推送 取消弹窗消息
                    messageObj.put("callStatus", Constants.VEDIO_ANSWER_POPUP_CODE);
                    messageObj.put("callStatusMsg", Constants.VEDIO_ANSWER_POPUP_MSG);
                    userGroup = new JSONArray();
                    //对其他被呼叫方推送消息
                    List<String> list = remainsTerminalList(respPlatform);
                    for (String terminal : list) {
                        userGroup.add(callee + "-" + terminal);
                    }
                    log.info("进行拒绝IM消息发送：消息：[{}],userGroup:[{}],sdkType:[{}]", messageObj.toJSONString(), userGroup.toString());
                    resultBean = sendImMsg(messageObj.toJSONString(), userGroup);
                    log.info("拒绝返回结果:" + resultBean.toString());
                }

                // 被呼叫人拒绝，向 呼叫人 推送 IM 信息
                messageObj.put("reqUserName", reqUserName);
                messageObj.put("respUsername", respUserName);
                messageObj.put("callStatus", Constants.VEDIO_USERBUSY_CODE);
                messageObj.put("callStatusMsg", receiverName + "（" + callee + "）" + Constants.VEDIO_USERBUSY_MSG);

                userGroup = new JSONArray();
                userGroup.add(reqUserName);

                resultBean = sendImMsg(messageObj.toJSONString(), userGroup);
                if (resultBean.getResultContent() != null) {
                    return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL, resultBean);
                } else {
                    return new ResultBean(Constants.RESCODE_VIDEO_REFUSE, Constants.RESMSG_VIDEO_REFUSE);
                }

            } else if ("T".equals(clickAgree)) {
                invitationRecord.setId(UUIDUtil.getUUID());
                invitationRecord.setOrderId(orderId);
                invitationRecord.setState("无操作");
                //是表示失效，否表示未失效
                invitationRecord.setInvalid("否");
                invitationRecord.setCallee(respUserName);
                //保存邀请记录
                invitationRecordService.saveInvitationRecord(invitationRecord);

                // 多端登录，其中一端无应答
                if (manyExis) {
                    // 向被呼叫方推送 取消弹窗消息
                    messageObj.put("callStatus", Constants.VEDIO_NORESP_TWOTIPS_CODE);
                    messageObj.put("callStatusMsg", Constants.VEDIO_NORESP_TWOTIPS_MSG);
                    //对其他被呼叫方推送消息
                    List<String> list = remainsTerminalList(respPlatform);
                    for (String terminal : list) {
                        userGroup.add(callee + "-" + terminal);
                    }
                    sendImMsg(messageObj.toJSONString(), userGroup);
                }


                userGroup = new JSONArray();
                userGroup.add(reqUserName);

                messageObj.put("callStatus", Constants.VEDIO_NORESP_ONETIPS_CODE);
                messageObj.put("callStatusMsg", receiverName + "（" + callee + "）" + Constants.VEDIO_NORESP_ONETIPS_MSG);

                ResultBean resultBean1 = sendImMsg(messageObj.toJSONString(), userGroup);
                if (resultBean1.getResultContent() != null) {
                    return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL);
                } else {
                    return new ResultBean(Constants.RESCODE_VIDEO_REFUSE, Constants.RESMSG_VIDEO_REFUSE);
                }
            }

            // 如果呼叫方取消，但被叫方点击了同意，向被呼叫方推送 视频会议已结束 提示信息
            @NotNull Set<String> reqUserNameStatus = RedisUtil.smembers("Im_userCallStatus:" + reqUserName);
            if (!reqUserNameStatus.isEmpty()) {
                for (String str : reqUserNameStatus) {
                    if ("cancle".equals(str)) {
                        return new ResultBean(Constants.RESCODE_VIDEO_END, Constants.RESMSG_VIDEO_END);
                    }
                }
            }


            // 邀请结束状态更新
            invitationRecord.setId(UUIDUtil.getUUID());
            invitationRecord.setOrderId(orderId);
            invitationRecord.setRoomNo(roomNo);
            invitationRecord.setState("同意");
            //是表示失效，否表示未失效
            invitationRecord.setInvalid("否");
            invitationRecord.setCallee(callee);
            //保存邀请记录 每邀约成功一个人插入一条数据，不包含房主
            invitationRecordService.saveInvitationRecord(invitationRecord);
            messageObj = new JSONObject();
            // 推送给呼叫人
            messageObj.put("reqUserName", reqUserName);
            messageObj.put("respUsername", respUserName);
            messageObj.put("callStatus", Constants.VEDIO_SUCCESS_CODE);
            messageObj.put("callStatusMsg", receiverName + "（工号 " + callee + "）" + Constants.VEDIO_SUCCESS_CODE);
            messageObj.put("roomNo", roomNo);
            messageObj.put("deviceType", enginBean.getDeviceType());
            messageObj.put("orderId", orderId);
            userGroup = new JSONArray();
            userGroup.add(reqUserName);

            resultBean = sendImMsg(messageObj.toJSONString(), userGroup);

            // 多端登录，其中一端接听
            if (manyExis) {
                // 向被呼叫方推送 取消弹窗消息
                JSONObject sendMsg = new JSONObject();
                sendMsg.put("callStatus", Constants.VEDIO_ANSWER_AGREEE_CODE);
                sendMsg.put("callStatusMsg", Constants.VEDIO_ANSWER_AGREEE_MSG);
                //对其他被呼叫方推送消息
                List<String> list = remainsTerminalList(respPlatform);
                for (String terminal : list) {
                    userGroup.add(callee + "-" + terminal);
                }
                resultBean = sendImMsg(sendMsg.toJSONString(), userGroup);
            }

            // 返回给给被呼叫人
            resultContent.put("reqUserName", reqUserName);
            resultContent.put("respUsername", respUserName);
            resultContent.put("deviceType", enginBean.getDeviceType());
            resultContent.put("roomNo", roomNo);
            resultContent.put("orderId", orderId);
            resultBean.setResultContent(resultContent);

            log.info("被呼叫人数据返回：{}", resultContent.toJSONString());
        } catch (Exception e) {
            log.error("judgeAgreePro->error：", e);
            return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, resultContent);
    }

    private ResultBean sendImMsg(String messageObj, JSONArray userGroup) {
        JSONObject msgResult;
        ResultBean resultBean = new ResultBean();
        msgResult = imSendmsgServiceImpl.imSendmsg(messageObj, userGroup);
        if (Constants.STATUS_OK.equals(msgResult.getString("ActionStatus"))) {
            resultBean.setResCode(Constants.RESCODE_SUCCESS);
            resultBean.setResMsg(Constants.RESMSG_SUCCESS);
        } else {
            resultBean.setResCode(Constants.RESCODE_SURVEYOR_PROCESS);
            resultBean.setResMsg(Constants.RESMSG_SURVEYOR_PROCESS);
            resultBean.setResultContent(msgResult);
        }

        return resultBean;
    }

    /**
     * @describe: 是否多端在线判断
     * @author: wangzi
     */
    private Boolean isManyTerminalConnection(String callee) {
        boolean result = false;
        Set<String> terminalset;
        List<String> list = allTerminalList();
        int count = 0;
        for (String terminal : list) {
            terminalset = RedisUtil.smembers("Im_online:Eclaim:" + terminal);
            if (!terminalset.isEmpty() && terminalset.contains(callee)) {
                log.info("人员:[{}]终端:[{}]处于在线状态", callee, terminal);
                count++;
            }
        }
        //至少有两端在线认为是多端在线
        if (count >= 2) {
            result = true;
        }
        return result;
    }
}
