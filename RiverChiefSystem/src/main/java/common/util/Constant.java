package common.util;

import java.io.IOException;
import java.util.Properties;

public class Constant {

	public static Properties properties = new Properties();
	static {
		try {
			properties.load(Constant.class.getClassLoader().getResourceAsStream("/conf/path.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 根据属性读取配置文件
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}

