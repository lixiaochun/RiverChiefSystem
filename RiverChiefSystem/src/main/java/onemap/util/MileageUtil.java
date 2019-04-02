package onemap.util;

import java.util.ArrayList;
import java.util.List;
import onemap.util.LocationUtils;

public class MileageUtil {

	/**
	 * 获取有效的巡河轨迹
	 * 
	 * @param pointText
	 *            巡河轨迹点文本
	 * @param lon
	 *            有效范围中心点
	 * @param lat
	 *            有效范围中心点
	 * @param radius
	 *            有效范围半径
	 * @return 轨迹点列表
	 */
	public static List<Point> EffectivePatrolRecord(String pointText, double lon, double lat, double radius) {
		Point centerPoint = new Point(lon, lat);
		String[] points = pointText.split(";");
		int pointsNumber = points.length;
		String[][] pointsXYText = new String[pointsNumber][2];
		List<Point> pointsList = new ArrayList<>();
		for (int i = 0; i < pointsNumber; i++) {
			pointsXYText[i][0] = points[i].substring(1, points[i].indexOf(","));
			pointsXYText[i][1] = points[i].substring(points[i].indexOf(",") + 1, points[i].length() - 1);
			double x = Double.valueOf(pointsXYText[i][0]);
			double y = Double.valueOf(pointsXYText[i][1]);
			Point point = new Point(x, y);
			// 判断轨迹点是否在有效范围内
			double distance = LocationUtils.getDistance(point, centerPoint);
			if (distance <= radius) {
				pointsList.add(point);
				System.out.println("point:" + point.x + " " + point.y);
			}
		}

		return pointsList;
	}

	/**
	 * 计算有效巡河里程
	 * 
	 * @param pointText
	 *            巡河轨迹点
	 * @param lon
	 *            lat 有效范围中心点
	 * @param radius
	 *            有效范围半径
	 * @return 里程数 单位（米）
	 */
	public static double CalculationMileage(String pointText, double lon, double lat, double radius) {
		List<Point> pointsList = EffectivePatrolRecord(pointText, lon, lat, radius);
		double mileage = 0;
		for (int i = 0; i < pointsList.size() - 1; i++) {
			mileage += LocationUtils.getDistance(pointsList.get(i).x, pointsList.get(i).y, pointsList.get(i + 1).x,
					pointsList.get(i + 1).y);
			// System.out.println("Distance:"+LocationUtils.getDistance(pointsList.get(i).x,
			// pointsList.get(i).y, pointsList.get(i + 1).x,pointsList.get(i + 1).y));
		}
		System.out.println("mileage:" + mileage);
		return mileage;
	}

	/**
	 * 计算巡河里程
	 * 
	 * @param pointsList
	 *            轨迹点列表
	 * @return 里程数 单位（米）
	 */
	public static double CalculationMileage(List<Point> pointsList) {
		double mileage = 0;
		for (int i = 0; i < pointsList.size() - 1; i++) {
			mileage += LocationUtils.getDistance(pointsList.get(i).x, pointsList.get(i).y, pointsList.get(i + 1).x,
					pointsList.get(i + 1).y);
			// System.out.println("Distance:"+LocationUtils.getDistance(pointsList.get(i).x,
			// pointsList.get(i).y, pointsList.get(i + 1).x,pointsList.get(i + 1).y));
		}
		System.out.println("mileage:" + mileage);
		return mileage;
	}

	
}
