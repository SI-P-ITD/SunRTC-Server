package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.CommonParameterMapper;
import com.sinosig.suntrtc.entity.CommonParameter;
import com.sinosig.suntrtc.service.CommonParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommonParameterServiceImpl implements CommonParameterService {
    private Logger log = LoggerFactory.getLogger(CommonParameterServiceImpl.class);

    @Autowired
    private CommonParameterMapper commonParameterDao;


    @Override
    public List<CommonParameter> selectCommonParameter(CommonParameter commonParameter) {
        return commonParameterDao.selectCommonParameter(commonParameter);
    }
}


