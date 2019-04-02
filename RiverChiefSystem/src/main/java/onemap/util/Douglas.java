package onemap.util;

import java.util.List;

public class Douglas {

	/**
	 * 使用三角形面积（使用海伦公式求得）相等方法计算点pX到点pA和pB所确定的直线的距离
	 * 
	 * @param start:起始经纬度
	 * @param end:结束经纬度
	 * @param center:前两个点之间的中心点
	 * @return 中心点到 start和end所在直线的距离
	 */
	public static double distToSegment(Point start, Point end, Point center) {
		double a = LocationUtils.getDistance(start, end);
		double b = LocationUtils.getDistance(start, center);
		double c = LocationUtils.getDistance(end, center);
		double p = (a + b + c) / 2.0;
		double s = Math.sqrt(Math.abs(p * (p - a) * (p - b) * (p - c)));
		double d = s * 2.0 / a;

		return d;
	}

	/**
	 * 根据最大距离限制，采用DP方法递归的对原始轨迹进行采样，得到压缩后的轨迹
	 * @param originalPoints:原始经纬度坐标点数组
	 * @param endPoints:保持过滤后的点坐标数组
	 * @param start:起始下标
	 * @param end:结束下标
	 * @param dMax:预先指定好的最大距离误差
	 * 
	 * @return
	 * 
	 * 
	 */
	public static List<Point> compressLine(List<Point> originalPoints, List<Point> endPoints, int start, int end,
			double dMax) {
		if (start < end) {
			// 递归进行调教筛选
			double maxDist = 0;
			int currentIndex = 0;
			for (int i = start + 1; i < end; i++) {
				double currentDist = distToSegment(originalPoints.get(start), originalPoints.get(end), originalPoints.get(i));
				if (currentDist > maxDist) {
					maxDist = currentDist;
					currentIndex = i;
				}
			}
			// 若当前最大距离大于最大距离误差
			if (maxDist >= dMax) {
				// 将当前点加入到过滤数组中
				endPoints.add(originalPoints.get(currentIndex));
				// 将原来的线段以当前点为中心拆成两段，分别进行递归处理
				compressLine(originalPoints, endPoints, start, currentIndex, dMax);
				compressLine(originalPoints, endPoints, currentIndex, end, dMax);
			}
		}
		return endPoints;
	}
	
	
	public static void main(String[] args) {
		
		
		
	}

}
