package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import com.sinosig.suntrtc.service.VideoCallVideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 查询房间中在线人数接口
 * @author: lukas
 */
@Service("roomUserNumPro")
@Slf4j
public class RoomUserNumPro extends BaseAction {

    @Autowired
    private VideoCallVideoInfoService videoCallVideoInfoService;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        JSONObject result = new JSONObject();
        try {
            log.info("roomUserNumPro接口入参: {}", in);
            JSONObject paramJson = JSON.parseObject(in);
            String businessNo = paramJson.getString("businessNo");
            if (StringUtils.isBlank(businessNo)) {
                return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }

            String roomNum = "";
            List<String> roomUserList = new ArrayList<>();
            List<VideoCallVideoInfo> videoCallVideoInfos = videoCallVideoInfoService.selectByBusinessNo(businessNo);
            if (null != videoCallVideoInfos) {
                roomNum = videoCallVideoInfos.size() + "";
            }
            assert videoCallVideoInfos != null;
            for (VideoCallVideoInfo info : videoCallVideoInfos) {
                String userName = info.getUserName();
                roomUserList.add(userName.split("-")[1] + "-" + userName.split("-")[2]);
            }

            result.put("roomUserNum", roomNum);
            result.put("roomUserList", roomUserList);
        } catch (Exception e) {
            log.error("roomUserNumPro->error：", e);
        }

        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, result);
    }

}
