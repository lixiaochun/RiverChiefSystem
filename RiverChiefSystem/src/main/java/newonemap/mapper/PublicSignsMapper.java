package newonemap.mapper;


import java.util.List;
import java.util.Map;

import newonemap.model.BasicInformation;
import newonemap.model.Publicsigns;

public interface PublicSignsMapper {

	public List<BasicInformation> findPublicsignsBasicInformation(Map<Object, Object> parameter);
	public Publicsigns findPublicsignsById(Map<Object, Object> parameter);
	
}
