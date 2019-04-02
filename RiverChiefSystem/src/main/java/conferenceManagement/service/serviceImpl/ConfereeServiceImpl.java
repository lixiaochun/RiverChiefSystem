package conferenceManagement.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.Conferee;
import common.model.Conference;
import conferenceManagement.mapper.ConfereeMapper;
import conferenceManagement.service.ConfereeService;
@Service("ConfereeService")
public class ConfereeServiceImpl implements ConfereeService{

	@Autowired
	private ConfereeMapper confereeMapper;
	
	public Map<Object, Object> queryConferee(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		List<Conferee> list=confereeMapper.queryConferee(map);
		int count = confereeMapper.countConferee(map);
		if(list!=null&&list.size()>0) {
			result.put("result", 1);
			result.put("Conferee",list);
			result.put("countConferee", count);
		}
		else
		{
			result.put("result", 0);
		}
		
		return result;
	}

	public Map<Object, Object> insertConferee(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Conferee conferee=new Conferee();
			//set数据
			conferee.setConferenceId(Integer.parseInt(map.get("conferenceId").toString()));
			conferee.setStatus(Integer.parseInt(map.get("status").toString()));
			conferee.setUserId(Integer.parseInt(map.get("userId").toString()));
			confereeMapper.insertConferee(conferee);
			if(conferee.getConfereeId()!=0) {
				result.put("result",1);
				result.put("confereeId",conferee.getConfereeId());
			}
		} catch (Exception e) {
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> updateConferee(Map<Object, Object> map) {
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();	
		try {
			//从map里获取id
			int confereeId=Integer.parseInt(map.get("confereeId").toString());
			query.put("confereeId", confereeId);
			List<Conferee> list=confereeMapper.queryConferee(query);
			if(list!=null&&list.size()>0)
			{
				confereeMapper.updateConferee(map);
				result.put("result", 1);
				
			}
		}catch(Exception e)
		{
			result.put("result",0);
		}
		return result;
	}

	public Map<Object, Object> deleteConferee(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();		
		Map<Object, Object> query = new HashMap<Object, Object>();
		try
		{
			//从map里获取id
			List<Conferee> list=confereeMapper.queryConferee(map);
			if(list!=null&&list.size()>0)
			{
				confereeMapper.deleteConferee(map);
				result.put("result", 1);
				
			}
		}catch(Exception e)
		{
			result.put("result",0);
		}
		return result;
	}

}
