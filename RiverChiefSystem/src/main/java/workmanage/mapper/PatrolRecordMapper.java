package workmanage.mapper;

import java.util.List;
import java.util.Map;

import common.model.FdStatistical;
import common.model.PatrolRecord;

public interface PatrolRecordMapper {

	/**
	 * 关联查询event
	 * 
	 * @param map
	 * @return
	 */
	public List<PatrolRecord> queryPatrolRecord(Map<Object, Object> map);

	/**
	 * 不关联查询event
	 * 
	 * @param map
	 * @return
	 */
	public List<PatrolRecord> queryPatrolrecord(Map<Object, Object> map);

	public void deletePatrolRecordByPatrolId(int PatrolRecordId);

	public int countPatrolRecord(Map<Object, Object> map);

	public void updatePatrolRecord(Map<Object, Object> map);

	public void deletePatrolRecord(Map<Object, Object> map);

	public int insertPatrolRecord(PatrolRecord patrolRecord);

	public int countPatrolRecordByPatrolId(int patrolId);

	public List<PatrolRecord> staticPatrolRecord(Map<Object, Object> map);

	public List<Map<String, Object>> staticalPatrolRecordByUser(Map<Object, Object> map);

	public List<FdStatistical> queryRegion(Map<Object, Object> map);

	public List<String> staticPatrolRecordMonth(Map<Object, Object> map);
}
