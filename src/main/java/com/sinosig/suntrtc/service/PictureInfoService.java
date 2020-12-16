package com.sinosig.suntrtc.service;

import com.sinosig.suntrtc.entity.PictureInfo;

import java.util.List;

public interface PictureInfoService {

    void savePictureInfo(PictureInfo pictureInfo);

    List<String> getlistPicture(String bussinessNo);
}
