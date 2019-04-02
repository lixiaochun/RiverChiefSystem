package workmanage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.util.GPSDataFactory;

@Controller
@RequestMapping("/adjustOrbit")
public class AdjustOrbitController {

	@RequestMapping(value = "/newOrbit.do", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<List<Double>> newOrbit(@RequestParam(value = "line") String line) {

		String[] points = line.split(",");
		// List<String> points = Arrays.asList(lines);
		// System.out.println(points.toString());

		GPSDataFactory gpsDataFactory = new GPSDataFactory(points);

		return gpsDataFactory.getNewPoints();
	}
}
