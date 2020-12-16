package com.sinosig.suntrtc.enums;

import lombok.Getter;

/**
 * @describe: 项目标识枚举
 * @author: wangzi
 * @date: 2020/8/26 6:49
 */
@Getter
public enum ProjectIdentificationEnum {
    C("C","车理赔事故号首位"),
    R("R", "非车理赔事故号首位");
    private String name;
    private String code;

    ProjectIdentificationEnum(String code,String name){
        this.name=name;
        this.code=code;
    }
}
