package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 用户列表接口
 * @author: lukas
 */
@Service("userStatusListPro")
@Slf4j
public class VideoUserListActionPro extends BaseAction {

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("userStatusListPro接口入参: {}", in);
        List<Map<String, Object>> listOnline = new ArrayList<>();
        try {
            Map<String, Object> map = null;
            Set<String> onlineAPP = RedisUtil.smembers("Im_online:Eclaim:" + "APP");
            Set<String> onlinePC = RedisUtil.smembers("Im_online:Eclaim:" + "PC");
            Set<String> onlineMiniProgram = RedisUtil.smembers("Im_online:Eclaim:" + "MINIPROGRAM");
            Set<String> Im_totalList = RedisUtil.smembers("Im_totalList");
            for (String userCode : Im_totalList) {
                if ((onlineAPP.contains(userCode)) && onlinePC.contains(userCode) && onlineMiniProgram.contains(userCode)) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "PC + 小程序 + APP");
                    listOnline.add(map);
                } else if ((onlineAPP.contains(userCode)) && onlinePC.contains(userCode)) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "PC + APP");
                    listOnline.add(map);
                } else if ((onlineAPP.contains(userCode)) && onlineMiniProgram.contains(userCode)) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "APP + 小程序");
                    listOnline.add(map);
                } else if (onlinePC.contains(userCode) && onlineMiniProgram.contains(userCode)) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "PC + 小程序");
                    listOnline.add(map);
                } else if (onlinePC.contains(userCode)) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "PC");
                    listOnline.add(map);
                } else if ((onlineAPP.contains(userCode))) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "APP");
                    listOnline.add(map);
                } else if (onlineMiniProgram.contains(userCode)) {
                    map = new HashMap<>();
                    map.put("userCode", userCode);
                    map.put("userStatus", "0");
                    map.put("platform", "小程序");
                    listOnline.add(map);
                }
            }
            log.info("--------- userStatusListPro在线人数列表为： --------- {}  ", listOnline == null ? 0 : JSON.toJSONString(listOnline));
        } catch (Exception e) {
            log.error("VideoUserListActionPro->error：" + e);
            return new ResultBean(Constants.RESCODE_ANALYZE_PARM_FAILED, Constants.RESMSG_ANALYZE_PARM_FAILED);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, listOnline);

    }
}
