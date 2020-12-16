package com.sinosig.suntrtc.consts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 * @auther wangzi
 * @crate 2020-08-18 9:50
 * @desc  阿里云登录信息
 * @worn 配置文件关联的prefix不支持驼峰命名和蛇形命名（下划线）
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyunlogin")
public class AliyunLoginYml  {
    HashMap<String,String> data=new HashMap<>();
}