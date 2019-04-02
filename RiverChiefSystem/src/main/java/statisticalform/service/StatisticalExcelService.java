package statisticalform.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.model.Statistics;


public interface StatisticalExcelService {
	public boolean ExportProblemTypeExcel(HttpServletRequest request,HttpServletResponse response, List<Map<String,Object>> problemTypeList);

	public boolean ExportPatrolExcel(HttpServletRequest request, HttpServletResponse response,List<Statistics> patrolRecordList);

	public boolean ExportEventTypeExcel(HttpServletRequest request, HttpServletResponse response,List<Map<String, Object>> eventTypeList);
}
