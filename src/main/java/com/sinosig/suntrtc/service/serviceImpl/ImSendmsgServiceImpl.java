package com.sinosig.suntrtc.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.global.utils.HttpGetUtil;
import com.sinosig.global.utils.StringUtil;
import com.sinosig.suntrtc.consts.SdkType;
import com.sinosig.suntrtc.engin.IMMsgBean;
import com.sinosig.suntrtc.engin.IMMsgBeanMutli;
import com.sinosig.suntrtc.entity.SDKParams;
import com.tencentyun.TLSSigAPIv2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Create by lukas
 * Date 2020-06-28
 * im 信息发送工具类
 */
@Service
@Slf4j
public class ImSendmsgServiceImpl {

    @Value("${sendmsg}")
    private String sendmsg;
    @Value("${batchsendmsg}")
    private String batchsendmsg;
    private long expire = 60 * 60 * 24 * 7;
    @Autowired
    private SdkType sdkType;


    /**
     * @describe: IM发送公共方法
     * @author: lukas
     */
    public  JSONObject imSendmsg(Object text, JSONArray toAccount) {
        JSONObject resultContent = new JSONObject();
        try {

            SDKParams sdkParams = sdkType;

            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(Long.valueOf(sdkParams.getAppId()), sdkParams.getSecretKey());

            String sdkappid = sdkParams.getAppId();
            //后端发送必须使用administrator账户
            String identifier = "administrator";
            String userSig = tlsSigAPIv2.genSig("administrator", Long.valueOf(expire));

            JSONObject msgObject = new JSONObject();
            msgObject.put("MsgType", "TIMTextElem");
            JSONObject textObject = new JSONObject();
            textObject.put("Text", text);
            msgObject.put("MsgContent", textObject);
            JSONArray array = new JSONArray();
            array.add(msgObject);

            IMMsgBean imMsgBean = null;
            IMMsgBeanMutli IMMsgBeanMutli = null;
            if(toAccount.size() == 1){
                log.info("单方发送");
                imMsgBean = new IMMsgBean(2, toAccount.get(0).toString(), 0, StringUtil.getRamdom(),  System.currentTimeMillis() / 1000, array);
            }else{
                log.info("多方发送");
                IMMsgBeanMutli = new IMMsgBeanMutli(2, toAccount, 0, StringUtil.getRamdom(),  System.currentTimeMillis() / 1000, array);

            }
            Map<String, Object> map = new HashMap<>();
            map.put("sdkappid", sdkappid);
            map.put("identifier", identifier);
            map.put("usersig", userSig);
            map.put("random", StringUtil.getRamdom());
            map.put("contenttype", "json");
            log.info("传送的报文为:" + JSONObject.toJSONString(imMsgBean==null?IMMsgBeanMutli:imMsgBean));

            String  result ;
            if(toAccount.size() == 1){
                result = HttpGetUtil.doGet(sendmsg, map, JSONObject.toJSONString(imMsgBean));
            }else{
                result = HttpGetUtil.doGet(batchsendmsg, map, JSONObject.toJSONString(IMMsgBeanMutli));
            }
            log.info("im返回的报文为:"+result);
            resultContent.put("result", result);
            resultContent.put("sign", userSig);

            return JSON.parseObject(result);
        } catch (Exception e) {
            log.error("ImSendmsgUtil->error:" + e);
        }
        return null;


    }

}
