package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterNumberMapper {
	
	public List<Map<Object, Object>> findWaterNumberAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterNumberById(int waterNumberId);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterNumberByPage(Map<Object, Object> parameter);
	public int insertWaterNumber(Map<Object, Object> parameter);
	public int updateWaterNumber(Map<Object, Object> parameter);
	public int deleteWaterNumberById(int id);
	public int findMaxId();
	
}
