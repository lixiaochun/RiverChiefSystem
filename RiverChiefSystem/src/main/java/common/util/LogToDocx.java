package common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import common.model.Event;
import common.model.EventFile;
import common.model.PatrolRecord;
import common.model.RiverLog;

public class LogToDocx {

	/**
	 * @return stencil
	 */
	public static List<RiverLog> setStencil() {
		List<RiverLog> riverLogs = new ArrayList<RiverLog>();
		RiverLog riverLog1 = new RiverLog();
		List<String> detail1 = new ArrayList<>();
		riverLog1.setDetail(detail1);
		riverLog1.setTf(false);
		riverLog1.setItem("河道有无垃圾，是否存在倾倒垃圾、废土弃渣、工业固废等，河面、河岸保洁是否到位");
		riverLogs.add(riverLog1);
		RiverLog riverLog2 = new RiverLog();
		List<String> detail2 = new ArrayList<>();
		riverLog2.setDetail(detail2);
		riverLog2.setTf(false);
		riverLog2.setItem("河中有无障碍、河床是否存在明显淤塞、河底是否存在明显淤泥");
		riverLogs.add(riverLog2);
		RiverLog riverLog3 = new RiverLog();
		List<String> detail3 = new ArrayList<>();
		riverLog3.setDetail(detail3);
		riverLog3.setTf(false);
		riverLog3.setItem("河岸有无违章，是否存在涉水违法建筑物、违章搭盖、擅自围堰、填堵河道、以及其他侵占河道的行为；是否存在破坏涉水工程的行为（破坏、侵占、毁坏堤防、水库、护岸等，擅自在堤防、大坝管护范围内进行爆破、打井、采砂、挖石、修坟等）");
		riverLogs.add(riverLog3);
		RiverLog riverLog4 = new RiverLog();
		List<String> detail4 = new ArrayList<>();
		riverLog4.setDetail(detail4);
		riverLog4.setTf(false);
		riverLog4.setItem("河水是否存在异常，水体是否有发黑、发黄、发白、发臭等现象");
		riverLogs.add(riverLog4);
		RiverLog riverLog5 = new RiverLog();
		List<String> detail5 = new ArrayList<>();
		riverLog5.setDetail(detail5);
		riverLog5.setTf(false);
		riverLog5.setItem("污水排放有无违规，是否存在异常情况；是否有违法新增入河排污口");
		riverLogs.add(riverLog5);
		RiverLog riverLog6 = new RiverLog();
		List<String> detail6 = new ArrayList<>();
		riverLog6.setDetail(detail6);
		riverLog6.setTf(false);
		riverLog6.setItem("水生态有无破坏，是否存在电、毒、炸鱼，以及违法砍伐林木等破坏水生态环境的行为；水电站是否落实生态下泄流量");
		riverLogs.add(riverLog6);
		RiverLog riverLog7 = new RiverLog();
		List<String> detail7 = new ArrayList<>();
		riverLog7.setDetail(detail7);
		riverLog7.setTf(false);
		riverLog7.setItem("告示牌设置是否规范，河长公示牌、水源地保护区公示牌是否存在倾斜、破损、变形、变色、老化等影响使用的问题");
		riverLogs.add(riverLog7);
		RiverLog riverLog8 = new RiverLog();
		List<String> detail8 = new ArrayList<>();
		riverLog8.setDetail(detail8);
		riverLog8.setTf(false);
		riverLog8.setItem("上次巡查发现的问题是否解决到位");
		riverLogs.add(riverLog8);
		RiverLog riverLog9 = new RiverLog();
		List<String> detail9 = new ArrayList<>();
		riverLog9.setDetail(detail9);
		riverLog9.setTf(false);
		riverLog9.setItem("是否存在其他影响水安全、水生态、水环境的问题");
		riverLogs.add(riverLog9);
		return riverLogs;
	}

