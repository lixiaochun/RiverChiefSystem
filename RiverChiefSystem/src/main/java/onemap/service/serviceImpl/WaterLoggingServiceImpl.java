package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterLoggingMapper;
import onemap.service.WaterLoggingService;

@Service
public class WaterLoggingServiceImpl implements WaterLoggingService{

	@Autowired
	private WaterLoggingMapper waterLoggingMapper;
	
	public Map<Object, Object> findWaterloggingAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
	
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterLoggingMapper.findWaterLoggingAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterLoggingMapper.findWaterLoggingAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}
		map.put("items", result);
		
		return map;
	}

	public Map<Object, Object> findWaterloggingById(int id) {
		Map<Object, Object> result=waterLoggingMapper.findWaterLoggingById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("WaterLogging", result);
		return map;
	}
	
	public Map<Object, Object> insertWaterlogging(Map<Object, Object> parameter) {
		int maxid=waterLoggingMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterLoggingMapper.insertWaterLogging(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterlogging(Map<Object, Object> parameter) {
		int result=waterLoggingMapper.updateWaterLogging(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> deleteWaterloggingById(int id) {
		int result=waterLoggingMapper.deleteWaterLoggingById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findWaterloggingByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterLoggingMapper.findWaterLoggingByPage(parameter);
		Map<Object, Object> pageInformation=waterLoggingMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
