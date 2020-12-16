package com.sinosig.suntrtc.dao;


import com.sinosig.suntrtc.entity.VideoInvitationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoInvitationRecordMapper {
    int deleteByPrimaryKey(String id);
    int insert(VideoInvitationRecord record);

    int insertSelective(VideoInvitationRecord record);

    VideoInvitationRecord selectByPrimaryKey(String id);

    /**
     * 通过orderId查询，有效的，并且按照时间倒序
     * @param orderId
     * @return
     */
    List<VideoInvitationRecord> selectByOrderId(@Param(value = "orderId") String orderId, @Param(value = "roomId") String roomId);

    int updateByPrimaryKeySelective(VideoInvitationRecord record);

    int updateByPrimaryKey(VideoInvitationRecord record);
}