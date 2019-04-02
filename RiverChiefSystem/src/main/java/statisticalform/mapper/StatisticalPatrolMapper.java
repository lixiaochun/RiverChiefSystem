package statisticalform.mapper;

import java.util.List;
import java.util.Map;

import common.model.Statistics;

public interface StatisticalPatrolMapper {

	public int statisticsRiver(Map<Object, Object> map);
	
	public int statisticsRoleType(Map<Object, Object> map);
	
	public List<Statistics>  statisticsLogin(Map<Object, Object> map);
	
	public List<Statistics>  statisticsNotLogin(Map<Object, Object> map);
	
	public List<Statistics>  statisticsPatrolCount(Map<Object, Object> map);
	
	public List<Statistics>  statisticsPatrolRecord(Map<Object, Object> map);
	
	public List<Map<String, Object>>  statisticsPatrolRecordByRegionId(Map<Object, Object> map);
	
	public List<Map<String, Object>>  statisticsPatrolRecordByRiverId(Map<Object, Object> map);
	
public List<Map<String, Object>>  statisticsPublicsignsByRegionId(Map<Object, Object> map);
	
	public List<Map<String, Object>>  statisticsPublicsignsByRiverId(Map<Object, Object> map);
}
