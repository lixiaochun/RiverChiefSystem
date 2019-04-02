package common.util;

import java.util.ArrayList;
import java.util.List;

public class Exporter {
	public List<List<Double>> writeData(List<List<Double>> newPoints, Double lon, Double lat) {
		List<Double> point = new ArrayList<Double>();
		point.add(lon);
		point.add(lat);
		newPoints.add(point);
		return newPoints;
	}
}
