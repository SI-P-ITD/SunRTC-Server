package com.sinosig.suntrtc.sendtemplate;/**
 * Create by XHH
 * Date 2020-05-20
 */

import com.sinosig.suntrtc.sendtemplate.thirdpartysend.ThirdPartySunRtcSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by XHH
 * Date 2020-05-20
 * 用于给前端推送websocket管理类,此类设计为单例模式
 */
public class SunRtcSendManager {

    private static Logger log = LoggerFactory.getLogger(SunRtcSendManager.class);

    private static SunRtcSendManager managerWebsocketSunRtcSend;

    private static Map<String, String> map = new ConcurrentHashMap<>();

    static {
        for (SunRtcSendEnum value : SunRtcSendEnum.values()) {
            map.put(value.getCode(), value.getObj());
        }
    }

    private SunRtcSendManager() {

    }

    public static SunRtcSendManager getInstance() {
        if (managerWebsocketSunRtcSend == null) {
            synchronized (SunRtcSendManager.class) {
                if (managerWebsocketSunRtcSend == null) {
                    managerWebsocketSunRtcSend = new SunRtcSendManager();
                }
            }
        }
        return managerWebsocketSunRtcSend;
    }



    public void sendMessgeToThirdParty(SunRtcSendRequestData data) throws Exception {
        log.info("ThirdPartySunRtcSend->begin");
        String obj = map.get(data.getCode());
        Class<?> clzz = Class.forName(obj);
        ThirdPartySunRtcSend o = (ThirdPartySunRtcSend) clzz.newInstance();
        o.send(data);
        log.info("ThirdPartySunRtcSend->end");
    }

}
