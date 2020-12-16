package com.sinosig.suntrtc.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe: 连线终端枚举
 * @author: wangzi
 * @date: 2020/8/26 6:12
 */
@Getter
public enum ConnectionTerminalEnums {
    PC("PC","电脑端"),
    APP("APP", "手机APP端");

    private String name;
    private String code;

    ConnectionTerminalEnums(String code,String name){
        this.name=name;
        this.code=code;
    }

    /**
     * @describe: 剩余终端集合
     */
    public static List<String>    remainsTerminalList(String terminal){
         List list=new ArrayList();
         for (ConnectionTerminalEnums terminalEnum : ConnectionTerminalEnums.values()) {
             if(!terminalEnum.getCode().equals(terminal)){
                 list.add(terminalEnum.getCode());
             }
        }
         return list;
    }
    /**
     * @describe: 所有终端集合
     */
    public static List<String>    allTerminalList(){
        List list=new ArrayList();
        for (ConnectionTerminalEnums terminalEnum : ConnectionTerminalEnums.values()) {
                list.add(terminalEnum.getCode());
        }
        return list;
    }

}

