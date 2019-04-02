package websocket.mapper;

import java.util.List;

import common.model.UserRTP;

public interface UserRTPMapper {
	public List<UserRTP> IsUserPositionExist(UserRTP userRTP);
    public void insertUserPosition(UserRTP userRTP);
    public void updateUserPosition(UserRTP userRTP);
	public void deleteUserPosition(String userId);
	public List<UserRTP> findUserRTPList();
}
