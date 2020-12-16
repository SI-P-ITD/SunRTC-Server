package com.sinosig;

import com.sinosig.global.utils.IpUtils;
import com.sinosig.global.utils.RedisUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: suntrtc启动类
 * @author: wangzi
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.sinosig.suntrtc.dao")
@EnableTransactionManagement
@EnableWebSocket
@EnableSwagger2
public class SunRtcInterfaceApplication {

    /**
     * @describe: 清除用户缓存中的状态
     */
    static {
        RedisUtil.del(RedisUtil.getJedis(), "busy");
        RedisUtil.del(RedisUtil.getJedis(), "online");
        RedisUtil.del(RedisUtil.getJedis(), "leave");
        RedisUtil.del(RedisUtil.getJedis(), "sunRtcUserList");
        cleanLocalRedisData();
    }

    /**
     * @describe: springboot 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(SunRtcInterfaceApplication.class, args);
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}");
    }

    /**
     * @describe: 跨域请求拦截器
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        // 允许Get的请求方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * @describe: 清除用户缓存中的状态方法
     */
    private static void cleanLocalRedisData() {
        String ipAddress = IpUtils.getLocalAddress();
        Set<String> iplrange0 = RedisUtil.smembers(ipAddress + ":onlinePro:" + "APP");
        Set<String> iplrange1 = RedisUtil.smembers(ipAddress + ":onlinePro:" + "PC");
        Set<String> iplrange2 = RedisUtil.smembers(ipAddress + ":onlinePro:" + "MINIPROGRAM");
        Set<String> iplrange3 = RedisUtil.smembers(ipAddress + ":busyPro:" + "APP");
        Set<String> iplrange4 = RedisUtil.smembers(ipAddress + ":busyPro:" + "PC");
        Set<String> iplrange5 = RedisUtil.smembers(ipAddress + ":busyPro:" + "MINIPROGRAM");

        if (null != iplrange0 && iplrange0.size() > 0) {
            for (String str : iplrange0) {
                RedisUtil.srem("onlinePro:" + "APP", str);
                RedisUtil.srem("busyPro:" + "APP", str);
                RedisUtil.srem("totalUserList", str);
                RedisUtil.del(RedisUtil.getJedis(), "userOnlineList:" + str);
            }
        }
        if (null != iplrange1 && iplrange0.size() > 0) {
            for (String str : iplrange1) {
                RedisUtil.srem("onlinePro:" + "PC", str);
                RedisUtil.srem("busyPro:" + "PC", str);
                RedisUtil.srem("totalUserList", str);
                RedisUtil.del(RedisUtil.getJedis(), "userOnlineList:" + str);
            }
        }
        if (null != iplrange2 && iplrange0.size() > 0) {
            for (String str : iplrange2) {
                RedisUtil.srem("onlinePro:" + "MINIPROGRAM", str);
                RedisUtil.srem("busyPro:" + "MINIPROGRAM", str);
                RedisUtil.srem("totalUserList", str);
                RedisUtil.del(RedisUtil.getJedis(), "userOnlineList:" + str);
            }
        }
        if (null != iplrange3 && iplrange0.size() > 0) {
            for (String str : iplrange3) {
                RedisUtil.srem("onlinePro:" + "APP", str);
                RedisUtil.srem("busyPro:" + "APP", str);
                RedisUtil.srem("totalUserList", str);
                RedisUtil.del(RedisUtil.getJedis(), "userOnlineList:" + str);

            }
        }
        if (null != iplrange4 && iplrange0.size() > 0) {
            for (String str : iplrange4) {
                RedisUtil.srem("onlinePro:" + "PC", str);
                RedisUtil.srem("busyPro:" + "PC", str);
                RedisUtil.srem("totalUserList", str);
                RedisUtil.srem("userOnlineList", str);
                RedisUtil.del(RedisUtil.getJedis(), "userOnlineList:" + str);

            }
        }
        if (null != iplrange5 && iplrange0.size() > 0) {
            for (String str : iplrange5) {
                RedisUtil.srem("onlinePro:" + "MINIPROGRAM", str);
                RedisUtil.srem("busyPro:" + "MINIPROGRAM", str);
                RedisUtil.srem("totalUserList", str);
                RedisUtil.del(RedisUtil.getJedis(), "userOnlineList:" + str);
            }
        }
        RedisUtil.del(RedisUtil.getJedis(), ipAddress + ":onlinePro:" + "APP");
        RedisUtil.del(RedisUtil.getJedis(), ipAddress + ":onlinePro:" + "PC");
        RedisUtil.del(RedisUtil.getJedis(), ipAddress + ":onlinePro:" + "MINIPROGRAM");
        RedisUtil.del(RedisUtil.getJedis(), ipAddress + ":busyPro:" + "APP");
        RedisUtil.del(RedisUtil.getJedis(), ipAddress + ":busyPro:" + "PC");
        RedisUtil.del(RedisUtil.getJedis(), ipAddress + ":busyPro:" + "MINIPROGRAM");
        RedisUtil.del(RedisUtil.getJedis(), "onlinePro:" + "APP");
        RedisUtil.del(RedisUtil.getJedis(), "onlinePro:" + "PC");
        RedisUtil.del(RedisUtil.getJedis(), "onlinePro:" + "MINIPROGRAM");
        RedisUtil.del(RedisUtil.getJedis(), "busyPro:" + "APP");
        RedisUtil.del(RedisUtil.getJedis(), "busyPro:" + "PC");
        RedisUtil.del(RedisUtil.getJedis(), "busyPro:" + "MINIPROGRAM");
        RedisUtil.del(RedisUtil.getJedis(), "totalUserList");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}


