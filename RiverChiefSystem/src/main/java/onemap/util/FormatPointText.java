package onemap.util;

import java.util.ArrayList;
import java.util.List;

public class FormatPointText {
	
	
	/**
	 * 格式化轨迹，轨迹文本转换成postgis的矢量格式
	 * @param pointText:轨迹文本
	 * @return
	 */
	public static String formatTextToPostgis(String pointText) {
		pointText = pointText.replaceAll(",", " ");
		pointText = pointText.replaceAll(";", ",");
		pointText = pointText.replaceAll("\\[", "");
		pointText = pointText.replaceAll("\\]", "");
		pointText = pointText.substring(0, pointText.length() - 1);
		pointText = "'LINESTRING(" + pointText + ")'";
		System.out.println(pointText);

		return pointText;
	}
	
	/**
	 * 格式化轨迹，轨迹文本转换成openlayers的矢量格式
	 * @param pointText:轨迹文本
	 * @return
	 */
	public static String formatTextToOpenlayers(String pointText) {
		pointText = pointText.replaceAll(";", ",");
		pointText = pointText.substring(0, pointText.length() - 1);
		pointText = "[" + pointText + "]";
		System.out.println(pointText);

		return pointText;
	}

	/**
	 * 格式化轨迹，轨迹文本转换成轨迹点列表
	 * @param pointText 轨迹文本
	 * @return 
	 */
	public static List<Point> formatTextToPointsList(String pointText){
		String[] points = pointText.split(";");
		int pointsNumber = points.length;
		String[] pointsXYText = new String[2];
		List<Point> pointsList = new ArrayList<>();
		for (int i = 0; i < pointsNumber; i++) {
			pointsXYText[0] = points[i].substring(1, points[i].indexOf(","));
			pointsXYText[1] = points[i].substring(points[i].indexOf(",") + 1, points[i].length() - 1);
			double x = Double.valueOf(pointsXYText[0]);
			double y = Double.valueOf(pointsXYText[1]);
			Point point = new Point(x, y);
			pointsList.add(point);
		}
		
		return pointsList;
	}
	
	/**
	 * 格式化轨迹，轨迹点列表转换成轨迹文本
	 * @param pointText
	 * @return
	 */
	public static String formatPointsListToText(List<Point> pointsList){
		StringBuffer stringBuffer = new StringBuffer();
		int pointsNumber = pointsList.size();
		for(int i = 0; i < pointsNumber; i++) {
			Point point = pointsList.get(i);
			stringBuffer.append("["+point.x+","+point.y+"];");
		} 	
		return stringBuffer.toString();
	}
	
	/**
	 * 去除NaN
	 * @param pointText
	 * @return
	 */
	public static String removeNaNPoint(String pointText) {
		String[] points = pointText.split(";");
		int pointsNumber = points.length;
		String[] pointsXYText = new String[2];
		List<Point> pointsList = new ArrayList<>();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < pointsNumber; i++) {
			pointsXYText[0] = points[i].substring(1, points[i].indexOf(","));
			pointsXYText[1] = points[i].substring(points[i].indexOf(",") + 1, points[i].length() - 1);
			if(pointsXYText[0].equals("NaN")||pointsXYText[1].equals("NaN")) {
				continue;	
			}
			double x = Double.valueOf(pointsXYText[0]);
			double y = Double.valueOf(pointsXYText[1]);
			Point point = new Point(x, y);
			pointsList.add(point);
			stringBuffer.append("["+x+","+y+"];");
		}

		return stringBuffer.toString();
	}
	
	/**
	 * 轨迹过滤
	 * @return
	 */
	public static String filterPatrolRecord(String pointText){
		List<Point> pointsList = FormatPointText.formatTextToPointsList(pointText);
		List<Point>  filterPointsList= new ArrayList<>();
		int threshold = 50; //距离阈值
		
		int range = 3;//相邻点范围
		int pointsNumber = pointsList.size();
		for (int i = 0; i < pointsNumber; i++) {
			Point point1 = pointsList.get(i);
			for(int j=i-range>0?i-range:0;j<(i+range<pointsNumber?i+range:pointsNumber);j++) {
				Point point2 = pointsList.get(j);
				if(!LocationUtils.isEqual(point1, point2) && LocationUtils.getDistance(point1, pointsList.get(j)) <= threshold) {			
					filterPointsList.add(point2);
					break;
				}
			}
			
		}
		pointText = FormatPointText.formatPointsListToText(filterPointsList);

		return pointText;
	}

	
	
}
