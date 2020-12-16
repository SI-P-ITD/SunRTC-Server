package com.sinosig.tencentCloud.utils;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.trtc.v20190722.TrtcClient;
import com.tencentcloudapi.trtc.v20190722.models.*;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.MediaInfo;
import com.tencentcloudapi.vod.v20180717.models.SearchMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.SearchMediaResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentCloudUtil {

    /**
     * 解散trtc房间
     */
    public static void dismissRoom(String roomId, String ak, String sk, String region, String sdkAppId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential(ak, sk);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
            TrtcClient trtcClient = new TrtcClient(cred, region, clientProfile);
            DismissRoomRequest dissolveRoomRequest = new DismissRoomRequest();
            dissolveRoomRequest.setRoomId(Long.valueOf(roomId));
            dissolveRoomRequest.setSdkAppId(Long.valueOf(sdkAppId));
            DismissRoomResponse dissolveRoomResponse = trtcClient.DismissRoom(dissolveRoomRequest);
            log.info(dissolveRoomResponse.toString());
        } catch (TencentCloudSDKException e) {
            log.error(e.toString());
        }

    }


    public static void StartMCUMixTranscode(String roomId, String ak, String sk, String region, String sdkAppId,String streamId) throws TencentCloudSDKException {
        Credential cred = new Credential(ak, sk);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
        TrtcClient trtcClient = new TrtcClient(cred, region, clientProfile);
        StartMCUMixTranscodeRequest startMCUMixTranscodeRequest = new StartMCUMixTranscodeRequest();
        startMCUMixTranscodeRequest.setSdkAppId(Long.valueOf(sdkAppId));
        startMCUMixTranscodeRequest.setRoomId(Long.valueOf(roomId));
        OutputParams outputParams = new OutputParams();
        outputParams.setStreamId(streamId);
        outputParams.setPureAudioStream(0L);
        outputParams.setRecordId(streamId+"_file");
        EncodeParams encodeParams = new EncodeParams();
        encodeParams.setVideoWidth(1280L);
        encodeParams.setVideoHeight(1000L);
        encodeParams.setVideoBitrate(1560L);
        encodeParams.setVideoFramerate(15L);
        encodeParams.setVideoGop(2L);
        encodeParams.setBackgroundColor(0L);
        encodeParams.setAudioSampleRate(48000L);
        encodeParams.setAudioBitrate(64L);
        encodeParams.setAudioChannels(2L);
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.setTemplate(1L);
        startMCUMixTranscodeRequest.setOutputParams(outputParams);
        startMCUMixTranscodeRequest.setEncodeParams(encodeParams);
        startMCUMixTranscodeRequest.setLayoutParams(layoutParams);
        StartMCUMixTranscodeResponse startMCUMixTranscodeResponse = trtcClient.StartMCUMixTranscode(startMCUMixTranscodeRequest);
        log.info("startMCUMixTranscodeResponse->"+ JSONObject.toJSONString(startMCUMixTranscodeResponse));
    }

    public static void StopMCUMixTranscode(String roomId, String ak, String sk, String region, String sdkAppId) throws TencentCloudSDKException {
        Credential cred = new Credential(ak, sk);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
        TrtcClient trtcClient = new TrtcClient(cred, region, clientProfile);
        StopMCUMixTranscodeRequest stopMCUMixTranscodeRequest = new StopMCUMixTranscodeRequest();
        stopMCUMixTranscodeRequest.setSdkAppId(Long.valueOf(sdkAppId));
        stopMCUMixTranscodeRequest.setRoomId(Long.valueOf(roomId));
        StopMCUMixTranscodeResponse stopMCUMixTranscodeResponse = trtcClient.StopMCUMixTranscode(stopMCUMixTranscodeRequest);
        log.info("stopMCUMixTranscodeResponse->"+ JSONObject.toJSONString(stopMCUMixTranscodeResponse));

    }

    /**
     * 解散trtc房间
     */
    public static void vod(String ak, String sk, String region) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential(ak, sk);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
            VodClient vodClient = new VodClient(cred, region, clientProfile);
            SearchMediaRequest searchMediaRequest = new SearchMediaRequest();
            searchMediaRequest.setStreamId("3f52a4cceea74f2396a47a1960c8933a");
            SearchMediaResponse searchMediaResponse = vodClient.SearchMedia(searchMediaRequest);
            MediaInfo[] mediaInfoSet = searchMediaResponse.getMediaInfoSet();
            for (MediaInfo mediaInfo : mediaInfoSet) {
                log.info(mediaInfo.getFileId());
            }
        } catch (TencentCloudSDKException e) {
            log.error(e.toString());
        }

    }

}
