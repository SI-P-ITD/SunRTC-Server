package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.util.Date;

/** 视频邀请记录表*/
@Data
public class VideoInvitationRecord {
    /** ID*/
    private String id;
    /** 案件号*/
    private String orderId;
    /** 状态（初始化、同意、不同意、失效）*/
    private String state;
    /** 是否失效（是或否）*/
    private String invalid;
    /**
     * 被呼叫方
     */
    private String callee;
    /** 房间号*/
    private String roomNo;

    private Date createTime;

    private Date updateTime;
}