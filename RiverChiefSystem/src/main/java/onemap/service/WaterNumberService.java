package onemap.service;

import java.util.Map;

public interface WaterNumberService {
	
	public Map<Object, Object> findWaterNumberAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterNumberById(int waternumberid);
	public Map<Object, Object> findWaterNumberByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterNumber(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterNumber(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterNumberById(int id);
}
