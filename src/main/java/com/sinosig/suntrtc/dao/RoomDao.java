package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface RoomDao {

    void createRoom(RoomInfo roomInfo);

    List<RoomInfo> getRoomList(@Param("createUser") String createUser, @Param("status") String status, @Param("offset") String offset, @Param("pageCount") String pageCount);

    List<RoomInfo> selectRoomInfo(RoomInfo roomInfo);

    List<Map<String, Object>> selectVideoCallEventsByRoomNO(String roomNo, String eventType);

    List<BusinessInfo> selectOnlineByRoomNO(String roomNo);

    void createBusiness(BusinessInfo businessInfo);

    String selectCurrentBusinessNo(String roomNo);

    void insertBusinessEvent(EventInfo eventInfo);

    List<EventInfo> selectvideoEvents(String businessNo);

    boolean insertBusinessVideoInfo(BusinessVideoInfo businessVideoInfo);

    void updateBusinessCauseEndBusiness(BusinessInfo businessInfo);

    int selectRoomCount(@Param("createUser") String createUser, @Param("status") String status);

    RoomInfo selectRoomInfoByBusinessNo(String businessNo);

    RoomInfo selectRoomInfoByRoomNo(String roomNo);

    void updateRoomStatus(@Param("roomNo") String roomNo, @Param("status") String status);

    String passwordRequiredByRoomNo(String roomNo);

    String queryRoomNoByOrderId(String orderId);

    void relatedCases(@Param("roomInfo") RoomInfo roomInfo, @Param("orderInfo") OrderInfo orderInfo);

    void updateOrderInfo(@Param("orderId") String orderId, @Param("username") String username);

    void updateRoomInfo(@Param("businessNo") String businessNo, @Param("roomNo") String roomNo);

    String selectSurveyorNoByRoomNo(String roomNo);

    RoomInfo selectRoomUserByBussinesNo(String roomNo);
}
