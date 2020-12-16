package com.sinosig.suntrtc.dao;

import com.sinosig.suntrtc.entity.VideoTimeInfo;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @create by
 * @description
 */
@Repository
public interface VideoTimeMapper {
    /**
     *  更新累计通话视察那个
     * @param times
     * @return
     */
    boolean updateVideoTime(String month, Long times);

    /**
     * 查询累计通话时长
     * @param month
     * @return
     */
    VideoTimeInfo selectVideoTime(String month);

    /**
     * 插入通话时间
     * @param month
     * @param times
     * @return boolean
     */
    boolean insertVideoTime(String month, long times);
}
