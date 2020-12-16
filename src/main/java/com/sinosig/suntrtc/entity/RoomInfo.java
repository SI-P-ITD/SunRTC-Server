package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomInfo  implements Serializable {
    private String roomNo;
    private String currentBusinessNo;
    private String needPassword;
    private String password;
    private String status;
    private String createUser;
    private String createSource;
    private String createTime;
    private String updateTime;
}
