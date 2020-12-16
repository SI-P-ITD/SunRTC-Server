package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.VideoCallBusiness;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCallBusinessMapper {
    int deleteByPrimaryKey(String businessNo);

    int insert(VideoCallBusiness record);

    int insertSelective(VideoCallBusiness record);

    VideoCallBusiness selectByPrimaryKey(String businessNo);

    int updateByPrimaryKeySelective(VideoCallBusiness record);

    int updateByPrimaryKey(VideoCallBusiness record);
}