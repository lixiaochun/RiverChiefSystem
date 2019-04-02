package onemap.service;

import java.util.Map;

public interface WaterProjectService {
	
	public Map<Object, Object> findWaterProjectAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterProjectById(int id);
	public Map<Object, Object> findWaterProjectByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterProject(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterProject(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterProjectById(int id);
}
