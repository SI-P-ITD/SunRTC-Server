package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.VideoSuntrcUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoSuntrcUserMapper {

    int deleteByPrimaryKey(String id);

    int insert(VideoSuntrcUser record);

    int insertSelective(VideoSuntrcUser record);

    VideoSuntrcUser selectByOne(String id,String usercode);

    List<VideoSuntrcUser> selectUserList(String username);

    int updateByPrimaryKeySelective(VideoSuntrcUser record);

    int updateByPrimaryKey(VideoSuntrcUser record);
}