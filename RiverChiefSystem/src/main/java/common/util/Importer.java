package common.util;

import java.util.ArrayList;
import java.util.List;

public class Importer {
	List<String> gpsDataLines = new ArrayList<String>();

	public List<String> readData(String[] points) {
		int count = 1;
		for (String point : points) {
			point = point.trim();
			point = count + " " + point;
			gpsDataLines.add(point);
			count++;
		}
		return gpsDataLines;
	}
}
