package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterLineMapper {
	
	public List<Map<Object, Object>> findWaterLineAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterLineById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterLineByPage(Map<Object, Object> parameter);
	public int insertWaterLine(Map<Object, Object> parameter);
	public int updateWaterLine(Map<Object, Object> parameter);
	public int deleteWaterLineById(int id);
	public int findMaxId();
	
}
