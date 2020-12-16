package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.*;

import java.util.List;
import java.util.Map;

public interface RoomService {

    void createRoom(RoomInfo roomInfo);

    List<RoomInfo> getRoomList(String createUser, String status, String offset, String pageCount);

    boolean enterRoomValidate(RoomInfo roomInfo);

    void createBusiness(BusinessInfo businessInfo);

    String getCurrentBusinessNo(String roomNo);

    List<Map<String,Object>> getOnlineUserByRoomNo(String roomNo,String eventType);

    List<BusinessInfo> getOnlineUserByRoomNo(String roomNo);

    void occurEvent(EventInfo eventInfo);

    List<EventInfo> selectvideoEvents(String businessNo);

    boolean startVideoRecord(BusinessVideoInfo businessVideoInfo);

    void endBusiness(BusinessInfo businessInfo);

    int getRoomCount(String createUser, String status);

    RoomInfo getRoomInfoByBusinessNo(String businessNo);

    RoomInfo getRoomInfoByRoomNo(String roomNo);

    void invaildRoom(String roomNo);

    String passwordRequiredByRoomNo(String roomNo);

    String queryRoomNoByOrderId(String orderId);

    void relatedCases(RoomInfo roomInfo, OrderInfo orderInfo);

    void updateOrderInfo(String orderId, String username);

    void updateRoomInfo(String businessNo, String roomNo);

    String selectSurveyorNoByRoomNo(String roomNo);
}
