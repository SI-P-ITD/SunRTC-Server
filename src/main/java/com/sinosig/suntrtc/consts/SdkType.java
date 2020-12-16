package com.sinosig.suntrtc.consts;

import com.sinosig.suntrtc.entity.SDKParams;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @auther lukas
 * @crate 2020-06-28 9:50
 */
@Configuration
@PropertySource(value="classpath:application.yml")
@ConfigurationProperties("sdk-type")
@Data
public class SdkType extends SDKParams {
    private String platType;
    private String sdkType;
    private String sceneType;
    private String ak;
    private String sk;
    private String appId;
    private String secretKey;
}
