package com.sinosig.global.entity;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: IM消息以及对应码值
 * @author: Aladdin.Cao
 */
public class Constants {

    private Constants() {
    }

    public static final String RESCODE_SUCCESS = "0000";
    public static final String RESMSG_SUCCESS = "成功";
    public static final String RESCODE_FAIL = "0001";
    public static final String RESMSG_FAIL = "失败";
    public static final String RESCODE_REQUEST_PARAM_IS_NULL = "1002";
    public static final String RESMSG_REQUEST_PARAM_IS_NULL = "请求字段为空";
    public static final String RESCODE_QUERY_RESULT_IS_NULL = "1003";
    public static final String RESMSG_QUERY_RESULT_IS_NULL = "未获取到相关数据";
    public static final String RESCODE_VALIDATE_FAILED = "1004";
    public static final String RESMSG_VALIDATE_FAILED = "密码验证错误";
    public static final String RESCODE_INVALID_PROCESS = "1005";
    public static final String RESMSG_INVALID_PROCESS = "流程非法";
    public static final String RESCODE_SURVEYOR_PROCESS = "1006";
    public static final String RESMSG_SURVEYOR_PROCESS = "未找到空闲查勘员";
    public static final String RESCODE_NOANSWER_PROCESS = "1007";
    public static final String RESMSG_NOANSWER_PROCESS = "关闭呼叫";
    public static final String RESCODE_ANALYZE_PARM_FAILED = "1008";
    public static final String RESMSG_ANALYZE_PARM_FAILED = "参数解析失败";
    public static final String RESCODE_USERNAME_ILLEGAL = "1010";
    public static final String RESMSG_USERNAME_ILLEGAL = "账户名称不符合规范";
    public static final String RESCODE_VIDEO_END = "1020";
    public static final String RESMSG_VIDEO_END = "视频会议已结束";
    public static final String RESCODE_VIDEO_REFUSE = "1021";
    public static final String RESMSG_VIDEO_REFUSE = "拒接成功";
    public static final String RESCODE_DEVICE_PUBLICEINFO = "1030";
    public static final String RESMSG_DEVICE_PUBLICEINFO = "设备公共信息";
    public static final String RESCODE_VIDEOKNOWS_PROCESS = "1031";
    public static final String RESMSG_VIDEOKNOWS_PROCESS = "视频指导人员不在线，请稍后再试";
    public static final String RESCODE_MULTI_MESSAGE_SEND = "1032";
    public static final String RESMSG_MULTI_MESSAGE_SEND = "文字信息多方发送";
    public static final String VEDIO_NOTICE_CODE = "2000";
    public static final String VEDIO_NOTICE_MSG = "向您发起视频邀请";
    public static final String VEDIO_SUCCESS_CODE = "2010";
    public static final String VEDIO_SUCCESS_MSG = "被呼叫方接受成功";
    public static final String PICTURE_SHOW_CODE = "2020";
    public static final String PICTURE_SHOW_MSG = "图片信息回显";
    public static final String VEDIO_USERBUSY_CODE = "2030";
    public static final String VEDIO_USERBUSY_MSG = "拒绝了您的视频邀请";
    public static final String VEDIO_CANCEL_POPUP_CODE = "2031";
    public static final String VEDIO_CANCEL_POPUP_MSG = "取消了视频请求";
    public static final String VEDIO_ANSWER_AGREEE_CODE = "2032";
    public static final String VEDIO_ANSWER_AGREEE_MSG = "已在其他平台接听";
    public static final String VEDIO_NORESP_ONETIPS_CODE = "2033";
    public static final String VEDIO_NORESP_ONETIPS_MSG = "现在可能不方便接听，建议稍后再次尝试";
    public static final String VEDIO_NORESP_TWOTIPS_CODE = "2034";
    public static final String VEDIO_NORESP_TWOTIPS_MSG = "无应答";
    public static final String VEDIO_IN_CODE = "2035";
    public static final String VEDIO_IN_MSG = "您正在视频中，请稍后再进行此操作";
    public static final String VEDIO_ANSWER_POPUP_CODE = "2040";
    public static final String VEDIO_ANSWER_POPUP_MSG = "已在其他平台拒绝";
    public static final String RESCODE_LOGIN_REPEATEDLY = "2050";
    public static final String RESMSG_LOGIN_REPEATEDLY = "您的账号尝试在其他地方登录，如非本人操作请注意账号安全";
    public static final String RESCODE_VIDEO_FUNCTION_DISABLED = "2060";
    public static final String RESMSG_VIDEO_FUNCTION_DISABLED = "视频功能暂不可用";
    public static final String DISMISS_ROOM_CODE = "3000";
    public static final String DISMISS_ROOM_MSG = "离开房间";
    public static final String VIDEO_TIME_CODE = "3010";
    public static final String VIDEO_TIME_MSG = "当月视频通话已到最大值，请下月进行体验";

    //redis缓存key前缀（用户分平台在线）
    public static final String IM_ONLINE_ECLAIM = "Im_online:Eclaim:";
    //redis缓存key前缀（用户全部在线）
    public static final String IM_TOTAL_LIST = "Im_totalList";

    //发送案件信息
    public static final String SENDCASEINFO = "sendCaseInfo";
    //同步图片接口
    public static final String SENDPICTUREINFO = "sendPictureInfo";
    //案件结束接口
    public static final String CASEEND = "caseEnd";
    public static final String SDK_NONCAR = "noncar";
    public static final String SDK_CARCLAIM = "carclaim";
    public static final String STATUS_OK = "OK";



}
