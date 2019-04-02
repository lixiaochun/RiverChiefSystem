package workmanage.service;

import java.util.Map;

public interface PatrolService {

	public Map<Object, Object> queryPatrol(Map<Object, Object> map);

	public Map<Object, Object> insertPatrol(Map<Object, Object> map);

	public Map<Object, Object> updatePatrol(Map<Object, Object> map);

	public Map<Object, Object> deletePatrol(int patrolId);

	public Map<Object, Object> queryPointFromPatrol(int patrolId);

	public Map<Object, Object> queryEventByPatrol(Map<Object, Object> map);

	public Map<Object, Object> countPatrol(Map<Object, Object> map);
}
