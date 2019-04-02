package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.VideoSurveillanceMapper;
import onemap.service.VideoSurveillanceService;

@Service("VideoSurveillanceServiceImpl")
public class VideoSurveillanceServiceImpl implements VideoSurveillanceService{

	@Autowired
	private VideoSurveillanceMapper videoSurveillanceMapper;
	
	
	public Map<Object, Object> findVideoSurveillanceAll(Map<Object, Object> parameter) {
		String code = (String) parameter.get("code");
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			System.out.println(regionIdArray.toString());
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = videoSurveillanceMapper.findVideoSurveillanceAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = videoSurveillanceMapper.findVideoSurveillanceAll(parameter);

				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		return map;
	}

	
	public Map<Object, Object> findVideoSurveillanceById(int id) {
		Map<Object, Object> rainInfo=videoSurveillanceMapper.findVideoSurveillanceById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("VideoSurveillance", rainInfo);
		return map;
	}
	
	public Map<Object, Object> insertVideoSurveillance(Map<Object, Object> parameter) {
		int maxid=videoSurveillanceMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=videoSurveillanceMapper.insertVideoSurveillance(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateVideoSurveillance(Map<Object, Object> parameter) {
		int result=videoSurveillanceMapper.updateVideoSurveillance(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> deleteVideoSurveillanceById(int id) {
		int result=videoSurveillanceMapper.deleteVideoSurveillanceById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findVideoSurveillanceByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = videoSurveillanceMapper.findVideoSurveillanceByPage(parameter);
		Map<Object, Object> pageInformation=videoSurveillanceMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}
	
	public Map<Object, Object> getDetailInformationAllData(Map<Object, Object> parameter){
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = videoSurveillanceMapper.getDetailInformationAllData(parameter);
		map.put("items", list);
		return map;		
	}

}
