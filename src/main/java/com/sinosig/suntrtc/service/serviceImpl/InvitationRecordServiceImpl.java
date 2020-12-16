package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.InvitationRecordDao;
import com.sinosig.suntrtc.entity.VideoInvitationRecord;
import com.sinosig.suntrtc.service.InvitationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationRecordServiceImpl implements InvitationRecordService {
    @Autowired
    InvitationRecordDao invitationRecordDao;
    @Override
    public void saveInvitationRecord(VideoInvitationRecord invitationRecord) {
        invitationRecordDao.saveInvitationRecord(invitationRecord);
    }

    @Override
    public void updateInvitationRecord(String state, String orderId) {
        invitationRecordDao.updateInvitationRecord(state,orderId);
    }

    @Override
    public VideoInvitationRecord selectRecordByOrderId(String orderId) {
        return invitationRecordDao.selectRecordByOrderId(orderId);
    }

    @Override
    public String selectOrderIdByRoomId(String roomNo) {
        return invitationRecordDao.selectOrderIdByRoomId(roomNo);
    }


}
