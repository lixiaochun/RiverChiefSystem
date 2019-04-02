package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.InlandWaterQualityMapper;
import onemap.service.InlandWaterQualityService;

@Service
public class InlandWaterQualityServiceImpl implements InlandWaterQualityService{
	
	@Autowired
	private InlandWaterQualityMapper inlandWaterQualityMapper;
	
	public Map<Object, Object> findInlandWaterQualityAll(Map<Object, Object> parameter) {
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = inlandWaterQualityMapper.findInlandWaterQualityAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = inlandWaterQualityMapper.findInlandWaterQualityAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);

		return map;
	}
	
	public Map<Object, Object> findInlandWaterQualityById(int inlandWaterQualityId) {
		Map<Object, Object> result=inlandWaterQualityMapper.findInlandWaterQualityById(inlandWaterQualityId);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("InlandWaterQuality", result);
		
		return map;
	}
	
	public Map<Object, Object> insertInlandWaterQuality(Map<Object, Object> parameter) {
		Integer maxid=inlandWaterQualityMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=inlandWaterQualityMapper.insertInlandWaterQuality(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> updateInlandWaterQuality(Map<Object, Object> parameter) {
		int result=inlandWaterQualityMapper.updateInlandWaterQuality(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> deleteInlandWaterQualityById(int id) {
		int result=inlandWaterQualityMapper.deleteInlandWaterQualityById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findInlandWaterQualityByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = inlandWaterQualityMapper.findInlandWaterQualityByPage(parameter);
		Map<Object, Object> pageInformation=inlandWaterQualityMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
	
		return map;
	}

}
