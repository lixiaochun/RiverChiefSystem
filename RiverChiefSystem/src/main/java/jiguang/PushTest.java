package jiguang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushTest {

	protected static final Logger log = LoggerFactory.getLogger(PushTest.class);
	protected static final String appKey = "0e1bfc1f5147065af2e84118";
	protected static final String masterSecret = "792810c75f4bb95c3c822042";

	// 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.alertAll(alert);
	}

	// 生成极光推送对象PushPayload（采用java SDK）
	public static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.addPlatformNotification(
								IosNotification.newBuilder().addExtra("type", "infomation").setAlert(alert).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(false)// true-推送生产环境 false-推送开发环境（测试使用参数）
						.setTimeToLive(90)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	// 所有平台，别名为alias，内容为 alert的通知。
	public static PushResult push(String alias, String alert) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
		System.out.println("开始推送");
		PushPayload payload = buildPushObject_android_ios_alias_alert(alias, alert);
		try {
			return jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			// log.error("Connection error. Should retry later. ", e);
			return null;
		} catch (APIRequestException e) {

			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());

			return null;
		}
	}

	// 所有平台，所有设备，内容为 ALERT 的通知。
	public static PushResult push(String alert) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
		PushPayload payload = buildPushObject_all_all_alert(alert);
		try {
			return jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			// log.error("Connection error. Should retry later. ", e);
			return null;
		} catch (APIRequestException e) {

			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());

			return null;
		}

	}

	public static void main(String[] args) {

		// new PushTest().jiguangPush();
		// new PushTest().push("a", "aaaaaaa");
		/*for (int i = 0; i < 100; i++) {
			PushTest.push("aaa", "fafafagag");
		}*/
		PushTest.push("aaa", "fafafagag");

	}

}
