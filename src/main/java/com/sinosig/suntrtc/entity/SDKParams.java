package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SDKParams  implements Serializable {
    private String platType;
    private String sdkType;
    private String sceneType;
    private String ak;
    private String sk;
    private String appId;
    private String secretKey;
    private String region;
    private String aliyunRegion;
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    private String aliyunBucket;

}
