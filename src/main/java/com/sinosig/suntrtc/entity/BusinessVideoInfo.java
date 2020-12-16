package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessVideoInfo  implements Serializable {
    private String businessNo;
    private String username;
    private String surName;
    private String userSource;
    private String streamId;
    private String videoPath;
    private String createTime;
    private String updateTime;
    private String onlineStatus;
    private String systemVersion;
}
