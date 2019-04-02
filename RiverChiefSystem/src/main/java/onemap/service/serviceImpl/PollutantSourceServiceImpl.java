package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.PollutantSourceMapper;
import onemap.service.PollutantSourceService;

@Service
public class PollutantSourceServiceImpl implements PollutantSourceService{

	@Autowired
	private  PollutantSourceMapper pollutantSourceMapper;
	
	public Map<Object, Object> findPollutantsourceAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = pollutantSourceMapper.findPollutantSourceAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = pollutantSourceMapper.findPollutantSourceAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		
		return map;
	}

	public Map<Object, Object> findPollutantsourceById(int id) {
		// TODO Auto-generated method stub
		Map<Object, Object> pollutantSource=pollutantSourceMapper.findPollutantSourceById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("PollutantSource", pollutantSource);
		return map;
	}
	
	public Map<Object, Object> insertPollutantsource(Map<Object, Object> parameter) {
		int maxid=pollutantSourceMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=pollutantSourceMapper.insertPollutantSource(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updatePollutantsource(Map<Object, Object> parameter) {
		int result=pollutantSourceMapper.updatePollutantSource(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> deletePollutantsourceById(int id) {
		int result=pollutantSourceMapper.deletePollutantSourceById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findPollutantsourceByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = pollutantSourceMapper.findPollutantSourceByPage(parameter);
		Map<Object, Object> pageInformation=pollutantSourceMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
