package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.MileageMapper;
import onemap.service.MileageService;
import onemap.util.MileageUtil;
import onemap.util.Point;

@Service
public class MileageServiceImpl implements MileageService {

	@Autowired
	private MileageMapper mileageMapper;

	/**
	 * @param regionId 行政区  
	 * @param pointText 轨迹文本
	 * @return effectiveText:有效轨迹文本, mileage:有效里程（米）, effective:巡河是否有效(1:有效  0:无效)
	 */
	public Map<Object, Object> CalculationMileage(String regionId, String pointText) {
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("regionId", regionId);
		Map<Object, Object> centerPoint = mileageMapper.findCenterPointTownByRegionId(parameter);
		if(centerPoint == null) {
			Map<Object, Object> result =  new HashMap<Object, Object>();
			result.put("effectiveText", pointText);
			result.put("mileage", MileageUtil.CalculationMileage(formatTextToPointsList(pointText)));
			result.put("effective", 1);

			return result;
		}
		double longitute = (double) centerPoint.get("longitute");
		double latitude = (double) centerPoint.get("latitude");
		double radius = (double) centerPoint.get("radius");
		//有效轨迹
		List<Point> pointsList = MileageUtil.EffectivePatrolRecord(pointText, longitute, latitude, radius);
		StringBuffer effectiveText = new StringBuffer();
		for(int i=0; i<pointsList.size(); i++) {
			Point point = pointsList.get(i);
			effectiveText.append("["+point.x+","+point.y+"];");
		}
		//有效里程
		double mileage = MileageUtil.CalculationMileage(pointsList);
 		//巡河是否有效
		int effective = 1;// 1:有效  0:无效
		if(pointsList.size() == 0) {
			effective = 0;
		}
		System.out.println("effective:" + effective);
		Map<Object, Object> result =  new HashMap<Object, Object>();
		result.put("effectiveText", effectiveText);
		result.put("mileage", mileage);
		result.put("effective", effective);

		return result;

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
}
