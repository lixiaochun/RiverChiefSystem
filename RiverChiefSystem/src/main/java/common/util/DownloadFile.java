/**
 *
 * @Title:downloadFile.java
 *
 * @Package:util
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月14日 下午5:08:30
 *
 * @version V1.0
 *
 */
package common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import common.model.Column;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DownloadFile {

	public void downloadii(String url, HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("utf-8");

		response.setContentType("multipart/form-data");

		String filename = url.substring(url.lastIndexOf("/") + 1);

		response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "UTF-8"));

		try {

			File file = new File(url);

			InputStream inputStream = new FileInputStream(file);

			OutputStream os = response.getOutputStream();

			byte[] b = new byte[1024];

			int length;

			while ((length = inputStream.read(b)) > 0) {

				os.write(b, 0, length);

			}

			inputStream.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Map<Object, Object> download(Map<Object, Object> map, HttpServletResponse reponse) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			File file = new File(map.get("savePath").toString());
			if (file.exists() && file.isFile()) {
				InputStream i = new BufferedInputStream(new FileInputStream(file));
				String filename = map.get("filename").toString();
				filename = URLEncoder.encode(filename, "UTF-8");
				reponse.addHeader("Content-Disposition", "attachment;filename=" + filename);
				reponse.addHeader("Content-Length", String.valueOf(file.length()));
				reponse.setContentType("multipart/form-data");
				BufferedOutputStream o = new BufferedOutputStream(reponse.getOutputStream());
				int len = 0;
				while ((len = i.read()) != -1) {
					o.write(len);
					o.flush();
				}
				o.close();
				i.close();
				result.put("result", 1);
				result.put("message", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "无该文件");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", 1);
			result.put("message", "操作异常");
		}
		return result;
	}

	/**
	 * 根据输入的内容创建一个表格 要求： 表头表格线为粗线，表体表格线为细线； 表头背景色为黄色且表头字体加粗居中显示，表体为无色；
	 * 表头以及表体内容可以按照一定的格式输入；
	 * 
	 * 保留一个sheet且sheet的背景为无网格线;
	 * 
	 * @param <T>
	 * 
	 * @return 创建成功：true；创建失败：false；
	 */
	public <T> boolean createTable(List<Column> list, List<T> objectlist, Map<Object, Object> map, HttpServletResponse response) {
		boolean createFlag = true;

		WritableWorkbook book;
		try {
			File file = new File(map.get("savePath").toString() + map.get("fileName").toString());
			// 根据路径生成excel文件
			book = Workbook.createWorkbook(file);
			// 创建一个sheet名为"表格"
			WritableSheet sheet = book.createSheet(map.get("tableName").toString(), 0);
			WritableSheet sheet1 = book.createSheet("规则", 1);

			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 设置NO列宽度
			// sheet.setColumnView(1, 5);
			// 去掉整个sheet中的网格线
			// sheet.getSettings().setShowGridLines(false);

			Label tempLabel = null;
			// 表头输出
			// String[] headerArr = header.split(",");
			// int headerLen = headerArr.length;

			// 合并单元格
			// 第一列第一行到第n列第一行
			sheet1.mergeCells(0, 0, list.size() - 1, 0);
			// 输出注释
			tempLabel = new Label(0, 0, map.get("detail").toString(), getHeaderCellStyle());
			sheet1.addCell(tempLabel);
			sheet1.setRowView(0, 1000);

			// 循环写入表头内容
			for (int i = 0; i < list.size(); i++) {
				sheet.setColumnView(i, cellView);
				// 设置NO列宽度
				tempLabel = new Label(i, 0, list.get(i).getColumnComment() + "\n" + "(" + list.get(i).getColumnName() + ")", getHeaderCellStyle());
				sheet.addCell(tempLabel);
			}
			if (objectlist != null&&objectlist.size()>0) {
				// 表体输出
				int bodyLen = objectlist.size();
				try {
					// 反射机制运行类

					// 循环写入表体内容
					for (int j = 0; j < bodyLen; j++) {
						Class cls = objectlist.get(j).getClass();
						Object object = cls.newInstance();
						object = objectlist.get(j);
						for (int i = 0; i < list.size(); i++) {
							String str = "get" + list.get(i).getColumnName().substring(0, 1).toUpperCase() + list.get(i).getColumnName().substring(1, list.get(i).getColumnName().length());
							Method getMethod = cls.getDeclaredMethod(str);

							WritableCellFormat tempCellFormat = null;
							/*
							 * 表体内容的对齐设置 这里将序号NO以及年龄居中对齐，姓名以及性别默认对齐方式
							 */
							tempCellFormat = getBodyCellStyle();
							if (tempCellFormat != null) {
								tempCellFormat.setAlignment(Alignment.CENTRE);
							}
							if (getMethod.invoke(object) != null) {
								tempLabel = new Label(i, j + 1, getMethod.invoke(object).toString(), tempCellFormat);
							} else {
								tempLabel = new Label(i, j + 1, null, tempCellFormat);
							}

							sheet.addCell(tempLabel);
						}

						String[] bodyTempArr = objectlist.get(j).toString().split(",");
						for (int k = 0; k < bodyTempArr.length; k++) {

						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			book.write();
			book.close();
			InputStream i = new BufferedInputStream(new FileInputStream(file));
			String filename = map.get("fileName").toString();
			filename = URLEncoder.encode(filename, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.addHeader("Content-Length", String.valueOf(file.length()));
			response.setContentType("multipart/form-data");
			BufferedOutputStream o = new BufferedOutputStream(response.getOutputStream());
			int len = 0;
			while ((len = i.read()) != -1) {
				o.write(len);
				o.flush();
			}
			o.close();
			i.close();
		} catch (IOException e) {
			createFlag = false;
			System.out.println("EXCEL创建失败！");
			e.printStackTrace();

		} catch (RowsExceededException e) {
			createFlag = false;
			System.out.println("EXCEL单元设置创建失败！");
			e.printStackTrace();
		} catch (WriteException e) {
			createFlag = false;
			System.out.println("EXCEL写入失败！");
			e.printStackTrace();
		}

		return createFlag;

	}

	/**
	 * 表头单元格样式的设定
	 */
	public WritableCellFormat getHeaderCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.BOLD:设置字体加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat headerFormat = new WritableCellFormat(NumberFormats.TEXT);
		try {
			// 添加字体设置
			headerFormat.setFont(font);
			// 设置单元格背景色：表头为黄色
			headerFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为粗线、黑色
			headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 表头内容水平居中显示
			headerFormat.setAlignment(Alignment.CENTRE);
			headerFormat.setWrap(true);
			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			System.out.println("表头单元格样式设置失败！");
		}
		return headerFormat;
	}

	/**
	 * 表头单元格样式的设定
	 */
	public WritableCellFormat getBodyCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			bodyFormat.setWrap(true);
			bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}
}
