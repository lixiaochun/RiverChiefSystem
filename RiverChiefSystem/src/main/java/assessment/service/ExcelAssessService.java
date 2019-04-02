package assessment.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.model.ExcelAssess;
import common.model.ExcelColumn;

public interface ExcelAssessService {
	public void importRules(HttpServletRequest request) throws Exception;

	public String showRules(String excelId);

	public List<ExcelAssess> findAllList(ExcelAssess ea);

	public int countList(ExcelAssess ea);

	public String modifyRules(String excelId);

	public ExcelAssess findOldAssessContent(String rowId);

	public void updateAssessContent(ExcelAssess ea, String content);

	public ExcelAssess findOldAssessNorm(String rowId);

	public void updateAssessNorm(ExcelAssess ea, String content);

	public void updateScoreExplain(ExcelColumn ec);
	
	public void updateExplain(ExcelColumn ec);

	public void deleteExcel(String excelId);

	public void confirmTemp(String excelId);

	public boolean exportTempRules(HttpServletRequest request, HttpServletResponse response,boolean isOnLine);

	
}
