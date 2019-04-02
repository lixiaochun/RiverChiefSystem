package onemap.service;

import java.util.Map;

public interface WaterIntakeService {

	public Map<Object, Object> findWaterIntakeAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterIntakeById(int id);
	public Map<Object, Object> findWaterIntakeByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterIntake(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterIntake(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterIntakeById(int id);
}
