package basemanage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import basemanage.mapper.OperationManualMapper;
import basemanage.model.OperationManualFile;

@Controller
@RequestMapping({ "/operationManualController" })
public class OperationManualController {

	@Autowired
	private OperationManualMapper operationManualMapper;
	
	@RequestMapping(value = { "/getOperationManualFileList" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getOperationManualFileList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		List<OperationManualFile>  list = operationManualMapper.selectFileList(parameter);
		
		map.put("items", list);
		map.put("result", 1);
		map.put("message", "操作成功");
	
		return map;
	}
	
	
	
}
