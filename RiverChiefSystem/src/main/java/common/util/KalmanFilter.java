package common.util;

import java.util.ArrayList;
import java.util.List;

public class KalmanFilter {

	private long timeStamp; // millis
	private double latitude; // degree
	private double longitude; // degree
	private float variance; // P matrix. Initial estimate of error
	private List<List<Double>> newPoints = new ArrayList<List<Double>>();
	private Exporter exporter;

	public KalmanFilter() {
		exporter = new Exporter();
		variance = -1;
	}

	public void onLocationUpdate(GPSSingleData singleData) {
		// if gps receiver is able to return 'accuracy' of position, change last
		// variable
		process(singleData.getSpeed(), singleData.getLatitude(), singleData.getLongitude(), singleData.getTimestamp(), Constants.MIN_ACCURACY);
	}

	// Init method (use this after constructor, and before process)
	// if you are using last known data from gps)
	public void setState(double latitude, double longitude, long timeStamp, float accuracy) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeStamp = timeStamp;
		this.variance = accuracy * accuracy;
	}

	/**
	 * Kalman filter processing for latitude and longitude
	 *
	 * newLatitude - new measurement of latitude newLongitude - new measurement of
	 * longitude accuracy - measurement of 1 standard deviation error in meters
	 * newTimeStamp - time of measurement in millis
	 */
	public void process(float newSpeed, double newLatitude, double newLongitude, long newTimeStamp, float newAccuracy) {
		// Uncomment this, if you are receiving accuracy from your gps
		// if (newAccuracy < Constants.MIN_ACCURACY) {
		// newAccuracy = Constants.MIN_ACCURACY;
		// }
		if (variance < 0) {
			// if variance < 0, object is unitialised, so initialise with current values
			setState(newLatitude, newLongitude, newTimeStamp, newAccuracy);
		} else {
			// else apply Kalman filter
			long duration = newTimeStamp - this.timeStamp;
			if (duration > 0) {
				// time has moved on, so the uncertainty in the current position increases
				variance += duration * newSpeed * newSpeed / 1000;
				timeStamp = newTimeStamp;
			}

			// Kalman gain matrix 'k' = Covariance * Inverse(Covariance +
			// MeasurementVariance)
			// because 'k' is dimensionless,
			// it doesn't matter that variance has different units to latitude and longitude
			float k = variance / (variance + newAccuracy * newAccuracy);
			// apply 'k'
			latitude += k * (newLatitude - latitude);
			longitude += k * (newLongitude - longitude);
			// new Covariance matrix is (IdentityMatrix - k) * Covariance
			variance = (1 - k) * variance;

			// Export new point
			exportNewPoint(newPoints, newSpeed, longitude, latitude, duration);
		}
	}

	private void exportNewPoint(List<List<Double>> newPoints, float speed, double longitude, double latitude, long timestamp) {
		GPSSingleData newGPSdata = new GPSSingleData(speed, longitude, latitude, timestamp);
		newPoints = exporter.writeData(newPoints, newGPSdata.getLongitude(), newGPSdata.getLatitude());
	}

	public List<List<Double>> getNewPoints() {
		return newPoints;
	}
}