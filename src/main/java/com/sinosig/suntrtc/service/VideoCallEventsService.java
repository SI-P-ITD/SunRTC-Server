package com.sinosig.suntrtc.service;


import com.sinosig.suntrtc.entity.EventInfo;

public interface VideoCallEventsService {

    EventInfo selectEventInfo(String businessNo, String username, String eventType);
}
