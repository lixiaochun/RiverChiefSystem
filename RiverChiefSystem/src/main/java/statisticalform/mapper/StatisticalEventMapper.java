package statisticalform.mapper;

import java.util.List;
import java.util.Map;

import common.model.Statistics;

public interface StatisticalEventMapper {

	public List<Map<String, Object>> statisticsProblemType(Map<Object, Object> map);
	
	public List<Map<String, Object>> statisticsEventCount(Map<Object, Object> map);
	
	public List<Map<String, Object>> statisticalReportType(Map<Object, Object> map);
	
	public List<Map<String, Object>> everyEventCount(Map<Object, Object> map);
	
	public List<Map<String, Object>> beforeEventCount(Map<Object, Object> map);
	
	public List<Map<String, Object>> publicComplaints(Map<Object, Object> map);
	
	public List<Map<String, Object>> statisticsProblemTypes(Map<Object, Object> map);
	
	public List<Map<String, Object>> statisticsEventType(Map<Object, Object> map);
}
