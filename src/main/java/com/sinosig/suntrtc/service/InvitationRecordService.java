package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.VideoInvitationRecord;

public interface InvitationRecordService {

    void saveInvitationRecord(VideoInvitationRecord invitationRecord);


    void updateInvitationRecord(String state, String orderId);

    VideoInvitationRecord selectRecordByOrderId(String orderId);

    String selectOrderIdByRoomId(String roomNo);
}
