package onemap.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import onemap.util.Constant;
import onemap.util.IdUitl;
import usermanage.service.UserService;
import workmanage.service.WorkflowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping({ "/basicInformationController" })
public class BasicInformationController {

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
	private WorkflowService workflowService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/getBasicInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBasicInformation(HttpServletRequest request,
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
		String code = request.getParameter("code");
		String type = request.getParameter("type");
		String value = request.getParameter("value");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("code", code);
		if (type.equals("0")) {
			String regionIds = value;
			String regionIdArray[] = regionIds.substring(1, regionIds.length() - 1).split(",");
			for (int i = 0; i < regionIdArray.length; i++) {
				regionIdArray[i] = IdUitl.getRegionId(regionIdArray[i]);
				System.out.println(regionIdArray[i]);
			}
			parameter.put("regionIdArray", regionIdArray);
		} else if (type.equals("1")) {
			String riverIds = value;
			String riverIdArray[] = riverIds.substring(1, riverIds.length() - 1).split(",");
			for (int i = 0; i < riverIdArray.length; i++) {
				riverIdArray[i] = IdUitl.getRiverId(riverIdArray[i]);
			}
			parameter.put("riverIdArray", riverIdArray);
		}

		// 处理业务
		if (code.startsWith("11")) {
			map = waterQualityService.findWaterQualityAll(parameter);
		} else if (code.startsWith("12")) {
			map = waterLineService.findWaterLineAll(parameter);
		} else if (code.startsWith("13")) {
			map = waterNumberService.findWaterNumberAll(parameter);
		} else if (code.startsWith("14")) {
			map = rainInfoService.findRaininfoAll(parameter);
		} else if (code.startsWith("15")) {
			map = inlandWaterQualityService.findInlandWaterQualityAll(parameter);
		}  else if (code.startsWith("16")) {
			map = waterFunctionService.findWaterFunctionAll(parameter);
		} else if (code.startsWith("17")) {
			map = waterSourceService.findWaterSourceAll(parameter);
		} else if (code.startsWith("18")) {
			map = onemapEventService.findEventAll(parameter);
		} else if (code.startsWith("21")) {
			parameter.put("keyword", request.getParameter("keyword") != null ? request.getParameter("keyword") : null);
			parameter.put("regionName", request.getParameter("regionName") != null ? request.getParameter("regionName") : null);
			map = publicSignsService.findPublicsignsAll(parameter);
		} else if (code.startsWith("23")) {
			map = waterIntakeService.findWaterIntakeAll(parameter);
		} else if (code.startsWith("24")) {
			map = outfallService.findOutfallAll(parameter);
		} else if (code.startsWith("25")) {
			map = pollutantSourceService.findPollutantsourceAll(parameter);
		} else if (code.startsWith("26")) {
			map = waterProjectService.findWaterProjectAll(parameter);
		} else if (code.startsWith("27")) {
			map = waterLoggingService.findWaterloggingAll(parameter);
		} else if (code.startsWith("28")) {
			parameter.put("keyword", request.getParameter("keyword") != null ? request.getParameter("keyword") : null);
			map = videoSurveillanceService.findVideoSurveillanceAll(parameter);
		} else if (code.startsWith("31")) {
			parameter.put("reportTpye", code.length() == 3 ? code.substring(2, 3) : null);
			map = onemapEventService.findEventAll(parameter);
		} else if (code.startsWith("32")) {
			parameter.put("type", 3);
			parameter.put("userId", userId);
			List<String> eventIdList = workflowService.queryTask(parameter);
			System.out.println("eventIdList:"+eventIdList);
			parameter.put("eventIdList", eventIdList);
			map = onemapEventService.findEventPoint(parameter);
		} else {
			map.put("result", 0);
			map.put("message", "code操作失败");
			return map;
		}

		map.put("code", code);
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) map.get("items");
		
