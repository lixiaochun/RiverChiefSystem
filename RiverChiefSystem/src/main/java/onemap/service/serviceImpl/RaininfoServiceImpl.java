package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.RaininfoMapper;
import onemap.service.RaininfoService;

@Service
public class RaininfoServiceImpl implements RaininfoService{

	@Autowired
	private RaininfoMapper rainInfoMapper;
	
	public Map<Object, Object> findRaininfoAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			System.out.println(regionIdArray.toString());
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = rainInfoMapper.findRaininfoAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = rainInfoMapper.findRaininfoAll(parameter);

				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		return map;
	}

	public Map<Object, Object> findRaininfoById(int raininfoId) {
		Map<Object, Object> rainInfo=rainInfoMapper.findRaininfoById(raininfoId);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("RainInfo", rainInfo);
		return map;
	}

	public Map<Object, Object> insertRaininfo(Map<Object, Object> parameter) {
		int maxid=rainInfoMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=rainInfoMapper.insertRaininfo(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateRaininfo(Map<Object, Object> parameter) {
		int result=rainInfoMapper.updateRaininfo(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> deleteRaininfoById(int id) {
		int result=rainInfoMapper.deleteRaininfoById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findRaininfoByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = rainInfoMapper.findRaininfoByPage(parameter);
		Map<Object, Object> pageInformation=rainInfoMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
