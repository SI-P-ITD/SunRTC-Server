package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.suntrtc.dao.VideoCallPictureMapper;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoCallPicture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 获取图片列表接口
 * @author: lg
 */
@Service("getPictureListPro")
@Slf4j
public class GetPictureListActionPro extends BaseAction {

    @Autowired
    private VideoCallPictureMapper videoCallPictureMapper;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        log.info("getPictureListPro接口入参: {}", in);
        List<VideoCallPicture> pictures;
        try {
            JSONObject paramJson = JSON.parseObject(in);
            String businessNo = paramJson.getString("businessNo");
            if (StringUtils.isBlank(businessNo)) {
                return new ResultBean(Constants.RESCODE_REQUEST_PARAM_IS_NULL, Constants.RESMSG_REQUEST_PARAM_IS_NULL);
            }
            pictures = videoCallPictureMapper.selectBybusinessNo(businessNo);
            if (pictures == null || pictures.isEmpty()) {
                return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS);
            }
        } catch (Exception e) {
            log.error("getPictureListPro->error：", e);
            return new ResultBean(Constants.RESCODE_FAIL, Constants.RESMSG_FAIL);
        }
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, pictures);
    }


}
