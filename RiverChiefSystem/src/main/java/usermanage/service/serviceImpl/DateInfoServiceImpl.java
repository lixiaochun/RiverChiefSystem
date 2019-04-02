package usermanage.service.serviceImpl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Column;
import common.model.Dict;
import common.model.Event;
import common.model.Organization;
import common.model.Region;
import common.model.River;
import common.model.Role;
import common.model.User;
import common.util.DownloadFile;
import common.util.ValidateUtil;
import jxl.Sheet;
import jxl.Workbook;
import usermanage.mapper.ColumnMapper;
import usermanage.mapper.DateInfoMapper;
import usermanage.mapper.OrganizationMapper;
import usermanage.mapper.RoleMapper;
import usermanage.mapper.UserMapper;
import usermanage.service.DateInfoService;
import workmanage.mapper.EventMapper;

@Service("DateInfoService")
@Transactional(readOnly = true)
public class DateInfoServiceImpl implements DateInfoService {

	@Autowired
	private DateInfoMapper dateInfoMapper;

	@Autowired
	private ColumnMapper columnMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private OrganizationMapper organizationMapper;

	DownloadFile downloadFile = new DownloadFile();

	public Map<Object, Object> exportExcel(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> searchmap = new HashMap<Object, Object>();
		try {
			// 查询organization对应的字段
			// int organizationId = organizationMapper.selectOrganizationId() + 1;
			String tableName = map.get("tableName").toString();
			List<Column> columnList = columnMapper.queryColumn(map);
			String fileName = tableName + ".xls";
			map.put("fileName", fileName);
			String detail = null;
			boolean flag = true;
			if (tableName.equals("river")) {
				detail = "规则：riverId:有规律的 XX 两个为一级，一般为下一级子节点，" + "\n"
						+ "riverLevel组织机构类型取值：0-5   0为干流，1为0的支流，2为1的支流，以此类推," + "\n" + "parentId取值为父节点，" + "\n"
						+ "regionId取值为行政区Id，" + "\n" + "isParent取值：1（是）、0（否）" + "\n" + "userId取值为用户的userId" + "\n"
						+ "type取值：1（河道）、0（非河道）" + "\n" + "remark为河流备注";
				map.put("detail", detail);
				List<River> list = dateInfoMapper.queryRiver(searchmap);
				flag = downloadFile.createTable(columnList, list, map, response);
			} else if (tableName.equals("organization")) {
				detail = "规则：organizationId:为自增顺序，" + "\n" + "organizationType组织机构类型取值：org（机构）、dept（部门）," + "\n"
						+ "level取值：0（全国）、1（省）、2（市）、3（区、县）、4（乡、镇、街道），" + "\n" + "isParent取值：1（是）、0（否）";
				List<Organization> list = organizationMapper.queryOrgList(searchmap);
				map.put("detail", detail);
				flag = downloadFile.createTable(columnList, list, map, response);
			} else if (tableName.equals("region")) {
				detail = "规则：regionId:为规定的行政区编码，" + "\n" + "parentId为父节点," + "\n"
						+ "level取值：0（全国）、1（省）、2（市）、3（区、县）、4（乡、镇、街道），" + "\n" + "isParent取值：1（是）、0（否）";
				List<Region> list = dateInfoMapper.queryRegion(searchmap);
				map.put("detail", detail);
				flag = downloadFile.createTable(columnList, list, map, response);
			} else if (tableName.equals("user")) {
				detail = "规则：userId:为，自增顺序," + "\n" + "token不用填password不用填," + "\n" + "role_id为角色ID，" + "\n"
						+ "organizationId为用户ID," + "\n" + "regionId为行政区ID" + "\n" + "status取值1（可用），0（不可用）"
						+ "createTime、modifiedTime、lastTime不用填";
				List<User> list = userMapper.queryUserList(searchmap);
				map.put("detail", detail);
				flag = downloadFile.createTable(columnList, list, map, response);
			} else if (tableName.equals("role")) {
				detail = "规则：userId:为，自增顺序," + "\n" + "token不用填password不用填," + "\n" + "role_id为角色ID，" + "\n"
						+ "organizationId为用户ID," + "\n" + "regionId为行政区ID" + "\n" + "status取值1（可用），0（不可用）"
						+ "createTime、modifiedTime、lastTime不用填";
				List<Role> list = roleMapper.queryRoleList(searchmap);
				map.put("detail", detail);
				flag = downloadFile.createTable(columnList, list, map, response);
			}

			if (flag == true) {
				result.put("message", "操作成功");
				result.put("result", 1);
			} else {
				result.put("message", "操作失败");
				result.put("result", 0);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("message", "操作失败");
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> importExcel(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		String tableName = map.get("tableName").toString();

		// 查询organization的最大的id
		// int orgaizationId = organizationMapper.selectOrganizationId();
		// System.out.println(clos + " rows:" + rows);
		if (tableName.equals("organization")) {
			result = importOrganization(map, response);
		} else if (tableName.equals("region")) {
			result = importRegion(map, response);
		} else if (tableName.equals("river")) {
			result = importRiver(map, response);
		} else if (tableName.equals("user")) {
			result = importUser(map, response);
		} else if (tableName.equals("role")) {
			result = importRole(map, response);
		}
		return result;
	}

	private Map<Object, Object> importOrganization(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> searchmap = new HashMap<Object, Object>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(map.get("fileurl").toString()));

			Sheet rs = rwb.getSheet(map.get("tableName").toString());// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			List<Organization> list = new ArrayList<Organization>();
			List<Organization> updatelist = new ArrayList<Organization>();
			for (int i = 1; i < rows; i++) {// 第二行
				for (int j = 0; j < clos; j++) {
					Organization organization = new Organization();
					// 第一个是列数，第二个是行数
					String strorganizationId = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
					// 所以这里得j++
					String organizationCode = rs.getCell(j++, i).getContents();
					String organizationName = rs.getCell(j++, i).getContents();
					String organizationType = rs.getCell(j++, i).getContents();
					String regionId = rs.getCell(j++, i).getContents();
					String strlevel = rs.getCell(j++, i).getContents();
					// String strisParent = rs.getCell(j++, i).getContents();
					String strparentId = rs.getCell(j++, i).getContents();
					int organizationId = 0;
					int level = 0;
					int isParent = 0;
					int parentId = 0;
					try {
						organizationId = Integer.parseInt(strorganizationId);
						level = Integer.parseInt(strlevel);
						// isParent = Integer.parseInt(strisParent);
						parentId = Integer.parseInt(strparentId);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误，level、isParent、parentId都必须为数字格式");
						return result;
					}

					searchmap.put("organizationId", parentId);
					List<Organization> organizationlist = organizationMapper.queryOrgList(searchmap);
					if (organizationlist == null || organizationlist.size() < 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行parentId数据有误，不存在此父节点");
						return result;
					}

					searchmap.put("parentId", regionId);
					List<Region> regionlist = dateInfoMapper.queryRegion(searchmap);
					if (regionlist == null || regionlist.size() <= 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行regionId数据有误，regionId不存在，请先填入region的数据");
						return result;
					}
					organization.setOrganizationId(organizationId);
					organization.setOrganizationCode(organizationCode);
					organization.setOrganizationName(organizationName);
					organization.setOrganizationType(organizationType);
					organization.setRegionId(regionId);
					organization.setLevel(level);
					// organization.setIsParent(isParent);
					organization.setParentId(parentId);
					List<String> msg = ValidateUtil.BeanValidate(organization);
					if (msg.size() > 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误" + msg.get(0).toString());
						return result;
					}
					searchmap.clear();
					searchmap.put("organizationId", organizationId);
					organizationlist = organizationMapper.queryOrganization(searchmap);
					if (organizationlist != null && organizationlist.size() > 0) {
						Organization temp = organizationlist.get(0);
						// System.out.println(temp.getOrganizationId()+);
						if (temp.equals(organization)) {
							continue;
						} else {
							updatelist.add(organization);
						}
					} else {
						list.add(organization);
					}
				}
			}
			if (list != null && list.size() > 0) {
				int count = organizationMapper.insertOrganization(list);
			}
			if (updatelist != null && updatelist.size() > 0) {
				int updatecount = organizationMapper.updateOrganization(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作失败");
			return result;
		}
		result.put("result", 1);
		result.put("message", "操作成功");
		return result;
	}

	private Map<Object, Object> importRegion(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> searchmap = new HashMap<Object, Object>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(map.get("fileurl").toString()));

			Sheet rs = rwb.getSheet(map.get("tableName").toString());// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			List<Region> list = new ArrayList<Region>();
			List<Region> updatelist = new ArrayList<Region>();
			for (int i = 1; i < rows; i++) {// 第二行
				for (int j = 0; j < clos; j++) {
					Region region = new Region();
					// 第一个是列数，第二个是行数
					String regionId = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
																		// 所以这里得j++
					String regionName = rs.getCell(j++, i).getContents();
					String parentId = rs.getCell(j++, i).getContents();
					// String strisParent = rs.getCell(j++, i).getContents();
					String strlevel = rs.getCell(j++, i).getContents();
					String longitute = rs.getCell(j++, i).getContents();
					String latitude = rs.getCell(j++, i).getContents();
					String remark = rs.getCell(j++, i).getContents();
					int level = 0;
					int isParent = 0;
					try {
						level = Integer.parseInt(strlevel);
						// isParent = Integer.parseInt(strisParent);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误，level、isParent都必须为数字格式");
						return result;
					}
					searchmap.put("parentId", parentId);
					List<Region> regionList = dateInfoMapper.queryRegion(searchmap);
					if (regionList == null || regionList.size() < 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行parentId数据有误，不存在此父节点");
						return result;
					}
					region.setRegionId(regionId);
					region.setRegionName(regionName);
					region.setParentId(parentId);
					region.setLevel(level);
					// region.setIsParent(isParent);
					if (latitude != null && !(latitude.equals(""))) {
						region.setLatitude(latitude);
					}
					if (longitute != null && !(longitute.equals(""))) {
						region.setLongitute(longitute);
					}

					region.setRemark(remark);
					List<String> msg = ValidateUtil.BeanValidate(region);
					if (msg.size() > 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误" + msg.get(0).toString());
						return result;
					}
					searchmap.put("parentId", regionId);
					List<Region> regionlist = dateInfoMapper.queryRegion(searchmap);
					if (regionlist != null && regionlist.size() > 0) {
						if (regionlist.get(0).equals(region)) {
							continue;
						} else {
							updatelist.add(region);
						}
					} else {
						list.add(region);
					}
				}
			}
			if (list != null && list.size() > 0) {
				int count = dateInfoMapper.insertRegion(list);
			}
			if (updatelist != null && updatelist.size() > 0) {
				int updatecount = dateInfoMapper.updateRegion(updatelist);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作失败");
			return result;
		}
		result.put("result", 1);
		result.put("message", "操作成功");
		return result;
	}

	private Map<Object, Object> importRiver(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> searchmap = new HashMap<Object, Object>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(map.get("fileurl").toString()));

			Sheet rs = rwb.getSheet(map.get("tableName").toString());// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			List<River> list = new ArrayList<River>();
			List<River> updatelist = new ArrayList<River>();
			for (int i = 1; i < rows; i++) {// 第二行
				for (int j = 0; j < clos; j++) {
					River river = new River();
					// 第一个是列数，第二个是行数
					String riverId = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
																		// 所以这里得j++
					String riverName = rs.getCell(j++, i).getContents();
					String strriverLevel = rs.getCell(j++, i).getContents();
					// String strisParent = rs.getCell(j++, i).getContents();
					String parentId = rs.getCell(j++, i).getContents();
					String fId = rs.getCell(j++, i).getContents();
					String regionId = rs.getCell(j++, i).getContents();
					String struserId = rs.getCell(j++, i).getContents();
					String strtype = rs.getCell(j++, i).getContents();
					String remark = rs.getCell(j++, i).getContents();
					int riverLevel = 0;
					int isParent = 0;
					int userId = 0;
					int type = 0;
					try {
						riverLevel = Integer.parseInt(strriverLevel);
						// isParent = Integer.parseInt(strisParent);
						userId = Integer.parseInt(struserId);
						type = Integer.parseInt(strtype);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误，riverLevel、isParent、userId、type都必须为数字格式");
						return result;
					}

					searchmap.put("riverId", parentId);
					List<River> riverlist = dateInfoMapper.queryRiverId(searchmap);
					if (riverlist == null || riverlist.size() < 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行parentId数据有误，不存在此父节点");
						return result;
					}

					searchmap.put("parentId", regionId);
					List<Region> regionlist = dateInfoMapper.queryRegion(searchmap);
					if (regionlist == null || regionlist.size() <= 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行regionId数据有误，regionId不存在，请先填入region的数据");
						return result;
					}
					river.setRiverId(riverId);
					river.setRiverName(riverName);
					river.setParentId(parentId);
					river.setRiverLevel(riverLevel);
					river.setRegionId(regionId);
					// river.setIsParent(isParent);
					river.setUserId(userId);
					if (fId != null && !(fId.equals(""))) {
						river.setFId(fId);
					}

					river.setType(type);
					river.setRemark(remark);
					List<String> msg = ValidateUtil.BeanValidate(river);
					if (msg.size() > 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误" + msg.get(0).toString());
						return result;
					}
					searchmap.clear();
					searchmap.put("riverId", riverId);
					riverlist = dateInfoMapper.queryRiverId(searchmap);
					if (riverlist != null && riverlist.size() > 0) {
						River temp = riverlist.get(0);
						// System.out.println(temp.getOrganizationId()+);
						if (temp.equals(river)) {
							continue;
						} else {
							updatelist.add(river);
						}
					} else {
						list.add(river);
					}
				}
			}
			if (list != null && list.size() > 0) {
				int count = dateInfoMapper.insertRiver(list);
			}
			if (updatelist != null && updatelist.size() > 0) {
				int updatecount = dateInfoMapper.updateRiver(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作失败");
			return result;
		}
		result.put("result", 1);
		result.put("message", "操作成功");
		return result;
	}

	private Map<Object, Object> importUser(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> searchmap = new HashMap<Object, Object>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(map.get("fileurl").toString()));

			Sheet rs = rwb.getSheet(map.get("tableName").toString());// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			List<User> list = new ArrayList<User>();
			List<User> updatelist = new ArrayList<User>();
			for (int i = 1; i < rows; i++) {// 第二行
				for (int j = 0; j < clos; j++) {
					User user = new User();
					// 第一个是列数，第二个是行数
					String struserId = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
																		// 所以这里得j++
					String userName = rs.getCell(j++, i).getContents();
					String password = rs.getCell(j++, i).getContents();
					String token = rs.getCell(j++, i).getContents();
					String strroleId = rs.getCell(j++, i).getContents();
					String regionId = rs.getCell(j++, i).getContents();
					String strorganizationId = rs.getCell(j++, i).getContents();
					String realName = rs.getCell(j++, i).getContents();
					String createTime = rs.getCell(j++, i).getContents();
					String modefiedTime = rs.getCell(j++, i).getContents();
					String lastTime = rs.getCell(j++, i).getContents();
					String email = rs.getCell(j++, i).getContents();
					String phone = rs.getCell(j++, i).getContents();
					String orgionOrganization = rs.getCell(j++, i).getContents();
					String orgionRole = rs.getCell(j++, i).getContents();
					String strstatus = rs.getCell(j++, i).getContents();
					String remark = rs.getCell(j++, i).getContents();
					int userId = 0;
					int roleId = 0;
					int organizationId = 0;
					int status = 0;
					try {
						userId = Integer.parseInt(struserId);
						roleId = Integer.parseInt(strroleId);
						organizationId = Integer.parseInt(strorganizationId);
						status = Integer.parseInt(strstatus);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误，userId、roleId、organizationId、status都必须为数字格式");
						return result;
					}

					searchmap.put("roleId", roleId);
					List<Role> rolelist = roleMapper.queryRoleList(searchmap);
					if (rolelist == null || rolelist.size() < 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行roleId数据有误，不存在此角色");
						return result;
					}

					searchmap.put("organizationId", organizationId);
					List<Organization> organizationlist = organizationMapper.queryOrgList(searchmap);
					if (organizationlist == null || organizationlist.size() < 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行organizationId数据有误，不存在此父节点");
						return result;
					}

					searchmap.put("parentId", regionId);
					List<Region> regionlist = dateInfoMapper.queryRegion(searchmap);
					if (regionlist == null || regionlist.size() <= 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行regionId数据有误，regionId不存在，请先填入region的数据");
						return result;
					}
					user.setUserId(userId);
					user.setUserName(userName);
					user.setRoleId(roleId);
					user.setRegionId(regionId);
					user.setOrganizationId(organizationId);
					user.setRealName(realName);
					if (email != null && (!email.equals(""))) {
						user.setEmail(email);
					}
					if (phone != null && (!phone.equals(""))) {
						user.setPhone(phone);
					}
					user.setOrgionOrganization(orgionOrganization);
					user.setOrgionRole(orgionRole);
					user.setStatus(status);
					user.setRemark(remark);
					List<String> msg = ValidateUtil.BeanValidate(user);
					if (msg.size() > 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误" + msg.get(0).toString());
						return result;
					}
					searchmap.clear();
					searchmap.put("userId", userId);
					List<User> userlist = userMapper.queryUserList(searchmap);
					if (userlist != null && userlist.size() > 0) {
						User temp = userlist.get(0);
						// System.out.println(temp.getOrganizationId()+);
						if (temp.equals(user)) {
							continue;
						} else {
							user.setModifiedTime(new Timestamp((new Date().getTime())));
							updatelist.add(user);
						}
					} else {
						user.setCreateTime(new Timestamp((new Date().getTime())));
						Md5Hash md5Hash = new Md5Hash("123456", "", 2);
						String mduuid = md5Hash.toString();
						user.setPassword(mduuid);
						list.add(user);
					}
				}
			}
			if (list != null && list.size() > 0) {
				int count = userMapper.insertUser(list);
			}
			if (updatelist != null && updatelist.size() > 0) {
				int updatecount = userMapper.updateUserList(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作失败");
			return result;
		}
		result.put("result", 1);
		result.put("message", "操作成功");
		return result;
	}

	private Map<Object, Object> importRole(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> searchmap = new HashMap<Object, Object>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(map.get("fileurl").toString()));

			Sheet rs = rwb.getSheet(map.get("tableName").toString());// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			List<Role> list = new ArrayList<Role>();
			List<Role> updatelist = new ArrayList<Role>();
			for (int i = 1; i < rows; i++) {// 第二行
				for (int j = 0; j < clos; j++) {
					Role role = new Role();
					// 第一个是列数，第二个是行数
					String strroleId = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
																		// 所以这里得j++
					String roleName = rs.getCell(j++, i).getContents();
					String strstatus = rs.getCell(j++, i).getContents();
					String roleDesc = rs.getCell(j++, i).getContents();
					int roleId = 0;
					int status = 0;
					try {
						roleId = Integer.parseInt(strroleId);
						status = Integer.parseInt(strstatus);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误，roleId、status都必须为数字格式");
						return result;
					}
					role.setRoleId(roleId);
					role.setRoleName(roleName);
					role.setStatus(status);
					role.setRoleDesc(roleDesc);
					List<String> msg = ValidateUtil.BeanValidate(role);
					if (msg.size() > 0) {
						result.put("result", 0);
						result.put("message", "第" + (i + 1) + "行数据有误" + msg);
						return result;
					}
					searchmap.clear();
					searchmap.put("roleId", roleId);
					List<Role> rolelist = roleMapper.queryRoleList(searchmap);
					if (rolelist != null && rolelist.size() > 0) {
						Role temp = rolelist.get(0);
						// System.out.println(temp.getOrganizationId()+);
						if (temp.equals(role)) {
							continue;
						} else {
							updatelist.add(role);
						}
					} else {
						list.add(role);
					}
				}
			}
			if (list != null && list.size() > 0) {
				int count = roleMapper.insertRole(list);
			}
			if (updatelist != null && updatelist.size() > 0) {
				int updatecount = roleMapper.updateRoleList(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作失败");
			return result;
		}
		result.put("result", 1);
		result.put("message", "操作成功");
		return result;
	}

	public Map<Object, Object> queryRegion(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Region> regionList = dateInfoMapper.queryRegionId(map);
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("regionList", regionList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> queryProblemType(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Dict> dictList = new ArrayList<Dict>();
			if (map.get("eventId") == null) {
				dictList = dateInfoMapper.queryProblemType(map);
			} else {
				List<Event> list = eventMapper.queryEvent(map);
				if (list != null && list.size() > 0) {
					String problemTypes = list.get(0).getProblemType();
					String[] problemType = problemTypes.split(",");
					for (int i = 0; i < problemType.length; i++) {
						map.put("problemType", problemType[i]);
						Dict dict = dateInfoMapper.queryProblemTypeByProblemType(map);
						if (dict != null) {
							dictList.add(dict);
						}
					}
				}
			}
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("dictList", dictList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}
}
