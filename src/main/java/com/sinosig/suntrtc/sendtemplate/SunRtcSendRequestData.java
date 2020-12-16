package com.sinosig.suntrtc.sendtemplate;/**
 * Create by XHH
 * Date 2020-05-18
 */

/**
 * Create by XHH
 * Date 2020-05-18
 * 用于sunrtc消息推送封装对象
 */
public class SunRtcSendRequestData<T> {
    //请求状态码
    private String code;
    //请求状态码描述
    private String msg;

    private String toUserCode;
    //传入参数类型(适用于任何object)
    private T obj;

    public SunRtcSendRequestData() {
    }

    public SunRtcSendRequestData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(String toUserCode) {
        this.toUserCode = toUserCode;
    }

    @Override
    public String toString() {
        return "SunRtcSendRequestData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", toUserCode='" + toUserCode + '\'' +
                ", obj=" + obj +
                '}';
    }
}
