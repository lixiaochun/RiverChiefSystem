package assessment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.model.GroupAssess;
import common.model.UserRank;
import common.model.User;
import common.model.UserScore;
import common.model.UserScoreOne;

public interface GroupAssessMapper {

	public List<GroupAssess> findAllList(GroupAssess ga);

	public List<UserScore> userScoreList(int groupId);

	public GroupAssess fetchExcelId(int groupId);
	
	public int countList(GroupAssess ga);
	
	public List<UserScoreOne> findOneUserScore(int groupId, int userId);
	
	public int insertGroupAssess(GroupAssess ga);

	public int findGroupAssessId(GroupAssess ga);
	
	public void insertGroupScore(List<UserScore> userscoreList);
	
	public void insertGroupScoreOne(List<UserScoreOne> userscoreOneList);
	
	public void modifyGroupAssess(GroupAssess ga);
	
	public void modifyUserOneScore(UserScore us);

	public void modifyUserOneScoreDetail(UserScoreOne uso);
	
	public List<User> findUserList(User user);

	public int countUserList(User user);

	public void addUserToBeAssess(List<GroupAssess> list);

	public List<GroupAssess> findToAssessList(String userId);

	public GroupAssess selectOneToAssess(String id);

	public void insertUserselfAssess(UserScore us);

	public void insertUserselfScore(List<UserScoreOne> list);

	public List<UserRank> findAssessRank(String excelId);

	public List<UserRank> findRegionRank(@Param("excelId")String excelId,@Param("regionId") String regionId);
}
