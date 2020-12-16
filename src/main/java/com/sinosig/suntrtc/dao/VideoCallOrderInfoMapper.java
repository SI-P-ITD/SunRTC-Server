package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.VideoCallOrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoCallOrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoCallOrderInfo record);

    int insertSelective(VideoCallOrderInfo record);

    VideoCallOrderInfo selectByPrimaryKey(Integer id);

    List<VideoCallOrderInfo> selectByRoomNo(String roomNo);

    int updateByPrimaryKeySelective(VideoCallOrderInfo record);

    int updateByPrimaryKey(VideoCallOrderInfo record);
}