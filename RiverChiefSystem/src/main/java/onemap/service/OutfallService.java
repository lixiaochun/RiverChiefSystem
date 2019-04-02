package onemap.service;

import java.util.Map;

public interface OutfallService {
	
	public Map<Object, Object> findOutfallAll(Map<Object, Object> parameter);
	public Map<Object, Object> findOutfallById(int id);
	public Map<Object, Object> findOutfallByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertOutfall(Map<Object, Object> parameter);
	public Map<Object, Object> updateOutfall(Map<Object, Object> parameter);
	public Map<Object, Object> deleteOutfallById(int id);
}
