package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface OnemapEventMapper {
	
	public List<Map<Object, Object>> findEventAll(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findEventPoint(Map<Object, Object> parameter);
	public Map<Object, Object> findEventById(int id);
	public List<Map<Object, Object>> getEventStatisticsInformation(Map<Object, Object> parameter);
	
	
	public Map<Object, Object> getPatrolPoint(int patrol_record_id);
	public List<Map<Object, Object>> getPatrolPoint();
	
}