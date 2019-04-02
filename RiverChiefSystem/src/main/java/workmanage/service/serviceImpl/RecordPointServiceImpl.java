package workmanage.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.RecordPoint;
import workmanage.mapper.RecordPointMapper;
import workmanage.service.RecordPointService;

@Service("RecordPointService")
public class RecordPointServiceImpl implements  RecordPointService{
	
	@Autowired
	private RecordPointMapper recordPointMapper;

	public Map<Object, Object> queryRecordPointByPatrolRecordId(int patrolRecordId) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		List<RecordPoint> list=recordPointMapper.queryRecordPointByPatrolRecordId(patrolRecordId);
		if(list!=null&&list.size()>0) {
			result.put("RecordPoint",list);
		}
		else
		result.put("result", 0);
		return result;
	}
	

}
