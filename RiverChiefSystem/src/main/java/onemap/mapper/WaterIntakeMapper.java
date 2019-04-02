package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterIntakeMapper {

	public List<Map<Object, Object>> findWaterIntakeAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterIntakeById(int waterIntakeId);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterIntakeByPage(Map<Object, Object> parameter);
	public int insertWaterIntake(Map<Object, Object> parameter);
	public int updateWaterIntake(Map<Object, Object> parameter);
	public int deleteWaterIntakeById(int id);
	public int findMaxId();
	
}
