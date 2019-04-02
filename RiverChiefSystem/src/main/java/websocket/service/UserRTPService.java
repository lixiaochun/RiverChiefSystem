package websocket.service;

import java.util.List;

import common.model.UserRTP;

public interface UserRTPService {
	  public List<UserRTP> findUserRTPList();
      public void updateUserPosition(UserRTP userRTP);
	  public void deleteUserPosition(String userId);
}
