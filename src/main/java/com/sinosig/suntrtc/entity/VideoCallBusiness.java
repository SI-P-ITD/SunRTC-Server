package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VideoCallBusiness {
    private String businessNo;

    private String roomNo;

    private String startSource;

    private String startUser;

    private Date startTime;

    private Date endTime;

    private String endType;

    private String dismissSource;

    private String dismissOperator;

    private String timeSpan;

    private Date createTime;

    private Date updateTime;
}