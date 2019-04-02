package assessment.service;

import java.util.List;

import common.model.GroupAssess;
import common.model.UserRank;
import common.model.User;
import common.model.UserScore;
import common.model.UserScoreOne;

public interface GroupAssessService {
	public List<GroupAssess> findAllList(GroupAssess ga);//小组考评列表

	public List<UserScore> userScoreList(int groupId);//用户得分列表

	public GroupAssess fetchExcelId(int groupId);//获取excelid

	public int countList(GroupAssess ga);

	public List<UserScoreOne> findOneUserScore(int groupId, int userId);

	public int insertGroupAssess(GroupAssess ga);

	public void insertGroupScore(List<UserScore> userscoreList);

	public void insertGroupScoreOne(List<UserScoreOne> userscoreOneList);

	public void modifyGroupAssess(GroupAssess ga);

	public void modifyUserOneScore(UserScore us);

	public void modifyUserOneScoreDetail(List<UserScoreOne> userscoreOneList);

	public List<User> findUserList(User user);

	public int countUserList(User user);

	public void addUserToBeAssess(List<GroupAssess> gaList);//为小组考核中的成员添加一条待办自评

	
	/*----------------------------------------------手机app端---------------------------------------------------------*/
	
	public List<GroupAssess> findToAssessList(String userId);//查询当前用户的待自评列表

	public GroupAssess selectOneToAssess(String id);//根据id查询一个待办评分任务

	public void insertUserselfAssess(UserScore us);

	public void insertUserselfScore(List<UserScoreOne> uso);

	public List<UserRank> findAssessRank(String excelId);

	public List<UserRank> findRegionRank(String excelId, String regionId);

}
