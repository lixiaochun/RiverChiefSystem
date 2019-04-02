package statisticalform.service.serviceImpl;


import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Statistics;
import statisticalform.service.StatisticalExcelService;

@Service("StatisticalExcelService")
@Transactional(readOnly = true)
public class StatisticalExcelServiceImpl implements  StatisticalExcelService{

	@Override
	public boolean ExportProblemTypeExcel(HttpServletRequest request, HttpServletResponse response,
			List<Map<String, Object>> problemTypeList) {
		try {
			String url = request.getSession().getServletContext().getRealPath("/");
			File excel= new File(url+"/template/statisticalExcel/问题类型统计.xlsx");
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excel);
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook,100);
			int rownum = 1;
			int obNum = 0;
			SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(0);
			SXSSFRow row = (SXSSFRow) sheet.getRow(0);
				for(Map<String, Object> ob:problemTypeList){
					row = (SXSSFRow) sheet.createRow(rownum);
					obNum = 0;
					for (Map.Entry<String, Object> entry : ob.entrySet()) { 
						if(entry.getKey().equals("count")){
							row.createCell(obNum).setCellValue(((BigDecimal)entry.getValue()).doubleValue());
						}else{
							 row.createCell(obNum).setCellValue((String)entry.getValue());
						}
						  obNum ++;
						}
					rownum++;
				}
			OutputStream out = null;
				// 文件名应该编码成UTF-8
			Format format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(new Date());
				String filename = URLEncoder.encode("问题类型统计"+date+".xlsx", "UTF-8");
				response.reset(); // 非常重要
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				out = response.getOutputStream();
				workbook.write(out);
				workbook.close();
				xssfWorkbook.close();
				out.flush();
				out.close();
				workbook = null;
				xssfWorkbook = null;
				out = null;
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean ExportPatrolExcel(HttpServletRequest request, HttpServletResponse response,
			List<Statistics> patrolRecordList) {
		try {
			String url = request.getSession().getServletContext().getRealPath("/");
			File excel= new File(url+"/template/statisticalExcel/巡河统计报表.xlsx");
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excel);
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook,100);
			int rownum = 1;
			int obNum = 0;
			SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(0);
			SXSSFRow row = (SXSSFRow) sheet.getRow(0);
				for(Statistics st:patrolRecordList){
					row = (SXSSFRow) sheet.createRow(rownum);
					
					row.createCell(0).setCellValue(st.getRegionId());
					
					row.createCell(1).setCellValue(st.getRegionName());
					
					row.createCell(2).setCellValue(st.getCompleteSum());
					
					row.createCell(3).setCellValue(st.getRecordGrade());
					
					row.createCell(4).setCellValue(st.getPercent());
					
					rownum++;
				}
			OutputStream out = null;
				// 文件名应该编码成UTF-8
			Format format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(new Date());
				String filename = URLEncoder.encode("巡河统计报表"+date+".xlsx", "UTF-8");
				response.reset(); // 非常重要
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				out = response.getOutputStream();
				workbook.write(out);
				workbook.close();
				xssfWorkbook.close();
				out.flush();
				out.close();
				workbook = null;
				xssfWorkbook = null;
				out = null;
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean ExportEventTypeExcel(HttpServletRequest request, HttpServletResponse response,
			List<Map<String, Object>> eventTypeList) {
			try {
				String url = request.getSession().getServletContext().getRealPath("/");
				File excel= new File(url+"/template/statisticalExcel/事件分类报表.xlsx");
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excel);
				SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook,100);
				int rownum = 1;
				int obNum = 0;
				SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(0);
				SXSSFRow row = (SXSSFRow) sheet.getRow(0);
					for(Map<String, Object> ob:eventTypeList){
						row = (SXSSFRow) sheet.createRow(rownum);
						obNum = 0;
						for (Map.Entry<String, Object> entry : ob.entrySet()) { 
							if(entry.getKey().equals("count")){
								row.createCell(obNum).setCellValue(((long)entry.getValue()));
							}else{
								 row.createCell(obNum).setCellValue((String)entry.getValue());
							}
							  obNum ++;
							}
						rownum++;
					}
				OutputStream out = null;
					// 文件名应该编码成UTF-8
				Format format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(new Date());
					String filename = URLEncoder.encode("事件分类报表"+date+".xlsx", "UTF-8");
					response.reset(); // 非常重要
					response.setContentType("application/x-msdownload");
					response.setHeader("Content-Disposition", "attachment; filename=" + filename);
					out = response.getOutputStream();
					workbook.write(out);
					workbook.close();
					xssfWorkbook.close();
					out.flush();
					out.close();
					workbook = null;
					xssfWorkbook = null;
					out = null;
					return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
}