		if (result.size() > 0) {
			map.put("result", 1);
			map.put("message", "操作成功");
		} else {
			map.put("result", 2);
			map.put("message", "查无数据");
		}
		return map;
	}

	@RequestMapping(value = { "/insertBasicInformation" }, method = { RequestMethod.POST })
	public @ResponseBody Map<Object, Object> insertBasicInformation(HttpServletRequest request,
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
		System.out.println("获取到的参数：");
		String code = request.getParameter("code");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			parameter.put(paraName, value);
			if (paraName.equals("point")) {
				String longitute = value.substring(0, value.indexOf(","));
				String latitude = value.substring(value.indexOf(",") + 1);
				parameter.put("longitute", longitute);
				parameter.put("latitude", latitude);
			}
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}

		// 处理业务
		if (code.startsWith("11")) {
			parameter = savaFile(parameter, request, "onemapfile/waterquality/");
			map = waterQualityService.insertWaterQuality(parameter);
		} else if (code.startsWith("12")) {
			map = waterLineService.insertWaterLine(parameter);
		} else if (code.startsWith("13")) {
			map = waterNumberService.insertWaterNumber(parameter);
		} else if (code.startsWith("14")) {
			map = rainInfoService.insertRaininfo(parameter);
		} else if (code.startsWith("15")) {
			parameter = savaFile(parameter, request, "onemapfile/inlandwaterquality/");
			map = inlandWaterQualityService.insertInlandWaterQuality(parameter);
		} else if (code.startsWith("18")) {

		} else if (code.startsWith("21")) {
			parameter = savaFile(parameter, request, "onemapfile/publicsigns/");
			parameter.put("base_info_type", "211");
			map = publicSignsService.insertPublicsigns(parameter);
		} else if (code.startsWith("23")) {
			parameter = savaFile(parameter, request, "onemapfile/waterintake/");
			map = waterIntakeService.insertWaterIntake(parameter);
		} else if (code.startsWith("24")) {
			parameter = savaFile(parameter, request, "onemapfile/outfall/");
			map = outfallService.insertOutfall(parameter);
		} else if (code.startsWith("25")) {
			map = pollutantSourceService.insertPollutantsource(parameter);
		} else if (code.startsWith("26")) {
			parameter = savaFile(parameter, request, "onemapfile/waterproject/");
			map = waterProjectService.insertWaterProject(parameter);
		} else if (code.startsWith("27")) {
			map = waterLoggingService.insertWaterlogging(parameter);
		} else if (code.startsWith("28")) {
			map = videoSurveillanceService.insertVideoSurveillance(parameter);
		} else {
			map.put("result", 0);
			map.put("message", "code操作失败");
			return map;
		}
		int result = (Integer) map.get("result");
		if (result == 1) {
			map.put("result", 1);
			map.put("message", "添加成功");
		} else {
			map.put("result", 2);
			map.put("message", "添加失败");
		}
		map.put("code", code);
		return map;
	}

	@RequestMapping(value = { "/updateBasicInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> updateBasicInformation(HttpServletRequest request,
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
		String code = request.getParameter("code");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			parameter.put(paraName, value);
			if (paraName.equals("point")) {
				String longitute = value.substring(0, value.indexOf(","));
				String latitude = value.substring(value.indexOf(",") + 1);
				parameter.put("longitute", longitute);
				parameter.put("latitude", latitude);
			}
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}
		// 处理业务
		if (code.startsWith("11")) {
			parameter = savaFile(parameter, request, "onemapfile/waterquality/");
			map = waterQualityService.updateWaterQuality(parameter);
		} else if (code.startsWith("12")) {
			map = waterLineService.updateWaterLine(parameter);
		} else if (code.startsWith("13")) {
			map = waterNumberService.updateWaterNumber(parameter);
		} else if (code.startsWith("14")) {
			map = rainInfoService.updateRaininfo(parameter);
		} else if (code.startsWith("15")) {
			parameter = savaFile(parameter, request, "onemapfile/inlandwaterquality/");
			map = inlandWaterQualityService.updateInlandWaterQuality(parameter);
		} else if (code.startsWith("21")) {
			parameter = savaFile(parameter, request, "onemapfile/publicsigns/");
			parameter.put("base_info_type", "211");
			map = publicSignsService.updatePublicsigns(parameter);
		} else if (code.startsWith("23")) {
			parameter = savaFile(parameter, request, "onemapfile/waterintake/");
			map = waterIntakeService.updateWaterIntake(parameter);
		} else if (code.startsWith("24")) {
			parameter = savaFile(parameter, request, "onemapfile/outfall/");
			map = outfallService.updateOutfall(parameter);
		} else if (code.startsWith("25")) {
			map = pollutantSourceService.updatePollutantsource(parameter);
		} else if (code.startsWith("26")) {
			parameter = savaFile(parameter, request, "onemapfile/waterproject/");
			map = waterProjectService.updateWaterProject(parameter);
		} else if (code.startsWith("27")) {
			map = waterLoggingService.updateWaterlogging(parameter);
		} else if (code.startsWith("28")) {
			map = videoSurveillanceService.updateVideoSurveillance(parameter);
		} else {
			map.put("result", 0);
			map.put("message", "code操作失败");
			return map;
		}
		int result = (Integer) map.get("result");
		if (result == 1) {
			map.put("result", 1);
			map.put("message", "更新成功");
		} else {
			map.put("result", 2);
			map.put("message", "更新失败");
		}
		map.put("code", code);
		return map;
	}

	@RequestMapping(value = { "/deleteBasicInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deleteBasicInformation(HttpServletRequest request,
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
		String code = request.getParameter("code");
		int id = Integer.valueOf(request.getParameter("id"));
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}
		// 处理业务
		if (code.startsWith("11")) {
			map = waterQualityService.deleteWaterQualityById(id);
		} else if (code.startsWith("12")) {
			map = waterLineService.deleteWaterLineById(id);
		} else if (code.startsWith("13")) {
			map = waterNumberService.deleteWaterNumberById(id);
		} else if (code.startsWith("14")) {
			map = rainInfoService.deleteRaininfoById(id);
		} else if (code.startsWith("15")) {
			map = inlandWaterQualityService.deleteInlandWaterQualityById(id);
		} else if (code.startsWith("21")) {
			map = publicSignsService.deletePublicsignsById(id);
		} else if (code.startsWith("23")) {
			map = waterIntakeService.deleteWaterIntakeById(id);
		} else if (code.startsWith("24")) {
			map = outfallService.deleteOutfallById(id);
		} else if (code.startsWith("25")) {
			map = pollutantSourceService.deletePollutantsourceById(id);
		} else if (code.startsWith("26")) {
			map = waterProjectService.deleteWaterProjectById(id);
		} else if (code.startsWith("27")) {
			map = waterLoggingService.deleteWaterloggingById(id);
		} else if (code.startsWith("28")) {
			map = videoSurveillanceService.deleteVideoSurveillanceById(id);
		} else {
			map.put("result", 0);
			map.put("message", "code操作失败");
			return map;
		}
		int result = (Integer) map.get("result");
		if (result == 1) {
			map.put("result", 1);
			map.put("message", "删除成功");
		} else {
			map.put("result", 2);
			map.put("message", "删除失败");
		}
		map.put("code", code);
		return map;
	}

	// 保存文件
	public static Map<Object, Object> savaFile(Map<Object, Object> parameter, HttpServletRequest request,
			String saveFilePath) {
		String NginxPath = Constant.getProperty("path");
		// 创建一个多分解的容器
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmr.isMultipart(request)) {
			// 将request转换成多分解请求
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			// 创建文件保存名
			String fileName = multipartFile.getOriginalFilename();
			String fileType = "";
			if (fileName.indexOf('.') != -1)
				fileType = fileName.substring(fileName.indexOf('.'), fileName.length());
			UUID uuid = UUID.randomUUID();
			String saveFileName = uuid.toString() + fileType;
			File fileMkdir = new File(NginxPath + saveFilePath);
			if (!fileMkdir.exists())
				fileMkdir.mkdirs();
			parameter.put("imgurl", saveFilePath + saveFileName);
			File file = new File(fileMkdir + "/" + saveFileName);
			System.out.println("文件保存路径：" + file.getAbsolutePath());
			// 上传文件
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		return parameter;
	}

}
