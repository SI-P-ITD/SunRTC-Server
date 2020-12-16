package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessInfo implements Serializable {
    private String businessNo;
    private String roomNo;
    private String startUser;
    private String startSource;
    private String startTime;
    private String endTime;
    private String endType;
    private String dismissSource;
    private String dismissOperator;
    private String timeSpan;
    private String createTime;
    private String updateTime;


}
