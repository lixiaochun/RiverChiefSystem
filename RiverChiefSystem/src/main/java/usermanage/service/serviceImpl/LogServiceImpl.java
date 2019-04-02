package usermanage.service.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.SystemLog;
import usermanage.mapper.LogMapper;
import usermanage.service.LogService;


@Service("LogService")
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService{

	@Autowired
	private LogMapper  logMapper;
	@Transactional(readOnly = false)
	public void addLog(String userId,String operInterface, String operName, String operType, String logType, String logDetail) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("userId", userId);
			map.put("operaInterface", operInterface);
			map.put("operaName", operName);
			map.put("operaType", operType);
			map.put("logType", logType);
			map.put("operaTime", new Date());
			map.put("logDetail", logDetail);
			logMapper.addLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = false)
	public void addLog(String operInterface, String operName, String operType, String logType, String logDetail) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("operaInterface", operInterface);
			map.put("operaName", operName);
			map.put("operaType", operType);
			map.put("logType", logType);
			map.put("operaTime", new Date());
			map.put("logDetail", logDetail);
			logMapper.addLog1(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Object, Object> queryLog(Map<Object, Object> map) {
		Map<Object, Object> result =  new HashMap<Object, Object>();
		try {
			List<SystemLog> list = logMapper.queryLog(map);
			int count = logMapper.countLog(map);
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("logcount", count);
			result.put("logList", list);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}
}
