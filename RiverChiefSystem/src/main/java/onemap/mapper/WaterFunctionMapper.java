package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterFunctionMapper {
	
	public List<Map<Object, Object>> findWaterFunctionAll(Map<Object, Object> map);
	public Map<Object, Object> findWaterFunctionById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterFunctionByPage(Map<Object, Object> parameter);
	public int insertWaterFunction(Map<Object, Object> map);
	public int updateWaterFunction(Map<Object, Object> map);
	public int deleteWaterFunctionById(int id);
	public int findMaxId();
	
}