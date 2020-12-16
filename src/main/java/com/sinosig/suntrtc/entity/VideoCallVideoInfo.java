package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.util.Date;

/**
 * 视频信息表
 */
@Data
public class VideoCallVideoInfo {
    private String id;

    private String businessNo;

    private String userName;

    private String surName;

    private String systemVersion;

    private String userSource;

    private String streamId;

    private String fileId;

    private String videoPath;

    private Date createTime;

    private Date updateTime;

    private String onlineStatus;
}