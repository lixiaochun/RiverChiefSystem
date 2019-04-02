package geojson.model;

import java.util.ArrayList;
import java.util.List;

public class Geometry {

	public String type;
	public List<Double[]> coordinates;
	
	public Geometry() {
		coordinates=new ArrayList<>();
		
	}

}
