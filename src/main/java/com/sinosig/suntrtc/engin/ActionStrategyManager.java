package com.sinosig.suntrtc.engin;/**
 * Create by XHH
 * Date 2020-05-09
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by XHH
 * Date 2020-05-09
 * 公共请求管理类
 */
public class ActionStrategyManager {

    private static ActionStrategyManager actionStrategyManager;

    private static Map<String,String> cacheMap = new ConcurrentHashMap<>();


    static {
        for (ActionEnum value : ActionEnum.values()) {
            cacheMap.put(value.getServiceName(),value.getClazzName());
        }
    }

    public static ActionStrategyManager getInstance() {
        if (actionStrategyManager == null) {
            synchronized (ActionStrategyManager.class) {
                if (actionStrategyManager == null) {
                    actionStrategyManager = new ActionStrategyManager();
                }
            }

        }
        return actionStrategyManager;
    }






}
