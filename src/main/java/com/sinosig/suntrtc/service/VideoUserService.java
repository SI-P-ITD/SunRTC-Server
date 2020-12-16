package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.VideoSuntrcUser;

import java.util.List;

public interface VideoUserService {

    int deleteByPrimaryKey(String id);

    int insert(VideoSuntrcUser record);

    int insertSelective(VideoSuntrcUser record);

    VideoSuntrcUser selectByOne(String id,String usercode);

    List<VideoSuntrcUser> selectUserList(String username);

    int updateByPrimaryKeySelective(VideoSuntrcUser record);

    int updateByPrimaryKey(VideoSuntrcUser videoUser);
}
