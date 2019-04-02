package onemap.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import cc.eguid.FFmpegCommandManager.FFmpegManager;
import cc.eguid.FFmpegCommandManager.FFmpegManagerImpl;
import common.util.Constant;
import common.util.FileUploadUtil;
import onemap.mapper.VideoSurveillanceMapper;
import onemap.service.VideoSurveillanceService;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/ShowVideo" })
@ServerEndpoint("/ShowVideo")
public class ShowVideo {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<ShowVideo> webSocketSet = new CopyOnWriteArraySet<ShowVideo>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	private UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("UserService");

	private VideoSurveillanceService videoSurveillanceService = (VideoSurveillanceService) ContextLoader.getCurrentWebApplicationContext().getBean("VideoSurveillanceServiceImpl");

	private VideoSurveillanceMapper videoSurveillanceMapper = (VideoSurveillanceMapper) ContextLoader.getCurrentWebApplicationContext().getBean("videoSurveillanceMapper");

	FileUploadUtil uploadinfo = new FileUploadUtil();

	Constant constant = new Constant();

	@RequestMapping(value = { "/ShowVideo" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> ShowVideo(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> update = new HashMap<Object, Object>();

		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		// 处理参数
		String id = request.getParameter("videoSurveillanceId");
		map = videoSurveillanceService.findVideoSurveillanceById(Integer.parseInt(id));
		
		return map;
	}

	@RequestMapping(value = { "/StopVideo" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> StopVideo(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> update = new HashMap<Object, Object>();

		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		String id = request.getParameter("videoSurveillanceId");
		map = videoSurveillanceService.findVideoSurveillanceById(Integer.parseInt(id));
		if (Integer.parseInt(map.get("result").toString()) == 1) {
			if (map.get("VideoSurveillance") != null) {
				String status = ((Map<Object, Object>) map.get("VideoSurveillance")).get("status").toString();
				if (status.equals("0")) {
					map.put("result", 1);
					map.put("message", "该视频无在播放！");
					return map;
				}
				FFmpegManager manager = new FFmpegManagerImpl();
				manager.stop(id);
				update.put("id", id);
				update.put("status", 0);
				videoSurveillanceMapper.updateVideoSurveillanceByStatus(update);
				map.put("result", 1);
				map.put("message", "操作成功");
			}
		} else {
			map.put("result", 0);
			map.put("message", "查无数据");
		}
		return map;
	}

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> update = new HashMap<Object, Object>();
		try {
			String id = message;
			map = videoSurveillanceService.findVideoSurveillanceById(Integer.parseInt(id));
			if (Integer.parseInt(map.get("result").toString()) == 1) {
				if (map.get("VideoSurveillance") != null) {
					String status = ((Map<Object, Object>) map.get("VideoSurveillance")).get("status").toString();
					if (status.equals("1")) {
						System.out.println("流服务正在启动!");
						this.sendMessage("-3");
						return;
					}
					FFmpegManager manager = new FFmpegManagerImpl();
					// -rtsp_transport tcp
					// 测试多个任何同时执行和停止情况
					// 默认方式发布任务
					if (((Map<Object, Object>) map.get("VideoSurveillance")).get("ip_address") != null && !(((Map<Object, Object>) map.get("VideoSurveillance")).get("ip_address").equals(""))) {
						String ipAddress = ((Map<Object, Object>) map.get("VideoSurveillance")).get("ip_address").toString();
						String url = constant.getProperty("path") + "video/" + id + "/";
						uploadinfo.mkfiledir(url);
						url = url + "playlist.m3u8";
						manager.start(id, "ffmpeg -rtsp_transport tcp -i " + ipAddress + " -fflags flush_packets -max_delay 2 -flags -global_header -hls_time 2 -hls_list_size 3 -vcodec copy -y " + url);

						update.put("id", id);
						update.put("status", 1);
						videoSurveillanceMapper.updateVideoSurveillanceByStatus(update);
						System.out.println("流服务正在启动!");
						this.sendMessage("-2");
						for (int i = 0; i < 300; i++) {
							Thread.sleep(1000);// 1秒*60*5
						}
						manager.stop(id);
						update.put("id", id);
						update.put("status", 0);
						videoSurveillanceMapper.updateVideoSurveillanceByStatus(update);
						// 停止全部任务
						for (ShowVideo item : webSocketSet) {
							item.sendMessage(message);
						}
					} else {
						map.put("result", 2);
						map.put("message", "该视频无路径");
						System.out.println("该视频无路径!");
						this.sendMessage("0");
						this.onClose();
					}
				} else {
					map.put("result", 2);
					map.put("message", "查无内容");
					System.out.println("查无内容!");
					this.sendMessage("0");
					this.onClose();
				}
			} else {
				map.put("result", 2);
				map.put("message", "查无数据");
				System.out.println("查无数据!");
				this.sendMessage("0");
				this.onClose();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("操作异常!");
			this.onError(session, e);
			this.onClose();
		}

		System.out.println("来自客户端的消息:" + message);

	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		ShowVideo.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		ShowVideo.onlineCount--;
	}

	public void sendMsg(String msg) {
		for (ShowVideo item : webSocketSet) {
			try {
				item.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
