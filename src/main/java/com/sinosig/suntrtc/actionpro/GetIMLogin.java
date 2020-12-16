package com.sinosig.suntrtc.actionpro;

import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.entity.Constants;
import com.sinosig.global.utils.DES3Util;
import com.sinosig.suntrtc.consts.SdkType;
import com.sinosig.suntrtc.engin.BaseAction;
import com.sinosig.suntrtc.engin.ResultBean;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.SDKParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: IM和阿里云登录信息回传接口
 * @author: wangzi
 */
@Service("getIMLogin")
@Slf4j
public class GetIMLogin extends BaseAction {
    @Autowired
    SdkType sdkType;

    @Override
    public Object executeAction(String in, String uuid, EnginBean enginBean) {
        JSONObject resultContent = new JSONObject();
        SDKParams sdkParams = sdkType;
        //IM参数返回
        assert sdkParams != null;
        resultContent.put("imappid", DES3Util.encryptDES(sdkParams.getAppId()));
        resultContent.put("imkey", DES3Util.encryptDES(sdkParams.getSecretKey()));
        log.info("IM参数key:[imappid],原值:[{}],加密值:[{}]", sdkParams.getAppId(), DES3Util.encryptDES(sdkParams.getAppId()));
        log.info("IM参数key:[imkey],原值:[{}],加密值:[{}]", sdkParams.getSecretKey(), DES3Util.encryptDES(sdkParams.getSecretKey()));
        return new ResultBean(Constants.RESCODE_SUCCESS, Constants.RESMSG_SUCCESS, resultContent);
    }
}
