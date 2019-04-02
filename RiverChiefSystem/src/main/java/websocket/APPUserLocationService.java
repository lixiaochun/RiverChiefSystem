package websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import common.model.UserRTP;
import common.util.SpringContextHolder;
import usermanage.service.UserService;
import websocket.service.UserRTPService;
import websocket.util.ZipUtil;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/userLocation/{userId}/{token}")
public class APPUserLocationService {
	@Autowired
	private UserService userService=SpringContextHolder.getBean(UserService.class);
	
	@Autowired
	private UserRTPService userRTPService=SpringContextHolder.getBean(UserRTPService.class);
	
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int usefulOnlineCount = 0;
    
    private String userId = "";
    private String token = "";

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<APPUserLocationService> webSocketSet = new CopyOnWriteArraySet<APPUserLocationService>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    public String getUserId(){
    	return this.userId;
    }
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId")String userId,@PathParam("token")String token){
        this.session = session;
        this.userId = userId;
        this.token = token;
        int check = userService.checkToken(this.userId, this.token);
        addOnlineCount();  //所有的在线数加1
        System.out.println("有新连接加入！当前在线人数:" + getOnlineCount());
        if(check == 1){
        	webSocketSet.add(this);     //加入set中
        	addUsefulOnlineCount();          //有用的在线数加1
            System.out.println("有新连接加入！当前在线人数:" + getOnlineCount()+";有效连接数:"+getUsefulOnlineCount()+";userId:"+this.userId);
            try {
				sendMessage("连接成功，欢迎使用系统服务！");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }else{
        	try {
        		this.userId = "";
                this.token = "";
				sendMessage("连接成功，请先登录系统，本次连接是无效连接！");
				this.session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	if(!"".equals(this.token)&&!"".equals(this.userId)){
    		subUsefulOnlineCount(); //有用的在线数减1
    		 System.out.println("有一连接关闭！当前有效在线人数为" + getUsefulOnlineCount());
    		 userRTPService.deleteUserPosition(this.userId);
    	}
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前所有在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {//this.session.getBasicRemote().sendText(message);可以给当前用户回消息
    	try {
    	UserRTP userRTP = JSON.parseObject(message, UserRTP.class);
    	userRTPService.updateUserPosition(userRTP);
    	List<UserRTP> list = userRTPService.findUserRTPList();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("onlineNum", getUsefulOnlineCount());
    	map.put("userLocationList", list);
    	this.session.getBasicRemote().sendText(JSON.toJSON(map).toString());
    	
    	String msg = ZipUtil.gzip(JSON.toJSON(map).toString());//压缩数据
    	this.session.getBasicRemote().sendText(msg);
		} catch (Exception e) {
			try {//用户上传位置信息出错
				this.session.getBasicRemote().sendText("请上传正确的位置信息");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
    }
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
    public static synchronized int getOnlineCount() {//获取当前所有连接数
        return onlineCount;
    }
    
    public static synchronized int getUsefulOnlineCount() {//获取当前有效连接数
        return usefulOnlineCount;
    }

    public static synchronized void addOnlineCount() {//当前所有连接数+1
    	APPUserLocationService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {//当前所有连接数-1
    	APPUserLocationService.onlineCount--;
    }
    
    
    public static synchronized void addUsefulOnlineCount() {//当前有效连接数+1
    	APPUserLocationService.usefulOnlineCount++;
    }

    public static synchronized void subUsefulOnlineCount() {//当前有效连接数-1
    	APPUserLocationService.usefulOnlineCount--;
    }
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }
    /**
     * 给指定用户ID发送消息
     * @param userId, message
     * @throws IOException
     */
    public void sendMessageByUserId(String userId,String message) throws IOException{
    	for(APPUserLocationService item: webSocketSet){
            try {
            	if(item.getUserId() == userId){
            		 item.sendMessage(message);
            	}
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    	
    }
}
