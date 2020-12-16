package com.sinosig.suntrtc.entity;

import java.util.Date;

/**
 * 图片信息
 */
public class VideoCallPicture {
    private String id;

    private String businessNo;

    private String pictureTimestamp;

    private String picturePath;

    private String pictureLength;

    private String pictureSize;

    private String pictureFormat;

    private String pictureSource;

    private String pictureProducer;

    private String pictureOperator;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    public String getPictureTimestamp() {
        return pictureTimestamp;
    }

    public void setPictureTimestamp(String pictureTimestamp) {
        this.pictureTimestamp = pictureTimestamp == null ? null : pictureTimestamp.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getPictureLength() {
        return pictureLength;
    }

    public void setPictureLength(String pictureLength) {
        this.pictureLength = pictureLength == null ? null : pictureLength.trim();
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize == null ? null : pictureSize.trim();
    }

    public String getPictureFormat() {
        return pictureFormat;
    }

    public void setPictureFormat(String pictureFormat) {
        this.pictureFormat = pictureFormat == null ? null : pictureFormat.trim();
    }

    public String getPictureSource() {
        return pictureSource;
    }

    public void setPictureSource(String pictureSource) {
        this.pictureSource = pictureSource == null ? null : pictureSource.trim();
    }

    public String getPictureProducer() {
        return pictureProducer;
    }

    public void setPictureProducer(String pictureProducer) {
        this.pictureProducer = pictureProducer == null ? null : pictureProducer.trim();
    }

    public String getPictureOperator() {
        return pictureOperator;
    }

    public void setPictureOperator(String pictureOperator) {
        this.pictureOperator = pictureOperator == null ? null : pictureOperator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}