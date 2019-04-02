package workmanage.service.serviceImpl;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.Event;
import common.model.FdStatistical;
import common.model.Patrol;
import common.model.PatrolRecord;
import common.util.CalendarUtil;
import common.util.LogToDocx;
import common.util.ValidateUtil;
import usermanage.mapper.DateInfoMapper;
import workmanage.mapper.EventMapper;
import workmanage.mapper.PatrolMapper;
import workmanage.mapper.PatrolRecordMapper;
import workmanage.service.PatrolRecordService;

@Service("PatrolRecordService")
public class PatrolRecordServiceImpl implements PatrolRecordService {

	@Autowired
	private PatrolRecordMapper patrolRecordMapper;

	@Autowired
	private PatrolMapper patrolMapper;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private DateInfoMapper dateInfoMapper;

	private LogToDocx logToDocx;

	public Map<Object, Object> queryPatrolRecord(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		map.put("recordStatus", "1");
		List<PatrolRecord> list = patrolRecordMapper.queryPatrolRecord(map);
		int count = patrolRecordMapper.countPatrolRecord(map);
		for (int i = 0; i < list.size(); i++) {
			int solve = 0;// 已办结
			int toSolve = 0;// 待办结
			List<Event> eventlist = list.get(i).getEventList();
			for (Event event : eventlist) {
				if (event.getEventType().equals("4")) {
					solve++;
				} else {
					toSolve++;
				}
			}
			list.get(i).setSolve(solve);
			list.get(i).setToSolve(toSolve);
		}

		result.put("PatrolRecord", list);
		result.put("result", 1);
		result.put("countPatrolRecord", count);
		return result;
	}

