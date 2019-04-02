package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface OutfallMapper {
	
	public List<Map<Object, Object>> findOutfallAll(Map<Object, Object> parameter);
	public Map<Object, Object> findOutfallById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findOutfallByPage(Map<Object, Object> parameter);
	public int insertOutfall(Map<Object, Object> parameter);
	public int updateOutfall(Map<Object, Object> parameter);
	public int deleteOutfallById(int id);
	public int findMaxId();
	
}
