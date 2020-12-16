package com.sinosig.suntrtc.engin;

/**
 * Create by XHH
 * Date 2020-05-09
 * 请求action枚举
 */
public enum ActionEnum {

    TEMPLATE("template", "com.sinosig.sunRTC.action.A_TemplateActionDemo"),

    CALLRESPPOLLING("callRespPolling", "com.sinosig.sunRTC.action.CallRespPollingActionDemo"),

    CALLSURVEYOR("callSurveyor", "com.sinosig.sunRTC.action.CallSurveyorActionDemo"),

    CREATEROOM("createRoom", "com.sinosig.sunRTC.action.CreateRoomActionDemo"),

    CREATESHARELINK("createShareLink", "com.sinosig.sunRTC.action.CreateShareLinkActionDemo"),

    DISMISSROOM("dismissRoom", "com.sinosig.sunRTC.action.DismissRoomActionDemo"),

    ENTERROOM("enterRoom", "com.sinosig.sunRTC.action.EnterRoomActionDemo"),

    ENTERROOMSUCCESS("enterRoomSuccess", "com.sinosig.sunRTC.action.EnterRoomSuccessActionDemo"),

    GETPICTURELIST("getPictureList", "com.sinosig.sunRTC.action.GetPictureListActionDemo"),

    GETSDKPARAMS("getSDKParams", "com.sinosig.sunRTC.action.GetSDKParamsActionDemo"),

    GETSWITCHCONTROL("getSwitchControl", "com.sinosig.sunRTC.action.GetSwitchControlActionDemo"),

    JUDGEPCAGREE("judgePCAgree", "com.sinosig.sunRTC.action.JudgePCAgreeActionDemo"),

    LEAVEROOM("leaveRoom", "com.sinosig.sunRTC.action.LeaveRoomActionDemo"),

    ROOMLIST("roomList", "com.sinosig.sunRTC.action.RoomListActionDemo"),

    STARTVIDEORECORD("startVideoRecord", "com.sinosig.sunRTC.action.StartVideoRecordActionDemo"),

    UPLOADPICCALLBACK("uploadPicCallback", "com.sinosig.sunRTC.action.UploadPicCallbackActionDemo");

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    //服务名称
    private String serviceName;

    //服务反射实体类
    private String clazzName;

    ActionEnum(String serviceName, String clazzName) {
        this.serviceName = serviceName;
        this.clazzName = clazzName;
    }}
