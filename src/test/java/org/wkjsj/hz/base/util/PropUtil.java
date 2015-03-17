package org.wkjsj.hz.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.log4testng.Logger;

public class PropUtil {
	private static Properties properties = null;
	private static Logger logger = Logger.getLogger(PropUtil.class);
	public PropUtil(String path){
		initialize(path);
		
	}
	private void initialize(String path){
		InputStream is = getClass().getClassLoader().getResourceAsStream(path);
		if(is ==null){
			logger.error("The properties is null.");
			return;
		}
		properties = new Properties();
		try{
			properties.load(is);
		}catch(IOException e){
			logger.error("执行PropUtil.initialize()方法发生异常，异常信息：",e);
		}finally{
			try{
				if(is!=null)
					is.close();
			}catch(Exception e){
				logger.error("执行PropUtil.initialize()方法发生异常，异常信息：",e);
			}
		}
	}
	
	public String get(String key){
		String keyValue = null;
		if(properties.containsKey(key)){
			keyValue = (String)properties.get(key);
		}
		return keyValue;
	}

}
