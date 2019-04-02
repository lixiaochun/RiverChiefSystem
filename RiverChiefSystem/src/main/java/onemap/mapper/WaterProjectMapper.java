package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterProjectMapper {
	
	public List<Map<Object, Object>> findWaterProjectAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterProjectById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterProjectByPage(Map<Object, Object> parameter);
	public int insertWaterProject(Map<Object, Object> parameter);
	public int updateWaterProject(Map<Object, Object> parameter);
	public int deleteWaterProjectById(int id);
	public int findMaxId();
	
}