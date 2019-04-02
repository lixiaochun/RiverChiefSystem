package common.util;

import java.util.List;

public class GPSDataFactory {

	private List<String> gpsDataLines;
	KalmanFilter filter = new KalmanFilter();

	public GPSDataFactory(String[] points) {
		Importer importer = new Importer();
		gpsDataLines = importer.readData(points);

		startFactory();
	}

	private void startFactory() {
		for (String string : gpsDataLines) {
			GPSSingleData gpsSingleData = proccessLine(string);

			filter.onLocationUpdate(gpsSingleData);
			// Simulate GPS intervals
			// pauseThread(SLEEP_TIME);
		}
	}

	private GPSSingleData proccessLine(String gpsLine) {
		String[] gpsParts = gpsLine.split(" ");
		return new GPSSingleData(Float.valueOf(gpsParts[1]), Double.valueOf(gpsParts[2]), Double.valueOf(gpsParts[3]), Long.valueOf(gpsParts[4]));
	}

	public List<List<Double>> getNewPoints() {
		return filter.getNewPoints();
	}
}
