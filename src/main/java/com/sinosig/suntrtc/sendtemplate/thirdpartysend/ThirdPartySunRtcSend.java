package com.sinosig.suntrtc.sendtemplate.thirdpartysend;/**
 * Create by XHH
 * Date 2020-06-04
 */

import com.sinosig.suntrtc.sendtemplate.SunRtcSend;
import com.sinosig.suntrtc.sendtemplate.SunRtcSendRequestData;

/**
 * Create by XHH
 * Date 2020-06-04
 */
public abstract class ThirdPartySunRtcSend implements SunRtcSend {

    public void send(SunRtcSendRequestData data) {
        pre();
        sendMessage(data);
    }

    public abstract void pre();

}
