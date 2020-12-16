package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.SDKParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigDao {

    SDKParams getSDKParams(@Param("sceneType") String sceneType, @Param("sdkType")String sdkType);

}
