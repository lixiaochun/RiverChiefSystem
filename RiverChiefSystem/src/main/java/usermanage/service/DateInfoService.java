package usermanage.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface DateInfoService {

	/**
	 * 
	 * @Title: exportExcel
	 * @Des: 导出河流的excel
	 * @Params:
	 * @Return: Map<Object,Object>
	 * @Author: lyf
	 * @Date: 2017年12月26日
	 */
	public Map<Object, Object> exportExcel(Map<Object, Object> map, HttpServletResponse response);

	/**
	 * 
	 * @Title: importExcel
	 * @Des:导出excel
	 * @Params:
	 * @Return: Map<Object,Object>
	 * @Author: lyf
	 * @Date: 2017年12月27日
	 */
	public Map<Object, Object> importExcel(Map<Object, Object> map, HttpServletResponse response);

	/**
	 * 
	 * @Title: queryRegion
	 * @Des:查询行政区列表
	 * @Params:
	 * @Return: Map<Object,Object>
	 * @Author: lyf
	 * @Date: 2017年12月29日
	 */
	public Map<Object, Object> queryRegion(Map<Object, Object> map);

	/**
	 * 
	 * @Title: queryProblemType
	 * @Des:查询问题列表
	 * @Params:
	 * @Return: Map<Object,Object>
	 * @Author: lyf
	 * @Date: 2017年12月29日
	 */
	public Map<Object, Object> queryProblemType(Map<Object, Object> map);
}
