package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import onemap.mapper.WaterIntakeMapper;
import onemap.service.WaterIntakeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterIntakeServiceImpl implements WaterIntakeService{

	@Autowired
	private WaterIntakeMapper waterIntakeMapper;
	
	public Map<Object, Object> findWaterIntakeAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterIntakeMapper.findWaterIntakeAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterIntakeMapper.findWaterIntakeAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);		
		return map;
	}

	public Map<Object, Object> findWaterIntakeById(int id) {
		// TODO Auto-generated method stub
		Map<Object, Object> result=waterIntakeMapper.findWaterIntakeById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("WaterIntake", result);
		return map;
	}
	
	public Map<Object, Object> insertWaterIntake(Map<Object, Object> parameter) {
		int maxid=waterIntakeMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterIntakeMapper.insertWaterIntake(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterIntake(Map<Object, Object> parameter) {
		int result=waterIntakeMapper.updateWaterIntake(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> deleteWaterIntakeById(int id) {
		int result=waterIntakeMapper.deleteWaterIntakeById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findWaterIntakeByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterIntakeMapper.findWaterIntakeByPage(parameter);
		Map<Object, Object> pageInformation=waterIntakeMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
