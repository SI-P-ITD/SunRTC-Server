package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.EventInfo;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @create by
 * @description 视频录制过程事件发生记录表
 */
@Repository
public interface VideoCallEventsMapper {

    /**
     * 根据流水号和用户名查询事件信息
     * @param businessNo 事务流水号
     * @param username 用户名
     * @return EventInfo
     */
   EventInfo selectEventInfo(String businessNo, String username, String eventType);

}
