package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.ConfigDao;
import com.sinosig.suntrtc.entity.SDKParams;
import com.sinosig.suntrtc.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {
    private Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private ConfigDao configDao;
    @Override
    public SDKParams getSDKParams(String sceneType, String sdkType) {
        return configDao.getSDKParams(sceneType, sdkType);
    }

}


