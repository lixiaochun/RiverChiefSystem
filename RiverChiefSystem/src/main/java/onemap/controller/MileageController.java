
package onemap.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import onemap.service.MileageService;

@Controller
@RequestMapping({ "/MileageController" })
public class MileageController {
	
	@Autowired
	private MileageService mileageService;
	
	@RequestMapping(value = { "/calculationMileage" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> calculationMileage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		// 处理参数
		String regionId = request.getParameter("regionId");
		String pointText = request.getParameter("pointText");
		System.out.println("regionId:"+regionId);
		System.out.println("pointText:"+pointText);

		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("regionId", regionId);
		parameter.put("pointText", pointText);
		
		map = mileageService.CalculationMileage(regionId, pointText);
		
		
		return map;
	}
	

}
