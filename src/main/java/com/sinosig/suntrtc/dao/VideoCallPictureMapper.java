package com.sinosig.suntrtc.dao;


import com.sinosig.suntrtc.entity.VideoCallPicture;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 图片信息
 */
@Repository
public interface VideoCallPictureMapper {
    int deleteByPrimaryKey(String id);

    int insert(VideoCallPicture record);

    int insertSelective(VideoCallPicture record);

    VideoCallPicture selectByPrimaryKey(String id);

    /**
     * 根据businessNo 查询 List<VideoCallPicture>
     * @param businessNo
     * @return
     */
    List<VideoCallPicture> selectBybusinessNo(String businessNo);

    int updateByPrimaryKeySelective(VideoCallPicture record);

    int updateByPrimaryKey(VideoCallPicture record);
}