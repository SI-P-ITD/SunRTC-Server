package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.CommonParameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonParameterMapper {
    /**
     * @describe: 公共参数表查询
     * @author: wangzi
     * @date: 2020/8/12 16:58
     */
    List<CommonParameter>  selectCommonParameter(CommonParameter commonParameter);
}