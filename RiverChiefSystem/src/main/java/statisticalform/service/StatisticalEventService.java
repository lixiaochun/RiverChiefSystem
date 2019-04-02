package statisticalform.service;

import java.util.Map;

public interface StatisticalEventService {

	public Map<Object, Object> statisticsEvent(Map<Object, Object> map);

	public Map<Object, Object> statisticsProblemType(Map<Object, Object> map);

	public Map<Object, Object> statisticsEventCount(Map<Object, Object> map);

	public Map<Object, Object> everyEventCount(Map<Object, Object> map);

	public Map<Object, Object> statisticalReportType(Map<Object, Object> map);

	public Map<Object, Object> statisticalRegion(Map<Object, Object> map);

	public Map<Object, Object> publicComplaints(Map<Object, Object> map);

	public Map<Object, Object> statisticsProblemTypes(Map<Object, Object> map);

	public Map<Object, Object> statisticsEventType(Map<Object, Object> map);

	public Map<Object, Object> statisticsHome(Map<Object, Object> map);
}
