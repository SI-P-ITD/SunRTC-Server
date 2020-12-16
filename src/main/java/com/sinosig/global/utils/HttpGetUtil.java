package com.sinosig.global.utils;


import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 请求工具类
 * @author: xhh
 */
@Slf4j
public class HttpGetUtil {

    private HttpGetUtil() {
    }
    
    public static String doGet(String baseUrl, Map<String, Object> paramMap,String json) {
        StringBuffer queryString = new StringBuffer();
        int i = 0;
        for (String param : paramMap.keySet()) {
            Object value = paramMap.get(param);
            queryString.append(param).append("=").append(value);
            if (paramMap.size() - 1 != i) {
                queryString.append("&");
                i = i + 1;
            }
        }
        return doGet(baseUrl, queryString.toString(), json);
    }


    public static String doGet(String baseUrl, String param,String json) {
        String result = "";
        BufferedReader in = null;
        PrintWriter out = null;
        String line;
        StringBuilder sb=new StringBuilder();


        try {
            String urlNameString = baseUrl;
            if (param != null) {
                urlNameString = baseUrl + "?" + param;
            }
            log.info("http请求：" + urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("content-type", "text/plain;charset=UTF-8");
            //设置超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            // 建立实际的连接
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();


        } catch (Exception e) {
            log.info("发送post请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
