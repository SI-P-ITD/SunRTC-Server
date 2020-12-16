package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.VideoCallVideoInfoMapper;
import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import com.sinosig.suntrtc.service.VideoCallVideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther lukas
 * @crate 2020-05-28 19:44
 */
@Service
public class VideoCallVideoInfoServiceImpl implements VideoCallVideoInfoService {

    @Autowired
    VideoCallVideoInfoMapper videoCallVideoInfoMapper;



    @Override
    public List<VideoCallVideoInfo> selectByBusinessNo(String businessNo) {
        return videoCallVideoInfoMapper.selectByBusinessNo(businessNo);
    }

    @Override
    public int updateOnlineStatus(String businessNo, String userName) {
        return videoCallVideoInfoMapper.updateOnlineStatus(businessNo,userName);
    }

    @Override
    public int updateByPrimaryKeySelective(VideoCallVideoInfo videoCallVideoInfo) {
        return videoCallVideoInfoMapper.updateByPrimaryKeySelective(videoCallVideoInfo);
    }

    @Override
    public VideoCallVideoInfo selectByBusinessNoAndUserName(String businessNo, String userName) {
        return videoCallVideoInfoMapper.selectByBusinessNoAndUserName(businessNo,userName);
    }

    @Override
    public List<VideoCallVideoInfo> selectByStreamId(String streamId) {
        return videoCallVideoInfoMapper.selectByStreamId(streamId);
    }
}