	public Map<Object, Object> deletePatrolRecordByPatrolId(int patrolRecordId) {
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			query.put("patrolRecordId", patrolRecordId);
			query.put("recordStatus", "1,2");
			List<PatrolRecord> list = patrolRecordMapper.queryPatrolrecord(query);
			if (list != null && list.size() > 0) {
				patrolRecordMapper.deletePatrolRecordByPatrolId(patrolRecordId);
				result.put("result", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}

		return result;
	}

	public Map<Object, Object> insertPatrolRecord(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Map<Object, Object> search = new HashMap<Object, Object>();
			String patrolId = map.get("patrolId").toString();
			search.put("patrolId", patrolId);
			List<Patrol> patrolList = patrolMapper.queryPatrolByStatus(search);
			if ((patrolList != null && patrolList.size() > 0) || patrolId.equals("0")) {
				PatrolRecord patrolRecord = new PatrolRecord();
				patrolRecord.setPatrolId(Integer.parseInt(patrolId));
				patrolRecord.setUserId(Integer.parseInt(map.get("userId").toString()));
				patrolRecord.setSubmitTime(new Timestamp(new Date().getTime()));
				patrolRecord.setRecordStatus("2");// 新增状态
				patrolRecord.setRiverId(map.get("riverId").toString());
				List<String> msg = ValidateUtil.BeanValidate(patrolRecord);
				if (msg.size() > 0) {
					result.put("result", 0);
					result.put("message", msg);
					return result;
				}
				patrolRecordMapper.insertPatrolRecord(patrolRecord);
				result.put("patrolRecordId", patrolRecord.getPatrolRecordId());
				result.put("result", 1);
				result.put("message", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "查无此巡查任务");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public String stringArrayToString(String str) {
		String[] sourceStrArray = str.split(",");
		String point = "";
		for (int i = 0; i < sourceStrArray.length; i = i + 2) {
			point += "[" + sourceStrArray[i] + "," + sourceStrArray[i + 1] + "];";
		}
		return point;
	}

	public List<List<String>> stringToStringArray(String str) {
		String x, y;
		List<List<String>> result = new ArrayList<List<String>>();
		String[] sourceStrArray = str.split(";");
		for (int i = 0; i < sourceStrArray.length; i++) {
			String[] strArray = sourceStrArray[i].split(",");

			for (int j = 0; j < strArray.length; j = j + 2) {
				List<String> map = new ArrayList<String>();
				x = strArray[j].substring(1);
				y = strArray[j + 1].substring(0, strArray[j + 1].length() - 1);
				map.add(x);
				map.add(y);
				result.add(map);
			}
		}
		return result;
	}

	public List<List<Double>> stringToDoubleArray(String str) {
		Double x, y;
		List<List<Double>> result = new ArrayList<List<Double>>();
		String[] sourceStrArray = str.split(";");
		for (int i = 0; i < sourceStrArray.length; i++) {
			String[] strArray = sourceStrArray[i].split(",");

			for (int j = 0; j < strArray.length; j = j + 2) {
				List<Double> map = new ArrayList<Double>();
				x = Double.parseDouble(strArray[j].substring(1));
				y = Double.parseDouble(strArray[j + 1].substring(0, strArray[j + 1].length() - 1));
				map.add(x);
				map.add(y);
				result.add(map);
			}
		}
		return result;
	}

	public Map<Object, Object> queryPointFromPatrolRecord(String patrolRecordId) {
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			query.put("patrolRecordId", patrolRecordId);
			query.put("recordStatus", "1");
			List<PatrolRecord> list = patrolRecordMapper.queryPatrolRecord(query);
			for (int i = 0; i < list.size(); i++) {
				int solve = 0;// 已办结
				int toSolve = 0;// 待办结
				List<Event> eventlist = list.get(i).getEventList();
				for (Event event : eventlist) {
					if (event.getEventType().equals("4")) {
						solve++;
					} else {
						toSolve++;
					}
				}
				list.get(i).setSolve(solve);
				list.get(i).setToSolve(toSolve);
			}
			String point = new String(list.get(0).getPoint());
			if (point != null && !(point.equals(""))) {
				result.put("point", stringToDoubleArray(point));
			} else {
				result.put("point", null);
			}
			result.put("PatrolRecord", list);
			result.put("result", 1);
			result.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作失败");
		}
		return result;
	}

	public Map<Object, Object> statisticalMileageAndTime(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			map.put("recordStatus", 1);
			map.put("appType", 1);
			List<PatrolRecord> list = patrolRecordMapper.queryPatrolrecord(map);
			Float totalMileage = (float) 0;
			String totalTime = "";
			int hours = 0;
			int minutes = 0;
			int seconds = 0;
			for (PatrolRecord patrolRecord : list) {
				String time = patrolRecord.getTotalTime();
				String[] times = time.split(":");
				totalMileage += Float.parseFloat(patrolRecord.getTotalMileage());
				hours += Integer.parseInt(times[0]);
				minutes += Integer.parseInt(times[1]);
				seconds += Integer.parseInt(times[2]);
			}
			minutes += seconds / 60;
			seconds = seconds % 60;
			hours += minutes / 60;
			minutes = minutes % 60;
			if (hours < 10) {
				totalTime = "0";
			}
			totalTime = totalTime + hours + ":";
			if (minutes < 10) {
				totalTime += "0";
			}
			totalTime = totalTime + minutes + ":";
			if (seconds < 10) {
				totalTime += "0";
			}
			totalTime = totalTime + seconds;
			result.put("totalTime", totalTime);
			result.put("totalMileage", totalMileage);
			result.put("result", 1);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	@Override
	public Map<Object, Object> updatePatrolRecord(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			search.put("patrolRecordId", map.get("patrolRecordId"));
			search.put("recordStatus", "2");
			List<PatrolRecord> patrolRecordList = patrolRecordMapper.queryPatrolrecord(search);
			if (patrolRecordList != null && patrolRecordList.size() > 0) {
				map.put("startTime", (new Timestamp(Long.parseLong(map.get("startTime").toString()))));
				map.put("endTime", (new Timestamp(Long.parseLong(map.get("endTime").toString()))));
				map.put("point", (map.get("point").toString().getBytes()));
				// map.put("recordName", map.get("riverName"));
				map.put("riverId", map.get("riverId"));
				Float totalMileage = Float.parseFloat(map.get("totalMileage").toString());
				totalMileage = ((float) Math.round(totalMileage * 100) / 100);
				String totalTime = map.get("totalTime").toString();
				map.put("totalTime", totalTime);
				map.put("totalMileage", totalMileage);
				map.put("recordStatus", 1);
				map.put("updateTime", new Date());
				patrolRecordMapper.updatePatrolRecord(map);
				result.put("message", "操作成功");
				result.put("result", 1);
			} else {
				result.put("message", "查无该数据");
				result.put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", "操作异常");
			result.put("result", 0);
		}
		return result;
	}

	@Override
	public Map<Object, Object> exportPatrolRecord(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<String, String> mappath = new HashMap<String, String>();
		map.put("recordStatus", "1");
		List<PatrolRecord> list = patrolRecordMapper.queryPatrolrecord(map);
		int count = patrolRecordMapper.countPatrolRecord(map);
		if (list != null && list.size() > 0) {
			try {
				String filename = map.get("month").toString() + "巡查日志报告" + new Date().getTime() + ".docx";
				String path = map.get("path").toString() + filename;
				mappath.put("filename", filename);
				mappath.put("path", path);
				logToDocx.getWord(mappath, list, response);
				result.put("PatrolRecord", list);
				result.put("result", 1);
				result.put("countPatrolRecord", count);
				result.put("message", "操作成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.put("result", 0);
				result.put("message", "操作异常");
			}
		} else {
			result.put("result", 0);
			result.put("message", "查无数据");
		}
		return result;
	}

	@Override
	public Map<Object, Object> deletePatrolRecord(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		map.put("recordStatus", "2");
		patrolRecordMapper.deletePatrolRecord(map);
		result.put("message", "操作成功");
		result.put("result", 1);
		return result;
	}

	@Override
	public Map<Object, Object> exportPatrolRecordByUser(Map<Object, Object> map, HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			map.put("level", 4);
			map.put("regionType", 1);
			// 查询县级行政区
			List<FdStatistical> list = patrolRecordMapper.queryRegion(map);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String strdate = "";
			Date date = new Date();
			if (map.get("date") != null) {
				strdate = map.get("date").toString() + "-01";
				date = df.parse(strdate);
			}
			List<String> dates = CalendarUtil.dayReport(date);
			if (list != null && list.size() > 0) {

				String url = request.getSession().getServletContext().getRealPath("/");
				File excel = new File(url + "/template/statisticalExcel/福鼎巡河记录统计.xlsx");
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excel);
				SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 100);
				Sheet sheet = xssfWorkbook.getSheetAt(0);
				Row row = (Row) sheet.getRow(0);
				row.createCell(0).setCellValue("福鼎市河道专管员巡河统计表(" + map.get("date").toString() + ")");
				int rownum = 2;
				int obNum = 1;
				int patrolcount = 0;// 任务数
				int recordcount = 0;// 巡查次数
				int tocount = 0;// 缺勤次数
				for (FdStatistical fdStatistical : list) {
					map.put("regionId", fdStatistical.getRegionId());
					map.put("dates", dates);
					map.put("count", dates.size());
					List<PatrolRecord> patrolRecordlist = patrolRecordMapper.staticPatrolRecord(map);
					// excel创建第二行
					row = (Row) sheet.createRow(rownum);
					obNum = 1;
					// 合并镇级单元格
					sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + patrolRecordlist.size(), 0, 0));
					row.createCell(0).setCellValue(fdStatistical.getRegionName());
					if (patrolRecordlist != null && patrolRecordlist.size() > 0) {
						int pcount = 0;
						int rcount = 0;
						int tcount = 0;
						for (int i = 0; i < patrolRecordlist.size(); i++) {
							if (i > 0) {
								row = (Row) sheet.createRow(rownum);
							}
							obNum = 1;
							if (patrolRecordlist.get(i).getToSolve() > 0) {
								map.put("userId", patrolRecordlist.get(i).getUserId());
								List<String> after = new ArrayList<>();
								after.addAll(dates);
								List<String> before = patrolRecordMapper.staticPatrolRecordMonth(map);
								after.removeAll(before);
								String remark = "";
								boolean first = true;
								if (after != null) {
									for (String aa : after) {
										if (first) {
											first = false;
										} else {
											remark = remark + ",";
										}
										remark = remark + aa;
									}
								}
								patrolRecordlist.get(i).setRemark(remark);
							}
							row.createCell(obNum).setCellValue(patrolRecordlist.get(i).getRealName());
							row.createCell(++obNum).setCellValue(dates.size());
							row.createCell(++obNum).setCellValue(patrolRecordlist.get(i).getSolve());
							row.createCell(++obNum).setCellValue(patrolRecordlist.get(i).getToSolve());
							row.createCell(++obNum).setCellValue(patrolRecordlist.get(i).getRemark());
							pcount += dates.size();
							rcount += patrolRecordlist.get(i).getSolve();
							tcount += patrolRecordlist.get(i).getToSolve();
							rownum++;
						}
						row = (Row) sheet.createRow(rownum);
						row.createCell(1).setCellValue("合计");
						row.createCell(2).setCellValue(pcount);
						row.createCell(3).setCellValue(rcount);
						row.createCell(4).setCellValue(tcount);
						patrolcount += pcount;
						recordcount += rcount;
						tocount += tcount;
						rownum++;
					}
				}
				row = (Row) sheet.createRow(rownum);
				row.createCell(0).setCellValue("总计");
				row.createCell(2).setCellValue(patrolcount);
				row.createCell(3).setCellValue(recordcount);
				row.createCell(4).setCellValue(tocount);
				OutputStream out = null;
				// 文件名应该编码成UTF-8
				String filename = URLEncoder.encode("福鼎巡河记录统计" + map.get("date").toString() + ".xlsx", "UTF-8");
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
				result.put("message", "操作成功");
				result.put("result", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", "操作异常");
			result.put("result", 0);
		}

		return result;
	}

	@Override
	public Map<Object, Object> staticalPatrolRecordByUser(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = patrolRecordMapper.staticalPatrolRecordByUser(map);// 统计巡河次数及周期
			result.put("result", 1);
			result.put("staticalPatrol", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

}
