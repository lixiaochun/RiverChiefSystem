package geojson.model;

public class Feature {
	public String type;
	public int id;
	public Properties properties;
	public Geometry geometry;
	
	public Feature(){
		properties = new Properties();
		geometry = new Geometry();
	}

	public class Properties {
		public int gid;
		public String name;
	}
	
}
