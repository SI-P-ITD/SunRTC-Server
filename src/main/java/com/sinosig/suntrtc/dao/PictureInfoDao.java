package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.PictureInfo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PictureInfoDao {

    void insertPictureInfo(PictureInfo pictureInfo);

    List<String> getlistPicture(String bussinessNo);
}
