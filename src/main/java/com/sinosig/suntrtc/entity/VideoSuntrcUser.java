package com.sinosig.suntrtc.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ygbx_video_suntrc_user
 * @author 
 */
@Data
public class VideoSuntrcUser implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String usercode;

    /**
     * 来源
     */
    private String source;

    /**
     * 账户状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;



    private static final long serialVersionUID = 1L;
}