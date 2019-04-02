package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface CollectionRiverMapper {
	
	public List<Map<Object, Object>> findCollectionRiverByUserId(int userId);
	public int insertCollectionRiver(Map<Object, Object> parameter);
	public int deleteCollectionRiverById(Map<Object, Object> parameter);
	
}
