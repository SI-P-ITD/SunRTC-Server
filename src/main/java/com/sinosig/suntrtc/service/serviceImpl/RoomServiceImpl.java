package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.RoomDao;
import com.sinosig.suntrtc.entity.*;
import com.sinosig.suntrtc.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private static Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private RoomDao roomDao;
    @Override
    public void createRoom(RoomInfo roomInfo) {
        roomDao.createRoom(roomInfo);
    }

    @Override
    public List<RoomInfo> getRoomList(String createUser, String status, String offset, String pageCount) {
        return roomDao.getRoomList(createUser, status, offset, pageCount);
    }

    @Override
    public boolean enterRoomValidate(RoomInfo roomInfo) {
        return roomDao.selectRoomInfo(roomInfo).size() > 0;
    }

    @Override
    public void createBusiness(BusinessInfo businessInfo) {
        roomDao.createBusiness(businessInfo);
    }

    @Override
    public String getCurrentBusinessNo(String roomNo) {
        return roomDao.selectCurrentBusinessNo(roomNo);
    }

    @Override
    public List<Map<String, Object>> getOnlineUserByRoomNo(String roomNo, String eventType) {
        return roomDao.selectVideoCallEventsByRoomNO(roomNo,eventType);
    }

    @Override
    public List<BusinessInfo> getOnlineUserByRoomNo(String roomNo) {
        return roomDao.selectOnlineByRoomNO(roomNo);
    }

    @Override
    public void occurEvent(EventInfo eventInfo) {
        roomDao.insertBusinessEvent(eventInfo);
    }

    @Override
    public List<EventInfo> selectvideoEvents(String businessNo) {
        return roomDao.selectvideoEvents(businessNo);
    }

    @Override
    public boolean startVideoRecord(BusinessVideoInfo businessVideoInfo) {
        return  roomDao.insertBusinessVideoInfo(businessVideoInfo);
    }

    @Override
    public void endBusiness(BusinessInfo businessInfo) {
        roomDao.updateBusinessCauseEndBusiness(businessInfo);
    }

    @Override
    public int getRoomCount(String createUser, String status) {
        return roomDao.selectRoomCount(createUser, status);
    }

    @Override
    public RoomInfo getRoomInfoByBusinessNo(String businessNo) {
        return roomDao.selectRoomInfoByBusinessNo(businessNo);
    }

    @Override
    public RoomInfo getRoomInfoByRoomNo(String roomNo) {
        return roomDao.selectRoomInfoByRoomNo(roomNo);
    }

    @Override
    public void invaildRoom(String roomNo) {
        roomDao.updateRoomStatus(roomNo, "无效");
    }

    @Override
    public String passwordRequiredByRoomNo(String roomNo) {
        return roomDao.passwordRequiredByRoomNo(roomNo);
    }

    @Override
    public String queryRoomNoByOrderId(String orderId) {
        return roomDao.queryRoomNoByOrderId(orderId);
    }

    @Override
    public void relatedCases(RoomInfo roomInfo, OrderInfo orderInfo) {
        roomDao.relatedCases(roomInfo,orderInfo);
    }

    @Override
    public void updateOrderInfo(String orderId, String username) {
        roomDao.updateOrderInfo(orderId,username);
    }

    @Override
    public void updateRoomInfo(String businessNo, String roomNo) {
        roomDao.updateRoomInfo(businessNo,roomNo);
    }

    @Override
    public String selectSurveyorNoByRoomNo(String roomNo) {
        return roomDao.selectSurveyorNoByRoomNo(roomNo);
    }


}
