package onemap.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onemap.service.InlandWaterQualityService;
import onemap.service.OnemapEventService;
import onemap.service.OutfallService;
import onemap.service.PollutantSourceService;
import onemap.service.PublicsignsService;
import onemap.service.RaininfoService;
import onemap.service.VideoSurveillanceService;
import onemap.service.WaterFunctionService;
import onemap.service.WaterIntakeService;
import onemap.service.WaterLineService;
import onemap.service.WaterLoggingService;
import onemap.service.WaterNumberService;
import onemap.service.WaterProjectService;
import onemap.service.WaterQualityService;
import onemap.service.WaterSourceService;
import onemap.util.IdUitl;
import usermanage.service.UserService;
import workmanage.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/detailInformationController" })
public class DetailInformationController {

	@Autowired
	private OutfallService outfallService;

	@Autowired
	private PollutantSourceService pollutantSourceService;

	@Autowired
	private RaininfoService rainInfoService;

	@Autowired
	private WaterLoggingService waterLoggingService;

	@Autowired
	private PublicsignsService publicSignsService;

	@Autowired
	private WaterIntakeService waterIntakeService;

	@Autowired
	private WaterNumberService waterNumberService;

	@Autowired
	private WaterLineService waterLineService;

	@Autowired
	private WaterQualityService waterQualityService;

	@Autowired
	private WaterProjectService waterProjectService;

	@Autowired
	private InlandWaterQualityService inlandWaterQualityService;
	
	@Autowired
	private WaterFunctionService waterFunctionService;

	@Autowired
	private WaterSourceService waterSourceService;

	@Autowired
	private OnemapEventService onemapEventService;

	@Autowired
	private VideoSurveillanceService videoSurveillanceService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;

	/**
	 * 查询基础设施详细信息 code:基础设施类别，id:唯一标识
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getDetailInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getDetailInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}

		String code = request.getParameter("code");
		int id = Integer.valueOf(request.getParameter("id"));
		if (code.startsWith("11")) {
			map = waterQualityService.findWaterQualityById(id);
		} else if (code.startsWith("12")) {
			map = waterLineService.findWaterLineById(id);
		} else if (code.startsWith("13")) {
			map = waterNumberService.findWaterNumberById(id);
		} else if (code.startsWith("14")) {
			map = rainInfoService.findRaininfoById(id);
		} else if (code.startsWith("15")) {
			map = inlandWaterQualityService.findInlandWaterQualityById(id);
		} else if (code.startsWith("16")) {
			map = waterFunctionService.findWaterFunctionById(id);
		} else if (code.startsWith("17")) {
			map = waterSourceService.findWaterSourceById(id);
		} else if (code.startsWith("18")) {
			map = onemapEventService.findEventById(id);
		} else if (code.startsWith("21")) {
			map = publicSignsService.findPublicsignsById(id);
		} else if (code.startsWith("22")) {

		} else if (code.startsWith("23")) {
			map = waterIntakeService.findWaterIntakeById(id);
		} else if (code.startsWith("24")) {
			map = outfallService.findOutfallById(id);
		} else if (code.startsWith("25")) {
			map = pollutantSourceService.findPollutantsourceById(id);
		} else if (code.startsWith("26")) {
			map = waterProjectService.findWaterProjectById(id);
		} else if (code.startsWith("27")) {
			map = waterLoggingService.findWaterloggingById(id);
		} else if (code.startsWith("28")) {
			map = videoSurveillanceService.findVideoSurveillanceById(id);
		} else if (code.startsWith("31")) {
			Map<Object, Object> parameter = new HashMap<Object, Object>();
			parameter.put("eventId", id);
			map = eventService.queryEventDetail(parameter);
		} else if (code.startsWith("32")) {
			Map<Object, Object> parameter = new HashMap<Object, Object>();
			parameter.put("eventId", id);
			map = eventService.queryEventDetail(parameter);
		} else {
			map.put("result", 0);
			map.put("message", "操作失败");
			return map;
		}
		map.put("result", 1);
		map.put("message", "操作成功");

		return map;
	}

	/**
	 * 分页查询基础信息详情 curPage:当前页码，pageNumber:每页显示数量，regionId:根据行政区筛选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getDetailInformationAll" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getDetailInformationByPage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		// 处理参数
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			System.out.println(paraName + ": " + value);
		}
		String code = request.getParameter("code");
		String pageNumber = request.getParameter("pageNumber");
		String regionId = request.getParameter("regionId");
		regionId = IdUitl.getRegionId(regionId);
		String curPage = String
				.valueOf((Integer.valueOf(request.getParameter("curPage")) - 1) * Integer.valueOf(pageNumber));
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("curPage", curPage);
		parameter.put("pageNumber", pageNumber);
		parameter.put("regionId", regionId);
		if (code.startsWith("11")) {
			map = waterQualityService.findWaterQualityByPage(parameter);
		} else if (code.startsWith("12")) {
			map = waterLineService.findWaterLineByPage(parameter);
		} else if (code.startsWith("13")) {
			map = waterNumberService.findWaterNumberByPage(parameter);
		} else if (code.startsWith("14")) {
			map = rainInfoService.findRaininfoByPage(parameter);
		} else if (code.startsWith("15")) {
			map = inlandWaterQualityService.findInlandWaterQualityByPage(parameter);
		} else if (code.startsWith("16")) {
			map = waterFunctionService.findWaterFunctionByPage(parameter);
		} else if (code.startsWith("17")) {
			map = waterSourceService.findWaterSourceByPage(parameter);
		} else if (code.startsWith("18")) {

		} else if (code.startsWith("21")) {
			map = publicSignsService.findPublicsignsByPage(parameter);
		} else if (code.startsWith("22")) {

		} else if (code.startsWith("23")) {
			map = waterIntakeService.findWaterIntakeByPage(parameter);
		} else if (code.startsWith("24")) {
			map = outfallService.findOutfallByPage(parameter);
		} else if (code.startsWith("25")) {
			map = pollutantSourceService.findPollutantsourceByPage(parameter);
		} else if (code.startsWith("26")) {
			map = waterProjectService.findWaterProjectByPage(parameter);
		} else if (code.startsWith("27")) {
			map = waterLoggingService.findWaterloggingByPage(parameter);
		} else if (code.startsWith("28")) {
			map = videoSurveillanceService.findVideoSurveillanceByPage(parameter);
		} else {
			map.put("result", 0);
			map.put("message", "操作失败");
			return map;
		}
		map.put("code", code);
		map.put("curPage", request.getParameter("curPage"));
		map.put("result", 1);
		map.put("message", "操作成功");

		return map;
	}

	/**
	 * 返回全部数据信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getDetailInformationAllData" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getDetailInformationAllData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		// 处理参数
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			System.out.println(paraName + ": " + value);
		}
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		String code = request.getParameter("code");
		if(request.getParameter("keyword") != null) {
			String keyword = request.getParameter("keyword");
			parameter.put("keyword", keyword);
		}
		if(code.startsWith("28")) {
			map = videoSurveillanceService.getDetailInformationAllData(parameter);
		} else {
			map.put("result", 0);
			map.put("message", "操作失败");
			return map;
		}

		map.put("result", 1);
		map.put("message", "操作成功");

		return map;
	}

}
