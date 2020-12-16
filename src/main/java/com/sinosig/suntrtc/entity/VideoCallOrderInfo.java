package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VideoCallOrderInfo {
    /** 需要修改为varchar类型*/
    private String id;

    private String orderId;
    /** 查勘员工号*/
    private String surveyorName;

    private String roomNo;

    private String createUser;

    private Date createTime;

    private Date updateTime;

    private String link;
    private String estimateNo;
}