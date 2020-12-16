package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnginBean  implements Serializable {
    private String requestSource;
    private String deviceType;
    private String deviceModel;
    private String systemVersion;
    private String serviceName;
    private Object servicePara;
}
