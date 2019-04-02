package onemap.service;

import java.util.Map;

public interface RaininfoService {
	
	public Map<Object, Object> findRaininfoAll(Map<Object, Object> parameter);
	public Map<Object, Object> findRaininfoById(int raininfoId);
	public Map<Object, Object> findRaininfoByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertRaininfo(Map<Object, Object> parameter);
	public Map<Object, Object> updateRaininfo(Map<Object, Object> parameter);
	public Map<Object, Object> deleteRaininfoById(int id);
	
}
