package geojson.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import geojson.mapper.GeojsonPatrolRecordMapper;
import geojson.model.Geometry;
import geojson.model.PatrolRecordFeature;
import geojson.model.PatrolRecordGeojson;
import geojson.util.PatrolRecordPoint;

@Controller
@RequestMapping({ "/geojsonPatrolRecordController" })
public class GeojsonPatrolRecordController {
	
	@Autowired
	private GeojsonPatrolRecordMapper geojsonPatrolRecordMapper;
	
	
	@RequestMapping(value = { "/getGeojson" }, method = {
			RequestMethod.GET }, produces = "application/octet-stream; charset=utf-8")
	public void getBasicInformation(HttpServletRequest request, HttpServletResponse response) {
	
		try {
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition",
					"attachment;filename=" + URLEncoder.encode("PatrolRecord.geojson", "UTF-8"));
			//设置允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			
			Gson gson = new Gson();
			PatrolRecordGeojson geojson = new PatrolRecordGeojson();
			geojson.type = "FeatureCollection";
			List<PatrolRecordFeature> features = new ArrayList<>();
			
			Map<Object, Object> parameter = new HashMap<>();
			String maxMonth = request.getParameter("maxMonth");
			String minMonth = request.getParameter("minMonth");
			String maxDay = request.getParameter("maxDay");
			String minDay = request.getParameter("minDay");
			parameter.put("maxMonth", maxMonth);
			parameter.put("minMonth", minMonth);
			parameter.put("maxDay", maxDay);
			parameter.put("minDay", minDay);
			System.out.println("maxMonth:"+maxMonth);
			System.out.println("minMonth:"+minMonth);
			System.out.println("maxDay:"+maxDay);
			System.out.println("minDay:"+minDay);

			List<Map<Object, Object>> list = geojsonPatrolRecordMapper.selectPatrolRecord(parameter);
			System.out.println("PatrolRecordSize:"+list.size());
			for (Map<Object, Object> map : list) {
				PatrolRecordFeature feature = new PatrolRecordFeature();
				feature.type = "Feature";
				feature.id = (int) map.get("id");
				feature.properties.gid = (int) map.get("id");
				feature.properties.name = (String) map.get("code");
				Geometry geometry = feature.geometry;
				geometry.type = "LineString";
				byte []bytes = (byte[]) map.get("point");
				String pointText = new String(bytes);
				System.out.println(pointText);
				List<Double[]> pointList = PatrolRecordPoint.getPatrolRecordPointList(pointText);
				geometry.coordinates = pointList;
				feature.geometry = geometry;
				features.add(feature);
				System.out.println(geometry.toString());
			}
			geojson.features = features;
			System.out.println(gson.toJson(geojson));
			PrintWriter pw = response.getWriter();
			pw.write(gson.toJson(geojson));			
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
