package cn.appsys.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static ConfigManager configManager;
	private static Properties properties;
	//˽�й�����-��ȡ���ݿ������ļ�
	private ConfigManager(){
		String configFile = "database.properties";
		properties = new Properties();
		InputStream is = 
				ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
		try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ȫ�ַ��ʵ�
	public static synchronized ConfigManager getInstance(){
		if(configManager == null){
			configManager = new ConfigManager();
		}
		return configManager;
	}
	
	public String getValue(String key){
		return properties.getProperty(key);
	}

}
