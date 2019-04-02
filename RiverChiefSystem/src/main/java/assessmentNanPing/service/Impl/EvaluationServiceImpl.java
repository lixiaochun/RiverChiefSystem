package assessmentNanPing.service.Impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assessmentNanPing.mapper.EvaluationMapper;
import assessmentNanPing.service.EvaluationService;
import common.model.EvaluationCriteria;
import common.model.EvaluationPieCharts;
import common.model.UserRank;
import common.util.DateHelper;

@Service("EvaluationService")
public class EvaluationServiceImpl implements EvaluationService{
    
	@Autowired
	private EvaluationMapper evaluationMapper;
	
	public int queryUserPatrolDays(int userId, String dateStr) {
		int dayNum = evaluationMapper.queryUserPatrolDays(userId,dateStr);
		return dayNum;
	}

	public int queryUserPatrolTime(int userId, String dateStr) {
		int patrolTime = evaluationMapper.queryUserPatrolTime(userId,dateStr);
		return patrolTime;
	}

	public double queryUserPatrolMileage(int userId, String dateStr) {
		double patrolMileage = evaluationMapper.queryUserPatrolMileage(userId,dateStr);
		return patrolMileage;
	}

	public int queryUserEventNum(int userId, String dateStr) {
		int eventNum = evaluationMapper.queryUserEventNum(userId,dateStr);
		return eventNum;
	}

	public List<EvaluationPieCharts> queryUserPieChart(int userId, String dateSubOneMonth) {
		List<EvaluationPieCharts> epcList = evaluationMapper.queryUserPieChart(userId,dateSubOneMonth);
		return epcList;
	}

	public String downLoadUserRank(HttpServletRequest request, HttpServletResponse response, String dateStr) {
		OutputStream out = null;
		try {
			String url = request.getSession().getServletContext().getRealPath("/");
			File file= new File(url+"/template/statisticalExcel/考核排名模板.xlsx");
			OPCPackage opcPackage = OPCPackage.open(file);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(opcPackage);
			@SuppressWarnings("resource")
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook,1000);
			int rownum = 1;
			SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(0);
			SXSSFRow row = (SXSSFRow) sheet.getRow(0);
			dateStr = dateStr+"%";
			List<UserRank> urList= evaluationMapper.queryUserRank(dateStr);
			for(UserRank ur :urList){
				row = (SXSSFRow) sheet.createRow(rownum);
				row.createCell(0).setCellValue(rownum);
				row.createCell(1).setCellValue(ur.getRealName());
				row.createCell(2).setCellValue(ur.getTotalScore());
				row.createCell(3).setCellValue(ur.getRegionName());
				rownum++;
			} 
				Date curDate = new Date();
				String now = DateHelper.formatDate(curDate);
				// 文件名应该编码成UTF-8
				String filename = URLEncoder.encode(now+"上月份巡河员排名情况"+".xlsx", "UTF-8");
				response.reset(); // 非常重要
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				out = response.getOutputStream();
				workbook.write(out);
				workbook = null;
				xssfWorkbook = null;
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}finally {
			try {
				if(out != null) {
					out.flush();out.close();out = null;System.gc();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public List<EvaluationCriteria> queryCurrentCriteria() {
		Date date = new Date();
		String currentDay = DateHelper.formatDateByFormat(date, "yyyy-MM");
		currentDay = currentDay + "-01";
		List<EvaluationCriteria> eaList = evaluationMapper.queryCurrentCriteria(currentDay);
		return eaList;
	}

	public void updateCurrentCriteria(List<EvaluationCriteria> eaList) {
		Date date = new Date();
		String currentDay = DateHelper.formatDateByFormat(date, "yyyy-MM");
		currentDay = currentDay +"-01";
		date = DateHelper.parseDate(currentDay,"yyyy-MM-dd");
		    for(EvaluationCriteria ec:eaList){
		    	ec.setYearMonth(date);
		    }
			evaluationMapper.updateCurrentCriteria(eaList);
	}
}
