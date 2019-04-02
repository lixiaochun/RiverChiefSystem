package websocket.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.UserRTP;
import websocket.mapper.UserRTPMapper;
import websocket.service.UserRTPService;

@Service
public class UserRTPServiceImpl implements UserRTPService{
    
	@Autowired
	private UserRTPMapper userRTPMapper;
	
	@Override
	public void updateUserPosition(UserRTP userRTP) {
		List<UserRTP> list = userRTPMapper.IsUserPositionExist(userRTP);
		if(list.size()>0){//当用户位置已存在则更新用户位置
			userRTPMapper.updateUserPosition(userRTP);
		}else{//当用户位置不存在则插入一条新消息
			userRTPMapper.insertUserPosition(userRTP);
		}
	}

	@Override
	public void deleteUserPosition(String userId) {
		userRTPMapper.deleteUserPosition(userId);
		
	}

	@Override
	public List<UserRTP> findUserRTPList() {
		List<UserRTP> list = userRTPMapper.findUserRTPList();
		return list;
	}

}
