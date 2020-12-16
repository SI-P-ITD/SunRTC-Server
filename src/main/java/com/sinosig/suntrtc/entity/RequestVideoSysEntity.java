package com.sinosig.suntrtc.entity;

/**
 * Create by XHH
 * Date 2020-06-04
 */
public class RequestVideoSysEntity {
    private String caseNo;
    private String surveyorNo;
    private String licenseNo;
    //本次视频的视频每一方的唯一id
    private String videoId;
    //本次视频的房间号
    private String roomId;
    //来源 1:车险 2:非车险
    private String resource;
    //图片地址
    private String fileUrl;
    //视频时间
    private String videTime;
    //视频角色 1:发起者 2:接收者
    private String videoRole;

    private String userPhone;

    private String snedUrl;

    private String reportName;

    private String damagePlace;

    //连线环节
    private String link;

    //估损单号
    private String eestimateNo;

    private String id;

    public String getEestimateNo() {
        return eestimateNo;
    }

    public void setEestimateNo(String eestimateNo) {
        this.eestimateNo = eestimateNo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDamagePlace() {
        return damagePlace;
    }

    public void setDamagePlace(String damagePlace) {
        this.damagePlace = damagePlace;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getSnedUrl() {
        return snedUrl;
    }

    public void setSnedUrl(String snedUrl) {
        this.snedUrl = snedUrl;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    private com.alibaba.fastjson.JSONObject JSONObject;


    public com.alibaba.fastjson.JSONObject getJSONObject() {
        return JSONObject;
    }

    public void setJSONObject(com.alibaba.fastjson.JSONObject JSONObject) {
        this.JSONObject = JSONObject;
    }

    public RequestVideoSysEntity() {
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getSurveyorNo() {
        return surveyorNo;
    }

    public void setSurveyorNo(String surveyorNo) {
        this.surveyorNo = surveyorNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getVideTime() {
        return videTime;
    }

    public void setVideTime(String videTime) {
        this.videTime = videTime;
    }

    public String getVideoRole() {
        return videoRole;
    }

    public void setVideoRole(String videoRole) {
        this.videoRole = videoRole;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
