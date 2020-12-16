package com.sinosig.suntrtc.entity;

import lombok.Data;

/**
 * @author
 * @create by
 * @description
 */
@Data
public class VideoTimeInfo {
    // 当前月份
    String ymTime;
    // 累计时长
    Long cumulativeDuration;
}
