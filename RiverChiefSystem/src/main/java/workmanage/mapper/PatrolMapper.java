package workmanage.mapper;

import java.util.List;
import java.util.Map;

import common.model.Patrol;

public interface PatrolMapper {

	public List<Patrol> queryPatrolByStatus(Map<Object, Object> map);

	public List<Patrol> queryPatrol(Map<Object, Object> map);

	public int insertPatrol(Patrol patrol);

	public void updatePatrol(Map<Object, Object> map);

	public void deletePatrol(int patrolId);

	public int countPatrol(Map<Object, Object> map);

	public void updatePatrolNum(int patrolId);
}
