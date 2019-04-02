package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface MileageMapper {
	
	public Map<Object, Object> findCenterPointTownByRegionId(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findCenterPointTownAll();
	public int updateCenterPointTown(Map<Object, Object> parameter);
	
	
	
}
