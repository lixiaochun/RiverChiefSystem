package onemap.service;

import java.util.Map;

public interface CollectionRiverService {
	
	public Map<Object, Object> findCollectionRiverByUserId(int userId);
	public Map<Object, Object> insertCollectionRiver(Map<Object, Object> parameter);
	public Map<Object, Object> deleteCollectionRiverById(Map<Object, Object> parameter);
}
