package assessment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import assessment.mapper.GroupAssessMapper;
import assessment.service.GroupAssessService;
import common.model.GroupAssess;
import common.model.UserRank;
import common.model.User;
import common.model.UserScore;
import common.model.UserScoreOne;

@Service("GroupAssessService")
@Transactional(readOnly = true)
public class GroupAssessServiceImpl implements GroupAssessService {

	@Autowired
	private GroupAssessMapper groupAssessMapper;

	public List<GroupAssess> findAllList(GroupAssess ga) {
		List<GroupAssess> list = groupAssessMapper.findAllList(ga);
		return list;
	}

	public List<UserScore> userScoreList(int groupId) {
		List<UserScore> list = groupAssessMapper.userScoreList(groupId);
		return list;
	}

	public GroupAssess fetchExcelId(int groupId) {
		GroupAssess ga = groupAssessMapper.fetchExcelId(groupId);
		return ga;
	}

	public int countList(GroupAssess ga) {
		int count = groupAssessMapper.countList(ga);
		return count;
	}
	

	@Override
	public List<User> findUserList(User user) {
		List<User>  list = groupAssessMapper.findUserList(user);
		return list;
	}

	@Override
	public int countUserList(User user) {
		int count = groupAssessMapper.countUserList(user);
		return count;
	}

	@Override
	public List<UserScoreOne> findOneUserScore(int groupId, int userId) {
		List<UserScoreOne> list =  groupAssessMapper.findOneUserScore(groupId, userId);
		return list;
	}

	@Override
	public int insertGroupAssess(GroupAssess ga) {
		groupAssessMapper.insertGroupAssess(ga);
		int gaId =groupAssessMapper.findGroupAssessId(ga);
		return gaId;
	}

	@Override
	public void insertGroupScore(List<UserScore> userscoreList) {
		groupAssessMapper.insertGroupScore(userscoreList);
	}

	@Override
	public void insertGroupScoreOne(List<UserScoreOne> userscoreOneList) {
		groupAssessMapper.insertGroupScoreOne(userscoreOneList);
	}

	@Override
	public void modifyGroupAssess(GroupAssess ga) {
		groupAssessMapper.modifyGroupAssess(ga);
	}

	@Override
	public void modifyUserOneScore(UserScore us) {
		groupAssessMapper.modifyUserOneScore(us);		
	}

	@Override
	public void modifyUserOneScoreDetail(List<UserScoreOne> userscoreOneList) {
		for(UserScoreOne uso:userscoreOneList){
		groupAssessMapper.modifyUserOneScoreDetail(uso);
		}
	}

	@Override/*  添加用户待评表  */
	public void addUserToBeAssess(List<GroupAssess> gaList) {
		groupAssessMapper.addUserToBeAssess(gaList);
		/* 投送最新待办消息
		WebSocketTest wst = new WebSocketTest();
		try {
			wst.sendMessageByUserId(123, "成功了你好牛逼");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public List<GroupAssess> findToAssessList(String userId) {
		List<GroupAssess> gaList = groupAssessMapper.findToAssessList(userId);
		return gaList;
	}

	public GroupAssess selectOneToAssess(String id) {
		GroupAssess ga = groupAssessMapper.selectOneToAssess(id);
		return ga;
	}

	public void insertUserselfAssess(UserScore us) {
		groupAssessMapper.insertUserselfAssess(us);
	}

	public void insertUserselfScore(List<UserScoreOne> uso) {
		groupAssessMapper.insertUserselfScore(uso);
	}

	public List<UserRank> findAssessRank(String excelId) {
		List<UserRank> list= groupAssessMapper.findAssessRank(excelId);
		return  list;
	}

	public List<UserRank> findRegionRank(String excelId, String regionId) {
		List<UserRank> list= groupAssessMapper.findRegionRank(excelId,regionId);
		return list;
	}
}
