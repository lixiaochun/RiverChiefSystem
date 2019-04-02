package onemap.service;

import java.util.Map;

public interface WaterFunctionService {
	
	public Map<Object, Object> findWaterFunctionAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterFunctionById(int id);
	public Map<Object, Object> findWaterFunctionByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterFunction(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterFunction(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterFunctionById(int id);
	
}