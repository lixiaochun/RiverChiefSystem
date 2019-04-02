package geojson.util;

import java.util.ArrayList;
import java.util.List;

public class PatrolRecordPoint {
	
	
	public static List<Double[]> getPatrolRecordPointList(String pointText) {
		List<Double[]> list = new ArrayList<>();
		pointText = pointText.replaceAll("\\[", "");
		pointText = pointText.replaceAll("\\]", "");
		String[] points = pointText.split(";");
		for(int i=0;i<points.length;i++) {
			Double[] d = new Double[2];
			String pointItem = points[i];
			d[0] = Double.valueOf(pointItem.split(",")[0]);
			d[1] = Double.valueOf(pointItem.split(",")[1]);
			list.add(d);
		}
	
		return list;
	}

}
