package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther lukas
 * @crate 2020-05-28 19:43
 */
public interface VideoCallVideoInfoService {

    List<VideoCallVideoInfo> selectByBusinessNo(String businessNo);

    int updateOnlineStatus(@Param("businessNo") String businessNo , @Param("userName") String userName);

    int updateByPrimaryKeySelective(VideoCallVideoInfo videoCallVideoInfo);

    VideoCallVideoInfo selectByBusinessNoAndUserName(String businessNo,String userName);

    List<VideoCallVideoInfo>  selectByStreamId(String StreamId);
}
