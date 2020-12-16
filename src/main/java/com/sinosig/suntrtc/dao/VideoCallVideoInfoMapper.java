package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频信息表mapper
 */
@Repository
public interface VideoCallVideoInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(VideoCallVideoInfo record);

    int insertSelective(VideoCallVideoInfo record);

    VideoCallVideoInfo selectByPrimaryKey(String id);

    List<VideoCallVideoInfo> selectByBusinessNo(String businessNo);

    /**
     *  根据流水号 与 用户名查询视频流水数据
     * @param businessNo
     * @param userName
     * @return
     */
    VideoCallVideoInfo selectByBusinessNoAndUserName(@Param(value = "businessNo") String businessNo,@Param(value = "userName")String userName);

    /**
     * 根据Streamid进行查询其中video_path为空的，并按照create_time顺序 查询结果
     * @param streamId
     * @return
     */
    List<VideoCallVideoInfo> selectByStreamId(String streamId);

    int updateByPrimaryKeySelective(VideoCallVideoInfo record);

    int updateByPrimaryKey(VideoCallVideoInfo record);

    int updateOnlineStatus(@Param("businessNo") String businessNo , @Param("userName") String userName);
}