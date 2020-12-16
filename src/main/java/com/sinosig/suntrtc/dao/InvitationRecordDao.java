package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.VideoInvitationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRecordDao {
    void saveInvitationRecord(VideoInvitationRecord invitationRecord);


    void updateInvitationRecord(@Param("state") String state, @Param("orderId") String orderId);

    VideoInvitationRecord selectRecordByOrderId(String orderId);

    String selectOrderIdByRoomId(String roomNo);
}
