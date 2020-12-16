package com.sinosig.suntrtc.engin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosig.suntrtc.entity.EnginBean;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAction {

	public String executeAction(String uuid, EnginBean enginBean){
		if(!getOutLoginServiceNames().contains(enginBean.getServiceName())){
			//登录校验
		}
		Object o = executeAction(JSONObject.toJSONString(enginBean.getServicePara()), uuid, enginBean);
		return JSON.toJSONString(o);
	}
	
	public abstract Object executeAction(String in,String uuid,EnginBean enginBean); 
	
	private static List<String> outLoginServiceNames = new ArrayList<String>();
	private static List<String> getOutLoginServiceNames(){
		outLoginServiceNames.add("loginAction");//登录页面
		outLoginServiceNames.add("getAliyunSts");//获取阿里上传临时token
		return outLoginServiceNames;
	}
}
