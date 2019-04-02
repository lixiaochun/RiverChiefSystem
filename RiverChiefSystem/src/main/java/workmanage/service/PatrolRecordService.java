package workmanage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PatrolRecordService {

	public Map<Object, Object> queryPatrolRecord(Map<Object, Object> map);

	public Map<Object, Object> deletePatrolRecordByPatrolId(int patrolRecordId);

	public Map<Object, Object> insertPatrolRecord(Map<Object, Object> map);

	public Map<Object, Object> updatePatrolRecord(Map<Object, Object> map);

	public String stringArrayToString(String str);

	public List<List<String>> stringToStringArray(String str);

	public Map<Object, Object> queryPointFromPatrolRecord(String patrolRecordId);

	public Map<Object, Object> statisticalMileageAndTime(Map<Object, Object> map);

	public Map<Object, Object> exportPatrolRecord(Map<Object, Object> map, HttpServletResponse response);

	public Map<Object, Object> deletePatrolRecord(Map<Object, Object> map);

	public Map<Object, Object> exportPatrolRecordByUser(Map<Object, Object> map, HttpServletRequest request, HttpServletResponse response);

	public Map<Object, Object> staticalPatrolRecordByUser(Map<Object, Object> map);
}
