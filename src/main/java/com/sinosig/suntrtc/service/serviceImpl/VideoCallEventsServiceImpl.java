package com.sinosig.suntrtc.service.serviceImpl;


import com.sinosig.suntrtc.dao.VideoCallEventsMapper;
import com.sinosig.suntrtc.entity.EventInfo;
import com.sinosig.suntrtc.service.VideoCallEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoCallEventsServiceImpl implements VideoCallEventsService {

    @Autowired
    private VideoCallEventsMapper videoCallEventsMapper;

    @Override
    public EventInfo selectEventInfo(String businessNo, String username, String eventType) {
        return videoCallEventsMapper.selectEventInfo(businessNo,username,eventType);
    }
}
