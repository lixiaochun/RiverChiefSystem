package assessment.mapper;

import java.util.List;

import common.model.ExcelAssess;
import common.model.ExcelColumn;

public interface ExcelAssessMapper {

	public void insertExcel(List<ExcelAssess> eaList);

	public List<String> findFirstRowNum(ExcelAssess ea);

	public List<ExcelAssess> findExcelAssessListForOne(ExcelAssess entity);//

	public List<String> findSecondRowNum(ExcelAssess ea);

	public void insertExcelMain(ExcelAssess ea);
	
	public List<ExcelAssess> findAllList(ExcelAssess ea);//分页查询

	public int countList(ExcelAssess ea);
	
	public ExcelAssess findOldAssessContent(String rowId);//查询旧的评分内容
	public void updateAssessContent(ExcelAssess ea);//更新新的评分内容

	public ExcelAssess findOldAssessNorm(String rowId);//查询旧的评分细则
	public void updateAssessNorm(ExcelAssess ea);//更新新的评分细则
	
	public void updateScoreExplain(ExcelColumn ec);//更新赋分说明
	
	public void updateExplain(ExcelColumn ec);//更新说明

	public void deleteExcel(String excelId);
	
	public void confirmTemp(String excelId);

	public void deleteExcelDetail(String excelId);

}
