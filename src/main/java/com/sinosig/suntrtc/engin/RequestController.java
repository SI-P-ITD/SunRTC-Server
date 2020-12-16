package com.sinosig.suntrtc.engin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.utils.DateUtil;
import com.sinosig.global.utils.RedisUtil;
import com.sinosig.global.utils.SpringContextUtil;
import com.sinosig.global.utils.UUIDUtil;
import com.sinosig.suntrtc.dao.VideoCallBusinessMapper;
import com.sinosig.suntrtc.dao.VideoCallOrderInfoMapper;
import com.sinosig.suntrtc.dao.VideoCallVideoInfoMapper;
import com.sinosig.suntrtc.entity.EnginBean;
import com.sinosig.suntrtc.entity.VideoCallBusiness;
import com.sinosig.suntrtc.entity.VideoCallOrderInfo;
import com.sinosig.suntrtc.entity.VideoCallVideoInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("request")
public class RequestController {

    private static Logger log = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    private VideoCallVideoInfoMapper videoInfoMapper;

    @Autowired
    private VideoCallBusinessMapper businessMapper;

    @Autowired
    private VideoCallOrderInfoMapper orderInfoMapper;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public void doPost(HttpServletResponse response,@RequestBody EnginBean enginBean) {
        PrintWriter out = null;
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            //允许请求方式
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            //用来指定本次预检请求的有效期，单位为秒，在此期间不用发出另一条预检请求
            response.setHeader("Access-Control-Max-Age", "3600");
            //请求包含的字段内容，如有多个可用哪个逗号分隔如下
            response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,Authorization, x-ui-request,lang");
            //访问控制允许凭据，true为允许
            response.setHeader("Access-Control-Allow-Credentials", "true");
            String result;
            String uuid = UUIDUtil.getUUID();
            log.info("{}->请求参数为->{}", uuid, JSON.toJSONString(enginBean));
            // redis 接口请求计数，用于压测
            String serviceName = enginBean.getServiceName();
            if(serviceName.contains("Multi")){
                RedisUtil.incr(DateUtil.date2String(new Date()) + ":" + serviceName);
            }
            BaseAction serviceAction = (BaseAction) SpringContextUtil.getBean(enginBean.getServiceName());
            result = serviceAction.executeAction(uuid, enginBean);
            log.info("{} 返回 {}", uuid, result);
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            //h5页面请求，bizId区分移动端和h5页面
            if(StringUtils.isNotBlank(enginBean.getRequestSource()) && "h5".equals(enginBean.getRequestSource())){
                out.println("successCallback("+result+")");//解决jsonp跨域
            }else{
                out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }

    /**
     * 需要解决stream_id有重复的问题，同一个orderId会有多个视屏流的问题
     * video_call_video_info操作逻辑：
     * 1、根据Streamid进行查询其中video_path为空的，并按照create_time顺序 查询结果
     * 2、把查询出来的第一条记录进行插入file_id、video_path、udpate_time
     *
     * 业务逻辑
     * 1、保存信息
     * @param request
     * @param json
     */
    @RequestMapping(value = "/recordCallBack", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void recordCallBack(HttpServletRequest request, @RequestBody JSONObject json) {
        if(null == json){
            log.info("请求json为空");
            return;
        }
        log.info("请求体：{}",json);

        String videoUrl = json.getString("video_url");
        String streamId = json.getString("stream_id");
        String fileId = json.getString("record_file_id");
        Long startTime = json.getLongValue("start_time");
        Long endTime = json.getLongValue("end_time");


        if(streamId == null || streamId.isEmpty()){
            return;
        }

        List<VideoCallVideoInfo> videoInfos = videoInfoMapper.selectByStreamId(streamId);
        if(null == videoInfos || videoInfos.size() < 1){
            log.info("videoInfos 无数据：");
            return;
        }

        //1、保存信息
        VideoCallVideoInfo videoInfo = videoInfos.get(0);
        videoInfo.setFileId(fileId);
        videoInfo.setVideoPath(videoUrl);
        videoInfo.setUpdateTime(new Date());
        videoInfoMapper.updateByPrimaryKeySelective(videoInfo);

    }


    /**
     * 根据businessNo获取orderId
     * @param businessNo
     * @return
     */
    private VideoCallOrderInfo getOrderInfo(String businessNo){

        List<VideoCallOrderInfo> infoList = orderInfoMapper.selectByRoomNo(getRoomNo(businessNo));
        if(null == infoList || infoList.size() == 0){
            throw new RuntimeException("业务错误");
        }
        return infoList.get(0);
    }

    /**
     * 根据businessNo获取orderId
     * @param businessNo
     * @return
     */
    private String getRoomNo(String businessNo){
        VideoCallBusiness business = businessMapper.selectByPrimaryKey(businessNo);
        if(null == business){
            throw new RuntimeException("业务错误");
        }

        return business.getRoomNo();
    }

}
