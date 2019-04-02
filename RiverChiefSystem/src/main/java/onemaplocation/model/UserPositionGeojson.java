package onemaplocation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 用户位置对应的Geojson类
 * @author wangwch
 *
 */
public class UserPositionGeojson {

	public String type;
	public List<Feature> features;

	public UserPositionGeojson(Map<String, String> userPosition) {
		type = "FeatureCollection"; 
		features = new ArrayList<>();
		for (Entry<String, String> entry : userPosition.entrySet()) {
			String userId = entry.getKey();
			String point = entry.getValue();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			Feature feature = new Feature();
			feature.type = "Feature";
			feature.id =  userId;
			feature.properties.userId = userId;
			feature.geometry.type = "Point";
			double x = Double.valueOf(point.split(",")[0]);
			double y = Double.valueOf(point.split(",")[1]);
			feature.geometry.coordinates.add(x);
			feature.geometry.coordinates.add(y);
			features.add(feature);
		}	
		
	}

	public class Feature {
		public String type;
		public String id;
		public Properties properties;
		public Geometry geometry;

		public Feature() {
			properties = new Properties();
			geometry = new Geometry();
		}
		
		public class Properties {
			public String userId; 	
		}

		public class Geometry {
			String type;
			//数据类型:Point
			List<Double> coordinates;

			public Geometry() {
				coordinates = new ArrayList<>();
			}
		}
		
	}

}
