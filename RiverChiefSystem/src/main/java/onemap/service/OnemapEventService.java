package onemap.service;

import java.util.Map;

public interface OnemapEventService {

	public Map<Object, Object> findEventAll(Map<Object, Object> parameter);
	public Map<Object, Object> findEventPoint(Map<Object, Object> parameter);
	public Map<Object, Object> findEventById(int id);
	public Map<Object, Object> getEventStatisticsInformation(Map<Object, Object> parameter);
	
	
	
}
