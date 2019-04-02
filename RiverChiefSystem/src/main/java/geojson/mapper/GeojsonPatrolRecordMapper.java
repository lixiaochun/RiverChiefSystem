package geojson.mapper;

import java.util.List;
import java.util.Map;

public interface GeojsonPatrolRecordMapper {
	
	public List<Map<Object, Object>> selectPatrolRecord(Map<Object, Object> parameter);
	

}
