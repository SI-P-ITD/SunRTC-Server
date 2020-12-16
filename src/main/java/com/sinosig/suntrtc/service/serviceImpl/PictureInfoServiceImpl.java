package com.sinosig.suntrtc.service.serviceImpl;

import com.sinosig.suntrtc.dao.PictureInfoDao;
import com.sinosig.suntrtc.entity.PictureInfo;
import com.sinosig.suntrtc.service.PictureInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PictureInfoServiceImpl implements PictureInfoService {

    @Autowired
    private PictureInfoDao pictureInfoDao;

    private static Logger log = LoggerFactory.getLogger(PictureInfoServiceImpl.class);

    @Override
    public void savePictureInfo(PictureInfo pictureInfo) {
        pictureInfoDao.insertPictureInfo(pictureInfo);
    }

    @Override
    public List<String> getlistPicture(String bussinessNo) {
        return pictureInfoDao.getlistPicture(bussinessNo);
    }
}
