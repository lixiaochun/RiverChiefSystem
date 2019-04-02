package app.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.util.Constant;

@Controller
@RequestMapping("/appController")
public class AppController {

	Constant constant = new Constant();

	@RequestMapping(value = { "/queryVersion" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		InputStream inStream;
		Properties prop;
		String version = "";
		try {
			// inStream = this.getClass().getResourceAsStream("/conf/path.properties");
			// prop = new Properties();
			// prop.load(inStream);
			// version = prop.getProperty("version");
			version = constant.getProperty("version");
			map.put("version", version);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return map;
	}
}
