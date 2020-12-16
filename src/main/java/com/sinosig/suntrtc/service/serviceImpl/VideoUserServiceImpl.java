package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.VideoSuntrcUserMapper;
import com.sinosig.suntrtc.entity.VideoSuntrcUser;
import com.sinosig.suntrtc.service.VideoUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class VideoUserServiceImpl implements VideoUserService {

    @Autowired
    private VideoSuntrcUserMapper videoSuntrcUserDao;

    @Override
    public int deleteByPrimaryKey(String id) {
        return videoSuntrcUserDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(VideoSuntrcUser videoUser) {
        return videoSuntrcUserDao.insert(videoUser);
    }

    @Override
    public int insertSelective(VideoSuntrcUser record) {
        return videoSuntrcUserDao.insertSelective(record);
    }

    @Override
    public VideoSuntrcUser selectByOne(String id,String userName) {
        return videoSuntrcUserDao.selectByOne(id,userName);
    }


    @Override
    public List<VideoSuntrcUser> selectUserList(String username) {
        return videoSuntrcUserDao.selectUserList(username);
    }


    @Override
    public int updateByPrimaryKeySelective(VideoSuntrcUser record) {
        return videoSuntrcUserDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(VideoSuntrcUser record) {
        return videoSuntrcUserDao.updateByPrimaryKey(record);
    }
}
