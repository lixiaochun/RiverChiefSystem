package basemanage.mapper;

import java.util.List;
import java.util.Map;

import basemanage.model.BaseGrid;

public interface BaseGridMapper {
	
	
	public List<BaseGrid> selectBaseGrid (Map<Object, Object> parameter);

}
