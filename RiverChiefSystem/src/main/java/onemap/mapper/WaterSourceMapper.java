package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterSourceMapper {
	
	public List<Map<Object, Object>> findWaterSourceAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterSourceById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterSourceByPage(Map<Object, Object> parameter);
	public int insertWaterSource(Map<Object, Object> map);
	public int updateWaterSource(Map<Object, Object> map);
	public int deleteWaterSourceById(int id);
	public int findMaxId();
}
