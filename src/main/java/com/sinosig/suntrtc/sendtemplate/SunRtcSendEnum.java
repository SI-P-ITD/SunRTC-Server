package com.sinosig.suntrtc.sendtemplate;

/**
 * Create by XHH
 * Date 2020-05-20
 * 用于存放放sunrtc的枚举类
 */
public enum SunRtcSendEnum {

    VIDEOREQUEST("0001", "用户登录sunrtc", "com.sinosig.sunRTC.sendtemplate.websocketsend.VideoRequestWebsocketSunRtcSend"),
    PUSHPICTURE("0002", "图片回传", "com.sinosig.sunRTC.sendtemplate.websocketsend.PushPictureWebsocketSunRtcSend"),
    VIDEO_USER_STATUS("0003", "呼叫中用户状态更改", "com.sinosig.sunRTC.sendtemplate.websocketsend.VideoUserStatusWebsocketSunRtcSend"),
    VIDEOLEAVE("0004", "离开房间或房间解散", "com.sinosig.sunRTC.sendtemplate.websocketsend.VideoLeaveWebsocketSunRtcSend"),
    VIDEO_CALL_STATUS("0005", "呼叫状态设置", "com.sinosig.sunRTC.sendtemplate.websocketsend.VideoCallStatusWebsocketSunRtcSend"),
    SENDTOVIDEOSYS("0006", "发送给第三方服务videosys服务", "com.sinosig.sunRTC.sendtemplate.thirdpartysend.VideoSysThirdPartySunRtcSend");


    SunRtcSendEnum(String code, String msg, String obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    private String code;
    private String msg;
    private String obj;

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

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
