package com.sinosig.suntrtc.entity;

import lombok.Data;
/**
 * @describe: 公共参数表
 * @author: wangzi
 * @date: 2020/8/12 16:45
 */
@Data
public class CommonParameter {
    private String paramCode;
    private String paramValue;
    private String paramName;
    private String paramChineseName;
    private String paramTypeCode;
    private String paramTypeName;
    private String remarks;
}