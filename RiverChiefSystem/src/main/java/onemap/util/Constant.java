package onemap.util;

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

	public final static String WaterQuality = "11";
	public final static String WaterLine = "12";
	public final static String WaterNumber = "13";
	public final static String Raininfo = "14";
	public final static String InlandWaterQuality = "15";
	public final static String WaterFunction = "16";
	public final static String WaterSource = "17";
	public final static String Event = "18";
	public final static String PublicSigns = "21";
	public final static String test = "22";
	public final static String WaterIntake = "23";
	public final static String Outfall = "24";
	public final static String PollutantSource = "25";
	public final static String WaterConservancy = "26";
	public final static String Waterlogging = "27";
	public final static String VideoSurveillance = "28";

}
