package basemanage.mapper;

import java.util.List;
import java.util.Map;

import basemanage.model.BaseReach;
import basemanage.model.BaseReachInformation;

public interface BaseReachMapper {
	
	public List<BaseReach> selectBaseReach(Map<Object, Object> parameter);
	public BaseReachInformation selectBaseReachInformationById(Map<Object, Object> parameter);


}
