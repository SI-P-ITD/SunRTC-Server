package com.sinosig.suntrtc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventInfo  implements Serializable {
    private String businessNo;
    private String eventType;
    private String eventOwner;
    private String eventTimestamp;
    private String createTime;
    private String updateTime;
}
