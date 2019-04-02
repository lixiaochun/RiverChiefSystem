package assessment.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import assessment.mapper.ExcelAssessMapper;
import assessment.service.ExcelAssessService;
import common.model.ExcelAssess;
import common.model.ExcelColumn;
import common.util.DateHelper;
import common.util.ExcelReader;

@Service("ExcelAssessService")
@Transactional(readOnly = true)
public class ExcelAssessServicesImpl implements ExcelAssessService {

	@Autowired
	private ExcelAssessMapper excelAssessMapper;

	private List<ExcelAssess> eaList = new ArrayList<ExcelAssess>();

	private String firstCol = "";

	private String secondCol = "";

	private String excelId = "";
	
	@Override
	public boolean exportTempRules(HttpServletRequest request, HttpServletResponse response,boolean isOnLine) {
		try {
			String url = request.getSession().getServletContext().getRealPath("/");
			File excel= new File(url+"/template/statisticalExcel/考评规则模板.xlsx");
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(excel));
		    byte[] buf = new byte[1024];
		    int len = 0;

		    response.reset(); // 非常重要
		    String filename = URLEncoder.encode("考核模板.xlsx", "UTF-8");
		    if (isOnLine) { // 在线打开方式
		        response.setContentType("application/x-msdownload");
		        response.setHeader("Content-Disposition", "inline; filename=" + filename);
		        // 文件名应该编码成UTF-8
		    } else { // 纯下载方式
		        response.setContentType("application/x-msdownload");
		        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		    }
		    OutputStream out = response.getOutputStream();
		    while ((len = br.read(buf)) > 0)
		        out.write(buf, 0, len);
		    br.close();
		    out.close();
		    System.gc();
		return true;
		} catch (Exception e) {
			return false;
		}
	}		

	@Transactional(readOnly = false)
	public void importRules(HttpServletRequest request) throws Exception {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multipartRequest.getFileNames();
			System.out.println(iter.toString());
			while (iter.hasNext()) {
				ExcelAssess ea = new ExcelAssess();
				MultipartFile file = multipartRequest.getFile((String) iter.next());
				/*==========================================================================*/
				Date curDate = new Date();
				excelId = DateHelper.formatDateByFormat(curDate, "yyMMddHHmmss");
				ea.setName(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+excelId);
				Random random = new Random();
				int r = random.nextInt(10000);
				/*==========================================================================*/
				excelId = excelId + r;
				InputStream in = file.getInputStream();
				ExcelReader reader = new ExcelReader() {
					public void getRows(int sheetIndex, int curRow, List<String> rowList) {
						if (curRow != 0) {// 抛弃表头
							insertExcel(curRow, rowList, false);
						}
					}

					public void getLastRows(int sheetIndex, int curRow) {// 插入最后不足百行的表格信息
						insertExcel(curRow, null, true);
					}
				};
				reader.process(in, 1);
				in.close();
				
				//将excel信息存入总表中.substring(0,getFilename .lastIndexOf("."));
				ea.setCreateTime(curDate);
				ea.setExcelId(excelId);
				excelAssessMapper.insertExcelMain(ea);
			}
		}
	}

	@Transactional(readOnly = false)
	public void insertExcel(int rowNum, List<String> strList, boolean isLast){
		if (strList != null) {
			ExcelAssess ea = new ExcelAssess();
			// ea.preCreate();
			if (strList.get(0) != null && !"".equals(strList.get(0))) {
				firstCol = strList.get(0);
			} else {
				strList.set(0, firstCol);
			}
			if (strList.get(1) != null && !"".equals(strList.get(1))) {
				secondCol = strList.get(1);
			} else {
				strList.set(1, secondCol);
			}
			ea.setAssessContent(strList.get(0));
			ea.setAssessNorm(strList.get(1));
			ea.setScoreExplain(strList.get(2));
			ea.setDescription(strList.get(3));
			ea.setExcelId(excelId);
			ea.setDelFlag("0");
			ea.setCreateTime(new Date());
			eaList.add(ea);
			if (eaList.size() % 10 == 0 && !isLast) {// 100行插入一次
				// 调用插入
				excelAssessMapper.insertExcel(eaList);
				eaList.clear();
			}
		} else if (isLast) {
			excelAssessMapper.insertExcel(eaList);
			eaList.clear();
		}

	}

	public String showRules(String excelId) {
		ExcelAssess ea = new ExcelAssess();
		ea.setExcelId(excelId);
		List<ExcelAssess> list = excelAssessMapper.findExcelAssessListForOne(ea);
		List<String> firstRowList = excelAssessMapper.findFirstRowNum(ea);
		List<String> secondRowList = excelAssessMapper.findSecondRowNum(ea);
		List<String> firstTable = new ArrayList<String>(firstRowList);
		List<String> secondTable = new ArrayList<String>(secondRowList);
		for (int x = 0; x < firstRowList.size(); x++) {
			if (x + 1 != firstRowList.size()) {
				firstRowList.set(x + 1, String.valueOf(Integer.parseInt(firstRowList.get(x)) + Integer.parseInt(firstRowList.get(x + 1))));
			}
		}
		for (int y = 0; y < secondRowList.size(); y++) {
			if (y + 1 != secondRowList.size()) {
				secondRowList.set(y + 1, String.valueOf(Integer.parseInt(secondRowList.get(y)) + Integer.parseInt(secondRowList.get(y + 1))));
			}
		}

		int firstFlag = 0;
		int secondFlag = 0;

		String html = "";
		if (list.size() > 0) {
			html = html + "<tr>" + "<td rowspan = '" + firstTable.get(0) + "'> " + list.get(0).getAssessContent() + "</td>" + "<td rowspan = '" + secondTable.get(0) + "'> "
					+ list.get(0).getAssessNorm() + "</td>" + "<td> " + list.get(0).getScoreExplain() + "</td>" + "<td SRID='"+list.get(0).getId()+"'> " + list.get(0).getDescription() + "</td>" + "</tr>";
			for (int i = 1; i < list.size(); i++) {
				if (i + 1 > Integer.parseInt(secondRowList.get(secondFlag)) && i + 1 <= Integer.parseInt(firstRowList.get(firstFlag))) {
					secondFlag++;
					html = html + "<tr>" + "<td rowspan = '" + secondTable.get(secondFlag) + "'> " + list.get(i).getAssessNorm() + "</td>" + "<td> " + list.get(i).getScoreExplain() + "</td>" + "<td> "
							+ list.get(i).getDescription() + "</td>" + "</tr>";
				} else if (i + 1 > Integer.parseInt(secondRowList.get(secondFlag)) && i + 1 > Integer.parseInt(firstRowList.get(firstFlag))) {
					firstFlag++;
					secondFlag++;
					html = html + "<tr>" + "<td rowspan = '" + firstTable.get(firstFlag) + "'> " + list.get(i).getAssessContent() + "</td>" + "<td rowspan = '" + secondTable.get(secondFlag) + "'> "
							+ list.get(i).getAssessNorm() + "</td>" + "<td> " + list.get(i).getScoreExplain() + "</td>" + "<td> " + list.get(i).getDescription() + "</td>" + "</tr>";
				} else {
					html = html + "<tr>" + "<td> " + list.get(i).getScoreExplain() + "</td>" + "<td> " + list.get(i).getDescription() + "</td>" + "</tr>";
				}
			}

		}
		return html;
	}
	
	@Override
	public String modifyRules(String excelId) {
		ExcelAssess ea = new ExcelAssess();
		ea.setExcelId(excelId);
		List<ExcelAssess> list = excelAssessMapper.findExcelAssessListForOne(ea);
		List<String> firstRowList = excelAssessMapper.findFirstRowNum(ea);
		List<String> secondRowList = excelAssessMapper.findSecondRowNum(ea);
		List<String> firstTable = new ArrayList<String>(firstRowList);
		List<String> secondTable = new ArrayList<String>(secondRowList);
		for (int x = 0; x < firstRowList.size(); x++) {
			if (x + 1 != firstRowList.size()) {
				firstRowList.set(x + 1, String.valueOf(Integer.parseInt(firstRowList.get(x)) + Integer.parseInt(firstRowList.get(x + 1))));
			}
		}
		for (int y = 0; y < secondRowList.size(); y++) {
			if (y + 1 != secondRowList.size()) {
				secondRowList.set(y + 1, String.valueOf(Integer.parseInt(secondRowList.get(y)) + Integer.parseInt(secondRowList.get(y + 1))));
			}
		}

		int firstFlag = 0;
		int secondFlag = 0;
		String html = "";                                                 
		if (list.size() > 0) { 
			html = html + "<tr>" + "<td rowspan = '" + firstTable.get(0) + "'> <input cellsInfo='assessContent' dataInfo='"+list.get(0).getId()+"' value='"+list.get(0).getAssessContent()+"'/></td>" + "<td rowspan = '" + secondTable.get(0) + "'> "
					+ "<input cellsInfo='assessNorm' dataInfo='"+list.get(0).getId()+"' value='"+list.get(0).getAssessNorm()+"'/>" + "</td>" + "<td> " + "<input cellsInfo='scoreExplain' dataInfo='"+list.get(0).getId()+"' value='"+list.get(0).getScoreExplain()+"'/>"  + "</td>" + "<td> " + "<input cellsInfo='description' dataInfo='"+list.get(0).getId()+"' value='"+list.get(0).getDescription()+"'/>" + "</td>" + "</tr>";
			for (int i = 1; i < list.size(); i++) {
				if (i + 1 > Integer.parseInt(secondRowList.get(secondFlag)) && i + 1 <= Integer.parseInt(firstRowList.get(firstFlag))) {
					secondFlag++;
					html = html + "<tr>" + "<td rowspan = '" + secondTable.get(secondFlag) + "'> " + "<input cellsInfo='assessNorm' dataInfo='"+list.get(i).getId()+"' value='"+list.get(i).getAssessNorm() +"'/>" + "</td>" + "<td> " + "<input cellsInfo='scoreExplain' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getScoreExplain() +"'/>" + "</td>" + "<td> "
							+ "<input cellsInfo='description' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getDescription() +"'/>" + "</td>" + "</tr>";
				} else if (i + 1 > Integer.parseInt(secondRowList.get(secondFlag)) && i + 1 > Integer.parseInt(firstRowList.get(firstFlag))) {
					firstFlag++;
					secondFlag++;
					html = html + "<tr>" + "<td rowspan = '" + firstTable.get(firstFlag) + "'> " + "<input cellsInfo='assessContent' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getAssessContent() +"'/>" + "</td>" + "<td rowspan = '" + secondTable.get(secondFlag) + "'> "
							+ "<input cellsInfo='assessNorm' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getAssessNorm()  +"'/>" + "</td>" + "<td> " + "<input cellsInfo='scoreExplain' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getScoreExplain() +"'/>" + "</td>" + "<td> "+ "<input cellsInfo='description' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getDescription() +"'/>" + "</td>" + "</tr>";
				} else {
					html = html + "<tr>" + "<td> "+ "<input cellsInfo='scoreExplain' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getScoreExplain() +"'/>" + "</td>" + "<td> " + "<input cellsInfo='description' dataInfo='"+list.get(i).getId()+"' value='" + list.get(i).getDescription() +"'/>"+ "</td>" + "</tr>";
				}
			}

		}
		return html;
	}

	@Override
	public List<ExcelAssess> findAllList(ExcelAssess ea) {
		List<ExcelAssess> list = excelAssessMapper.findAllList(ea);
		return list;
	}

	@Override
	public int countList(ExcelAssess ea) {
		int count =  excelAssessMapper.countList(ea);
		return count;
	}

	@Override
	public ExcelAssess findOldAssessContent(String rowId) {
		ExcelAssess ea = excelAssessMapper.findOldAssessContent(rowId);
		return ea;
	}

	@Override
	public void updateAssessContent(ExcelAssess ea, String content) {
		ea.setAssessNorm(content);
		excelAssessMapper.updateAssessContent(ea);
	}

	@Override
	public ExcelAssess findOldAssessNorm(String rowId) {
		ExcelAssess ea = excelAssessMapper.findOldAssessNorm(rowId);
		return ea;
	}

	@Override
	public void updateAssessNorm(ExcelAssess ea, String content) {
		ea.setAssessContent(content);
		excelAssessMapper.updateAssessNorm(ea);
	}

	@Override
	public void updateScoreExplain(ExcelColumn ec) {
		excelAssessMapper.updateScoreExplain(ec);		
	}
	
	@Override
	public void updateExplain(ExcelColumn ec) {
		excelAssessMapper.updateExplain(ec);
	}

	@Override
	public void deleteExcel(String excelId) {
		excelAssessMapper.deleteExcel(excelId);
		excelAssessMapper.deleteExcelDetail(excelId);
	}

	@Override
	public void confirmTemp(String excelId) {
		excelAssessMapper.confirmTemp(excelId);
	}


}
