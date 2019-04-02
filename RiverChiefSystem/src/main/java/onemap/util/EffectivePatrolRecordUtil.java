package onemap.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

/**
 * 轨迹有效性工具
 * @author wangwch
 *
 */
public class EffectivePatrolRecordUtil {
	
	//轨迹判断接口地址
	private static String baseUrl="http://36.248.23.130:10023/PatrolRecordOperation/PatrolRecordController/getEffectivePatrolRecord";

	/**
	 * 获取有效轨迹
	 * @param patrolRecord 轨迹文本
	 * @param regionId 有效行政区范围
	 * @return
	 */
	public static ResultModel getEffectivePatrolRecord(String patrolRecord ,String regionId) {
		
		ResultModel resultModel = null;
		try {
			//接口参数
			String params = "?patrolRecord="+URLEncoder.encode(patrolRecord,"UTF-8")+"&regionId="+URLEncoder.encode(regionId,"UTF-8");
			String url = baseUrl + params;
			URL restURL = new URL(url);
			//此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
	        HttpURLConnection connection = (HttpURLConnection) restURL.openConnection();
	        //请求方式
	        connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
			connection.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			String json = "";
			while ((line = br.readLine()) != null) {
				json += line;
			}
			Gson gson = new Gson();
			resultModel = gson.fromJson(json, ResultModel.class);
			System.out.println("effective:"+resultModel.effective);
			System.out.println("message:"+resultModel.message);
			System.out.println("Mileage:"+resultModel.Mileage);
			System.out.println("patrolRecord:"+resultModel.patrolRecord);
			System.out.println("result:"+resultModel.result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
		return resultModel;
	}
	
	class ResultModel{
		String result;
		String effective;
		String patrolRecord;
		String Mileage;
		String message;
		
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getEffective() {
			return effective;
		}
		public void setEffective(String effective) {
			this.effective = effective;
		}
		public String getPatrolRecord() {
			return patrolRecord;
		}
		public void setPatrolRecord(String patrolRecord) {
			this.patrolRecord = patrolRecord;
		}
		public String getMileage() {
			return Mileage;
		}
		public void setMileage(String mileage) {
			Mileage = mileage;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	
	}
	
	public static void main(String[] args) {
		//new  URLUtil().load("http://36.248.23.130:10023/PatrolRecordOperation/PatrolRecordController/getEffectivePatrolRecord");
	 EffectivePatrolRecordUtil.getEffectivePatrolRecord(
				"[118.299945,27.458257];[119.311356,26.094343];[119.361805,26.102];[119.318687,26.084479];",
				"350102000000");
	}

}