	public static int getWord(Map<String, String> map, List<PatrolRecord> list, HttpServletResponse response) throws Exception {
		XWPFDocument xdoc = new XWPFDocument();
		for (int k = 0; k < list.size(); k++) {
			PatrolRecord pr = list.get(k);
			// 标题
			XWPFParagraph titleMes = xdoc.createParagraph();
			titleMes.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r1 = titleMes.createRun();
			r1.setBold(true);
			r1.setFontFamily("宋体");
			r1.setText("河长巡查日志");// 活动名称
			r1.setFontSize(24);
			r1.setColor("000000");
			r1.setBold(true);
			List<RiverLog> riverLogs = setStencil();
			List<String> problems = new ArrayList<>();
			List<Event> eventList = pr.getEventList();
			int j = 1;
			if (eventList != null && eventList.size() > 0) {
				for (Event event : eventList) {
					int i = Integer.parseInt(event.getProblemType().substring(1, 2));
					riverLogs.get(i + 1).setTf(true);
					List<String> detail = riverLogs.get(i + 1).getDetail();
					detail.add(event.getEventContent());
					riverLogs.get(i + 1).setDetail(detail);
					String problem = "事件" + (j++) + ":" + event.getEventContent() + "  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEventTime());
					problems.add(problem);
				}
			}

			// 表格
			XWPFTable xTable = xdoc.createTable(8 + riverLogs.size(), 4);
			createSimpleTable(xTable, xdoc, pr, riverLogs, problems);

			if ((k + 1) < list.size()) {
				// 换页
				XWPFParagraph p = xdoc.createParagraph();
				p.setPageBreak(true);
			}

		}
		// setEmptyRow(xdoc, r1);
		// 在服务器端生成
		File file = new File(map.get("path").toString());
		FileOutputStream fos = new FileOutputStream(file);
		xdoc.write(fos);
		InputStream i = new BufferedInputStream(new FileInputStream(file));
		String filename = map.get("filename").toString();
		filename = URLEncoder.encode(filename, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		response.addHeader("Content-Length", String.valueOf(file.length()));
		response.setContentType("multipart/form-data");
		BufferedOutputStream o = new BufferedOutputStream(response.getOutputStream());

		byte buffer[] = new byte[1024];
		int len = 0;

		while ((len = i.read(buffer)) > 0) {
			o.write(buffer, 0, len);
		}

		o.close();
		i.close();
		fos.close();
		return 1;
	}

	public static int getWordByEvent(Map<String, String> map, List<Event> list, HttpServletResponse response) throws Exception {
		Constant constant = new Constant();
		CustomXWPFDocument document = new CustomXWPFDocument();
		for (int k = 0; k < list.size(); k++) {
			Event event = list.get(k);
			// XWPFDocument xdoc = new XWPFDocument();
			// 标题
			XWPFParagraph titleMes = document.createParagraph();
			titleMes.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r1 = titleMes.createRun();
			r1.setBold(true);
			r1.setFontFamily("宋体");
			r1.setText("问题事件");// 活动名称
			r1.setFontSize(24);
			r1.setColor("000000");
			r1.setBold(true);

			// 表格
			XWPFTable xTable = document.createTable(5, 4);
			createSimpleTableByEvent(xTable, document, event);

			// 插入图片
			List<XWPFTableRow> rows = xTable.getRows();// 取得表格的行
			List<XWPFTableCell> cells = rows.get(4).getTableCells();
			if (event.getEventFileList() != null && event.getEventFileList().size() > 0) {
				String path = constant.getProperty("path");
				System.out.println("地址：  " + path);
				for (EventFile eventFile : event.getEventFileList()) {
					if (eventFile.getType().equals("image")) {
						File pic = new File(path + eventFile.getFileUrl());
						System.out.println("地址：  " + path + eventFile.getFileUrl());
						System.out.println("图片是否存在" + pic.exists());
						if (pic.exists()) {
							BufferedImage sourceImg = ImageIO.read(new FileInputStream(pic));
							int height = sourceImg.getHeight() * 400 / sourceImg.getWidth();
							FileInputStream is = new FileInputStream(pic);
							CTP ctp = CTP.Factory.newInstance();
							XWPFParagraph p = cells.get(1).addParagraph();
							document.addPictureData(is, XWPFDocument.PICTURE_TYPE_JPEG);
							document.createPicture(p, document.getAllPictures().size() - 1, 400, height, "");
							if (is != null) {
								is.close();
							}
						}
					}
				}
			}
			if ((k + 1) < list.size()) {
				// 换页
				XWPFParagraph p = document.createParagraph();
				p.setPageBreak(true);
			}

		}

		// setEmptyRow(xdoc, r1);
		// 在服务器端生成
		File file = new File(map.get("path").toString());
		FileOutputStream fos = new FileOutputStream(file);
		document.write(fos);
		InputStream i = new BufferedInputStream(new FileInputStream(file));
		String filename = map.get("filename").toString();
		filename = URLEncoder.encode(filename, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		response.addHeader("Content-Length", String.valueOf(file.length()));
		response.setContentType("multipart/form-data");
		BufferedOutputStream o = new BufferedOutputStream(response.getOutputStream());
		byte buffer[] = new byte[1024];
		int len = 0;

		while ((len = i.read(buffer)) > 0) {
			o.write(buffer, 0, len);
		}

		o.close();
		i.close();
		fos.close();
		return 1;
	}

	// 设置表格高度
	private static XWPFTableCell getCellHight(XWPFTable xTable, int rowNomber, int cellNumber, int height) {
		XWPFTableRow row = null;
		row = xTable.getRow(rowNomber);
		row.setHeight(height);
		XWPFTableCell cell = null;
		cell = row.getCell(cellNumber);
		return cell;
	}

	/**
	 * @param xDocument
	 * @param cell
	 * @param text
	 * @param width
	 */
	private static void setCellText(XWPFDocument xDocument, XWPFTableCell cell, String text, int size, int width, boolean pcenter) {
		CTP ctp = CTP.Factory.newInstance();
		XWPFParagraph p = new XWPFParagraph(ctp, cell);
		if (pcenter) {
			p.setAlignment(ParagraphAlignment.CENTER);
		} else {
			p.setAlignment(ParagraphAlignment.LEFT);
		}

		XWPFRun run = p.createRun();
		run.setColor("000000");
		run.setFontSize(size);
		run.setText(text);

		CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("仿宋");
		fonts.setEastAsia("仿宋");
		fonts.setHAnsi("仿宋");
		cell.setParagraph(p);
		cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		CTTcPr tcpr = cell.getCTTc().addNewTcPr();
		CTTblWidth cellw = tcpr.addNewTcW();
		cellw.setType(STTblWidth.DXA);
		cellw.setW(BigInteger.valueOf(width));
	}

	private static void setBodyCellText(XWPFDocument xDocument, XWPFTableCell cell, List<String> list, int width) {
		CTP ctp = CTP.Factory.newInstance();
		XWPFParagraph p = new XWPFParagraph(ctp, cell);
		p.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run = p.createRun();
		run.setColor("000000");
		run.setFontSize(10);
		for (int i = 0; i < list.size(); i++) {
			run.setText(list.get(i));
			if (i == list.size() - 1)
				break;
			run.addBreak();
		}
		CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");
		cell.setParagraph(p);
		cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		CTTcPr tcpr = cell.getCTTc().addNewTcPr();
		CTTblWidth cellw = tcpr.addNewTcW();
		cellw.setType(STTblWidth.DXA);
		cellw.setW(BigInteger.valueOf(width));
	}

	// 设置表格间的空行
	public static void setEmptyRow(XWPFDocument xdoc, XWPFRun r1) {
		XWPFParagraph p1 = xdoc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		p1.setVerticalAlignment(TextAlignment.CENTER);
		r1 = p1.createRun();
	}

	/**
	 * 创建表 事件问题
	 * 
	 * @param xTable
	 * @param xdoc
	 * @throws IOException
	 */
	public static void createSimpleTableByEvent(XWPFTable xTable, XWPFDocument xdoc, Event event) throws IOException {
		String bgColor = "FFFFFF";
		CTTbl ttbl = xTable.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		tblWidth.setW(new BigInteger("8300"));
		tblWidth.setType(STTblWidth.DXA);
		// mergeCellHorizontally(xTable, 0, 1, 3);//从第0行的第一个到第三个单元格合并
		mergeCellHorizontally(xTable, 1, 1, 3);
		mergeCellHorizontally(xTable, 2, 1, 3);
		mergeCellHorizontally(xTable, 3, 1, 3);
		mergeCellHorizontally(xTable, 4, 1, 3);
		setCellText(xdoc, getCellHight(xTable, 0, 0, 450), "河流名称", 16, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 0, 1, 450), event.getRiverName(), 16, 360 * 11, true);
		setCellText(xdoc, getCellHight(xTable, 0, 2, 450), "上报时间", 16, 360 * 5, true);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		setCellText(xdoc, getCellHight(xTable, 0, 3, 450), dateFormat.format(event.getEventTime()), 16, 360 * 11, true);
		setCellText(xdoc, getCellHight(xTable, 1, 0, 450), "问题情况", 16, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 1, 1, 450), event.getEventContent(), 16, 360 * 11, true);
		setCellText(xdoc, getCellHight(xTable, 2, 0, 450), "地点", 16, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 2, 1, 450), "", 16, 360 * 11, true);
		setCellText(xdoc, getCellHight(xTable, 3, 0, 450), "当前处理流程", 16, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 3, 1, 450), "交由" + event.getNowRealName() + "处理", 16, 360 * 11, true);
		setCellText(xdoc, getCellHight(xTable, 4, 0, 450), "具体情况", 16, 360 * 5, true);
	}

	/**
	 * 创建表 巡河日志导出
	 * 
	 * @param xTable
	 * @param xdoc
	 * @throws IOException
	 */
	public static void createSimpleTable(XWPFTable xTable, XWPFDocument xdoc, PatrolRecord patrolRecord, List<RiverLog> riverLogs, List<String> problems) throws IOException {
		String bgColor = "FFFFFF";
		CTTbl ttbl = xTable.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		tblWidth.setW(new BigInteger("8300"));
		tblWidth.setType(STTblWidth.DXA);
		mergeCellHorizontally(xTable, 0, 1, 3);
		mergeCellHorizontally(xTable, 2, 1, 3);
		mergeCellHorizontally(xTable, 3, 1, 3);
		mergeCellHorizontally(xTable, 1, 1, 3);
		mergeCellHorizontally(xTable, 4, 0, 2);
		setCellText(xdoc, getCellHight(xTable, 0, 0, 432), "河流名称", 14, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 0, 1, 432), patrolRecord.getRiverName(), 12, 360 * 11, false);
		setCellText(xdoc, getCellHight(xTable, 1, 0, 432), "巡查范围", 14, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 1, 1, 432), "", 12, 360 * 11, false);
		setCellText(xdoc, getCellHight(xTable, 2, 0, 432), "巡查人员", 14, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 2, 1, 432), patrolRecord.getUserName(), 12, 360 * 11, false);
		setCellText(xdoc, getCellHight(xTable, 3, 0, 432), "起止时间", 14, 360 * 5, true);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		setCellText(xdoc, getCellHight(xTable, 3, 1, 432), dateFormat.format(patrolRecord.getStartTime()) + "至" + dateFormat.format(patrolRecord.getEndTime()), 12, 360 * 11, false);
		setCellText(xdoc, getCellHight(xTable, 4, 0, 432), "巡查项目", 14, 360 * 11, true);
		setCellText(xdoc, getCellHight(xTable, 4, 3, 432), "具体情况", 14, 360 * 6, true);

		for (int i = 0; i < riverLogs.size(); i++) {
			mergeCellHorizontally(xTable, 5 + i, 0, 1);
			setCellText(xdoc, getCellHight(xTable, 5 + i, 0, 200), riverLogs.get(i).getItem(), 10, 9 * 360, false);
			if (riverLogs.get(i).isTf()) {
				setCellText(xdoc, getCellHight(xTable, 5 + i, 2, 200), "是", 12, 2 * 360, true);
			} else {
				setCellText(xdoc, getCellHight(xTable, 5 + i, 2, 200), "否", 12, 2 * 360, true);
			}
			setBodyCellText(xdoc, getCellHight(xTable, 5 + i, 3, 200), riverLogs.get(i).getDetail(), 6 * 360);
		}

		mergeCellHorizontally(xTable, 5 + riverLogs.size(), 1, 3);
		mergeCellHorizontally(xTable, 6 + riverLogs.size(), 1, 3);
		mergeCellHorizontally(xTable, 7 + riverLogs.size(), 1, 3);
		setCellText(xdoc, getCellHight(xTable, 5 + riverLogs.size(), 0, 360 * 3), "问题情况", 14, 360 * 5, true);
		setBodyCellText(xdoc, getCellHight(xTable, 5 + riverLogs.size(), 1, 360 * 3), problems, 9 * 360);
		setCellText(xdoc, getCellHight(xTable, 6 + riverLogs.size(), 0, 360 * 3), "处理过程及结果", 14, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 7 + riverLogs.size(), 0, 360 * 2), "河长签字", 14, 360 * 5, true);
		setCellText(xdoc, getCellHight(xTable, 6 + riverLogs.size(), 1, 360 * 3), "", 10, 9 * 360, false);
		setCellText(xdoc, getCellHight(xTable, 7 + riverLogs.size(), 1, 360 * 2), "", 10, 9 * 360, false);
	}

	// 列合并
	static void mergeCellVertically(XWPFTable table, int col, int fromRow, int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			CTVMerge vmerge = CTVMerge.Factory.newInstance();
			if (rowIndex == fromRow) {
				// The first merged cell is set with RESTART merge value
				vmerge.setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				vmerge.setVal(STMerge.CONTINUE);
			}
			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			// Try getting the TcPr. Not simply setting an new one every time.
			CTTcPr tcPr = cell.getCTTc().getTcPr();
			if (tcPr != null) {
				tcPr.setVMerge(vmerge);
			} else {
				// only set an new TcPr if there is not one already
				tcPr = CTTcPr.Factory.newInstance();
				tcPr.setVMerge(vmerge);
				cell.getCTTc().setTcPr(tcPr);
			}
		}
	}

	// 行合并
	static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
		for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
			CTHMerge hmerge = CTHMerge.Factory.newInstance();
			if (colIndex == fromCol) {
				// The first merged cell is set with RESTART merge value
				hmerge.setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				hmerge.setVal(STMerge.CONTINUE);
			}
			XWPFTableCell cell = table.getRow(row).getCell(colIndex);
			// Try getting the TcPr. Not simply setting an new one every time.
			CTTcPr tcPr = cell.getCTTc().getTcPr();
			if (tcPr != null) {
				tcPr.setHMerge(hmerge);
			} else {
				// only set an new TcPr if there is not one already
				tcPr = CTTcPr.Factory.newInstance();
				tcPr.setHMerge(hmerge);
				cell.getCTTc().setTcPr(tcPr);
			}
		}
	}
}
