package com.sinosig.global.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: Spring容器 工具类
 * @author: Aladdin.Cao
 */
@Service
public class SpringContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getAppilcationContext(){
		return applicationContext;
	}
	
	public static Object getBean(String name){
			return applicationContext.getBean(name);
	}
	
}