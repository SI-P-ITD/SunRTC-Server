package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.CommonParameter;
import java.util.List;

public interface CommonParameterService {
    /**
     * @describe: 公共参数表查询
     * @author: wangzi
     * @date: 2020/8/12 16:58
     */
    List<CommonParameter> selectCommonParameter(CommonParameter commonParameter);

}
