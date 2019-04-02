package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterQualityMapper;
import onemap.service.WaterQualityService;

@Service
public class WaterQualityServiceImpl implements WaterQualityService{

	@Autowired
	private WaterQualityMapper waterQualityMapper;
	
	public Map<Object, Object> findWaterQualityAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterQualityMapper.findWaterQualityAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterQualityMapper.findWaterQualityAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		return map;
	}

	public Map<Object, Object> findWaterQualityById(int waterQualityId) {
		
		Map<Object, Object> result=waterQualityMapper.findWaterQualityById(waterQualityId);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("WaterQuality", result);
		
		return map;
	}
	
	public Map<Object, Object> insertWaterQuality(Map<Object, Object> parameter) {
		int maxid=waterQualityMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterQualityMapper.insertWaterQuality(parameter);		
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterQuality(Map<Object, Object> parameter) {
		int result=waterQualityMapper.updateWaterQuality(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public  Map<Object, Object> deleteWaterQualityById(int id) {
		int result=waterQualityMapper.deleteWaterQualityById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		return map;
	}

	public Map<Object, Object> findWaterQualityByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterQualityMapper.findWaterQualityByPage(parameter);
		Map<Object, Object> pageInformation=waterQualityMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

	public Map<Object, Object> getStatisticsInformation(Map<Object, Object> parameter){
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list1=waterQualityMapper.getStatisticsByRegionId();
		List<Map<Object, Object>> list2=waterQualityMapper.getStatisticsByType(parameter);
		map.put("items1", list1);
		map.put("items2", list2);
		
		return map;
	} 

}
