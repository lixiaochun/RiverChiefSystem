package onemap.util;

public class LocationUtils {

	private static final double EARTH_RADIUS = 6378137;// 赤道半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算两点间的距离
	 * @param lon1 1点经度
	 * @param lat1 1点纬度
	 * @param lon2 2点经度 
	 * @param lat2 2点纬度
	 * @return 单位米
	 */
	public static double getDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}
	
	/**
	 * 计算两点间的距离
	 * @param point1 点1
	 * @param point2 点2
	 * @return
	 */
	public static double getDistance(Point point1,Point point2) {
		double radLat1 = rad(point1.y);
		double radLat2 = rad(point2.y);
		double a = radLat1 - radLat2;
		double b = rad(point1.x) - rad(point2.x);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}
	
	/**
	 * 判断两点是否为同一点
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static boolean isEqual(Point point1,Point point2) {
		if(point1.x == point2.x && point1.y == point2.y) {
			return true;
		}		
		
		return false;
	}
	

	public static void main(String[] args) {
		double length = LocationUtils.getDistance(120.21592590991689,
				30.210793016606, 120.21670777384473, 30.211168525868086);
		System.out.println(length);
		double length2 = LocationUtils.getDistance(new Point(120.21592590991689,
				30.210793016606), new Point(120.21670777384473, 30.211168525868086));
		System.out.println(length2);
	}
}
