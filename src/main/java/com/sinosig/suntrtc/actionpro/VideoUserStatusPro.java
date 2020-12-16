package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.DateUtil;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.suntrtc.dao.VideoTimeMapper;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoTimeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.sinosig.suntrtc.enums.ConnectionTerminalEnums.remainsTerminalList;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 用户状态回调接口（用于登录和退出登录）
 * @author: lukas
 */
@Service("userStatusPro")
@Slf4j
public class VideoUserStatusPro extends BaseAction {

    @Autowired
    private VideoTimeMapper videoTimeMapper;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {

        //视频通话累计时间判断,当月通话时间不能大于2万分钟
        VideoTimeInfo videoTimeInfo = videoTimeMapper.selectVideoTime(DateUtil.getNowDate(""));
        if (videoTimeInfo != null && videoTimeInfo.getCumulativeDuration() > (20000 * 60)) {
            return new ResultBean(Constants.VIDEO_TIME_CODE, Constants.VIDEO_TIME_MSG);
        }

        log.info("userStatusPro接口入参: {}", in);
        JSONObject jsonObject = JSON.parseObject(in);
        JSONObject info = jsonObject.getJSONObject("Info");
        String action = info.getString("Action");
        String toAccount = info.getString("To_Account");
        try {
            if (toAccount.contains("-")) {
                String platform = toAccount.substring(toAccount.lastIndexOf("-") + 1);
                String userCode = toAccount.substring(0, toAccount.lastIndexOf("-"));
                if ("Login".equals(action)) {
                    RedisUtil.sadd(Constants.IM_ONLINE_ECLAIM + platform, userCode);
                    RedisUtil.sadd(Constants.IM_TOTAL_LIST, userCode);
                } else {
                    RedisUtil.srem(Constants.IM_ONLINE_ECLAIM + platform, userCode);
                    //20200827 wangzi 如果没有其他端登录就退出列表
                    if (!this.isAnotherLogin(platform, userCode)) {
                        RedisUtil.srem(Constants.IM_TOTAL_LIST, userCode);
                    }
                }
            }
        } catch (Exception e) {
            log.error("userStatusPro->error：" + e);
            return new ResultBean(Constants.RESCODE_ANALYZE_PARM_FAILED, Constants.RESMSG_ANALYZE_PARM_FAILED);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);

    }

    /**
     * @describe: 是否其他端登录
     * @author: wangzi
     */
    private boolean isAnotherLogin(String platform, String userCode) {
        List<String> list = remainsTerminalList(platform);
        Set<String> terminalset;
        int count = 0;
        boolean result = false;
        for (String terminal : list) {
            terminalset = RedisUtil.smembers(Constants.IM_ONLINE_ECLAIM + terminal);
            if (!terminalset.isEmpty() && terminalset.contains(userCode)) {
                log.info("人员:[{}]终端:[{}]仍然处于在线状态，不可退出", userCode, terminal);
                count++;
            }
        }
        if (count > 0) {
            result = true;
        }
        return result;
    }
}
