package com.sinosig.suntrtc.engin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @auther lukas
 * @crate 2020-06-28 11:26
 */
public class IMMsgBean {
    /**
     * // 消息不同步至发送方 2
     */
    @JSONField(name = "SyncOtherMachine")
    Integer SyncOtherMachine;
    @JSONField(name = "To_Account")
    String To_Account;
    /**
     * 消息保存时间 单位:s
     */
    @JSONField(name = "MsgLifeTime")
    Integer MsgLifeTime;
    @JSONField(name = "MsgRandom")
    Integer MsgRandom;
    @JSONField(name = "MsgTimeStamp")
    long MsgTimeStamp;
    @JSONField(name = "MsgBody")
    JSONArray MsgBody;

    public IMMsgBean() {
    }

    public IMMsgBean(Integer syncOtherMachine, String to_Account, Integer msgLifeTime, Integer msgRandom, long msgTimeStamp, JSONArray msgBody) {
        SyncOtherMachine = syncOtherMachine;
        To_Account = to_Account;
        MsgLifeTime = msgLifeTime;
        MsgRandom = msgRandom;
        MsgTimeStamp = msgTimeStamp;
        MsgBody = msgBody;
    }

    public Integer getSyncOtherMachine() {
        return SyncOtherMachine;
    }

    public void setSyncOtherMachine(Integer syncOtherMachine) {
        SyncOtherMachine = syncOtherMachine;
    }

    public String getTo_Account() {
        return To_Account;
    }

    public void setTo_Account(String to_Account) {
        To_Account = to_Account;
    }

    public Integer getMsgLifeTime() {
        return MsgLifeTime;
    }

    public void setMsgLifeTime(Integer msgLifeTime) {
        MsgLifeTime = msgLifeTime;
    }

    public Integer getMsgRandom() {
        return MsgRandom;
    }

    public void setMsgRandom(Integer msgRandom) {
        MsgRandom = msgRandom;
    }

    public long getMsgTimeStamp() {
        return MsgTimeStamp;
    }

    public void setMsgTimeStamp(long msgTimeStamp) {
        MsgTimeStamp = msgTimeStamp;
    }

    public JSONArray getMsgBody() {
        return MsgBody;
    }

    public void setMsgBody(JSONArray msgBody) {
        MsgBody = msgBody;
    }


}

