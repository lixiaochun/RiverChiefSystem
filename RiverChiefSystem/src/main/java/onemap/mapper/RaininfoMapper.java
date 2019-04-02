package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface RaininfoMapper {
	
	public List<Map<Object, Object>> findRaininfoAll(Map<Object, Object> parameter);
	public Map<Object, Object> findRaininfoById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findRaininfoByPage(Map<Object, Object> parameter);
	public int insertRaininfo(Map<Object, Object> parameter);
	public int updateRaininfo(Map<Object, Object> parameter);
	public int deleteRaininfoById(int id);
	public int findMaxId();
	
}
