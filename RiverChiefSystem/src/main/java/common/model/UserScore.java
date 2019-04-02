package common.model;

public class UserScore {
	private int id;
	private int userId;
	private double userScore;
	private String realName;
	private int groupId;
	
	

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getUserScore() {
		return userScore;
	}

	public void setUserScore(double userScore) {
		this.userScore = userScore;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
