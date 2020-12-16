package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.SDKParams;

public interface ConfigService {
    SDKParams getSDKParams(String sceneType, String sdkType);

}
