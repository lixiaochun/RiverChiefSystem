package onemaplocation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import onemap.util.FormatPointText;
import onemap.util.Point;

/**
 * 用户轨迹对应的Geojson类
 * @author wangwch
 *
 */
public class UserPathGeojson {
	
	public String type;
	public List<Feature> features;

	public UserPathGeojson(Map<String, String> userPath) {
		type = "FeatureCollection"; 
		features = new ArrayList<>();
		for (Entry<String, String> entry : userPath.entrySet()) {
			String userId = entry.getKey();
			String path = entry.getValue();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			Feature feature = new Feature();
			feature.type = "Feature";
			feature.id =  userId;
			feature.properties.userId = userId;
			feature.geometry.type = "LineString";
			List<Point> points = FormatPointText.formatTextToPointsList(path);
			for (Point point : points) {
				double x = Double.valueOf(point.x);
				double y = Double.valueOf(point.y);
				feature.geometry.coordinates.add(new Double[] {x,y});
			}
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
			List<Double[]> coordinates;

			public Geometry() {
				coordinates = new ArrayList<>();
			}
		}
		
	}

}
