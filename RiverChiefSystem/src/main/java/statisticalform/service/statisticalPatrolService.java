package statisticalform.service;

import java.util.Map;

public interface statisticalPatrolService {

	public Map<Object, Object> statisticsRiver(Map<Object, Object> map);
	
	public Map<Object, Object> statisticsLogin(Map<Object, Object> map);
	
	public Map<Object, Object> statisticsNotLogin(Map<Object, Object> map);
	
	public Map<Object, Object> statisticsPatrol(Map<Object, Object> map);
	
	public Map<Object, Object> statisticsPatrolRecord(Map<Object, Object> map);
	
	public Map<Object, Object> statisticsPublicsigns(Map<Object, Object> map);
}
