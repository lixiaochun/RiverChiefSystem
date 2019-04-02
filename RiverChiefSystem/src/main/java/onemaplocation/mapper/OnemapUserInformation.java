package onemaplocation.mapper;

import java.util.List;
import java.util.Map;

public interface OnemapUserInformation {
	
	 public Map<Object, Object> selectUserInformation(int userId);
	 public List<Map<Object, Object>> selectUserRelationRiver(int userId);

}
