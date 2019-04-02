package geojson.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import geojson.mapper.GeojsonMapper;
import geojson.model.Feature;
import geojson.model.Geojson;
import geojson.model.Geometry;

@Controller
@RequestMapping({ "/geojsonController" })
public class GeojsonController {

	@Autowired
	private GeojsonMapper geojsonMapper;

	/**
	 * 获取geojson文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getGeojson" }, method = {
			RequestMethod.GET }, produces = "application/octet-stream; charset=utf-8")
	public void getBasicInformation(HttpServletRequest request, HttpServletResponse response) {

		try {
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition",
					"attachment;filename=" + URLEncoder.encode("fuding.geojson", "UTF-8"));
			//设置允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			Gson gson = new Gson();
			Geojson geojson = new Geojson();
			geojson.type = "FeatureCollection";
			List<Feature> features = new ArrayList<>();
			//切换数据库
			DbcontextHolder.setDbType("postgresDataSource");
			List<Map<Object, Object>> list = geojsonMapper.selectGeojson();
			for (Map<Object, Object> map : list) {
				Feature feature = new Feature();
				feature.type = "Feature";
				feature.id = (int) map.get("gid");
				feature.properties.gid = (int) map.get("id");
				feature.properties.name = (String) map.get("code");
				Geometry geometry = gson.fromJson((String) map.get("geometry"), Geometry.class);
				feature.geometry = geometry;
				features.add(feature);
			}
			geojson.features = features;
			// System.out.println(gson.toJson(geojson));
			PrintWriter pw = response.getWriter();
			pw.write(gson.toJson(geojson));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
