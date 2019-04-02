package common.util.RemoteAPIUtil;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


import net.sf.json.JSONObject;

public class RemoteAPIUtil {

	@Test
	public void test() throws Exception {
		String serverUrl = "http://36.250.234.48:8005/newAPI/basics/v1.0/rivers";//上传地址  
		callRemoteWithJSONString(serverUrl,"{'data':[{'code':'DNDFD0004','river_name':'交溪(管阳溪)1','user_id':'578','extent':'','type':'stream','chunk':'','purpose':'7','area_code':'350982000000','acreage':'','longitude':'143.03534','latitude':'132.1201','latlng_arry':null,'user_title':null,'layer_level':null,'basin':'','province_code':'350000000000','start_point':'','end_point':''}]}");
	}
	
	/**
	 * 本方法可以通过请求头为mutipart/form-data，上传参数和文件，参数或者文件list均可为空。
	 * 注：请求头为mutipart/form-data,参数需要用到boundary分割，声明Content-Disposition等
	 * @param url 对接系统的链接
	 * @param o  传参可以是java对象，对接系统接收属性名映射接受参数。该对象可以为空，即只上传文件
	 * @param ufi 设定要上传的文件，list表示可以上传多个文件
	 * @return  JSONObject 返回值是JSON对象  对接系统的返回值
	 * @author liwq111 2018-4-9
	 * 
	 * 	ArrayList<FormFieldKeyValuePair> ffkvp = new ArrayList<FormFieldKeyValuePair>();  
     *  ffkvp.add(new FormFieldKeyValuePair("username", "FJSHZB01"));//其他参数  
     *  ffkvp.add(new FormFieldKeyValuePair("password", "123456"));  
	 */
	public static JSONObject callRemoteFormData(String url,Object o,ArrayList<UploadFileItem> ufi) throws Exception {  
        // 设定要上传的普通Form Field及其对应的value  
        Field[] fields=o.getClass().getDeclaredFields(); 
        ArrayList<FormFieldKeyValuePair> ffkvp = new ArrayList<FormFieldKeyValuePair>();  
        for(int i=0;i<fields.length;i++){  
        	ffkvp.add(new FormFieldKeyValuePair(fields[i].getName(), getFieldValueByName(fields[i].getName(), o)));
        }  
        HttpPostEmulator hpe = new HttpPostEmulator();  
        String response = hpe.sendHttpPostRequest(url, ffkvp, ufi);  
        JSONObject JSONob = JSONObject.fromObject(response);
        return JSONob;
        //对 imageUrl、thumbnailUrl、shortUrl进行获取，不能返回空  
      /*  HttpClient httpClient = new HttpClient();  
            GetMethod getMethod = new GetMethod(imageUrl);  
            if (httpClient.executeMethod(getMethod) != HttpStatus.SC_OK) {  
                Assert.fail("imageUrl 内容不存在.");  
            }  */
     }  
	
	/**
	 * 注：请求头为x-www-form-urlencoded,参数为键值对如username=xxx&password=xxx
	 * @param url 对接系统的链接
	 * @param o  传参可以是java对象，对接系统接收
	 * @return  JSONObject 返回值是JSON对象  对接系统的返回值
	 * @author liwq111 2018-4-9
	 */
	public static JSONObject callRemoteWithObject(String url,Object o) {
		 Field[] fields=o.getClass().getDeclaredFields();  
	    	List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	    for(int i=0;i<fields.length;i++){  
	        formparams.add(new BasicNameValuePair(fields[i].getName(), getFieldValueByName(fields[i].getName(), o)));
	    }  
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
         try {  
             CloseableHttpClient httpclient = HttpClients.createDefault();  
             HttpPost httpPost= new HttpPost(url);  
             //设置需要传递的数据  
             httpPost.setEntity(entity);  
             // Create a custom response handler  
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {  
                //对访问结果进行处理  
                public JSONObject handleResponse(  
                        final HttpResponse response) throws ClientProtocolException, IOException {  
                    int status = response.getStatusLine().getStatusCode();  
                    if (status >= 200 && status < 300) {  
                        HttpEntity entity = response.getEntity();  
                        if(null!=entity){  
                            String result= EntityUtils.toString(entity);  
                            //根据字符串生成JSON对象  
                            JSONObject resultObj = JSONObject.fromObject(result);  
                            return resultObj;  
                        }else{  
                            return null;  
                        }  
                    } else {  
                        throw new ClientProtocolException("Unexpected response status: " + status);  
                    }  
                }  
            };  
          //返回的json对象  
            JSONObject responseBody = httpclient.execute(httpPost, responseHandler);  
            System.out.println(responseBody);  
            httpclient.close();  
            return responseBody;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return null;  
	}
	
	
	/**
	 * 注：请求头为x-www-form-urlencoded
	 * @param url 对接系统的链接
	 * @param jsonContext  JSON字符串
	 * @return  JSONObject 返回值是JSON对象  对接系统的返回值
	 * @author liwq111 2018-4-9
	 */
	public static JSONObject callRemoteWithJSONString(String url,String jsonContext) {
         try {  
             CloseableHttpClient httpclient = HttpClients.createDefault();  
             HttpPost httpPost= new HttpPost(url);  
             //发送json格式的数据  
             StringEntity myEntity = new StringEntity(jsonContext,   
                       ContentType.create("text/plain", "UTF-8"));  
             //设置需要传递的数据  
             httpPost.setEntity(myEntity);  
             // Create a custom response handler  
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {  
                //对访问结果进行处理  
                public JSONObject handleResponse(  
                        final HttpResponse response) throws ClientProtocolException, IOException {  
                    int status = response.getStatusLine().getStatusCode();  
                    if (status >= 200 && status < 300) {  
                        HttpEntity entity = response.getEntity();  
                        if(null!=entity){  
                            String result= EntityUtils.toString(entity);  
                            //根据字符串生成JSON对象  
                            JSONObject resultObj = JSONObject.fromObject(result);  
                            return resultObj;  
                        }else{  
                            return null;  
                        }  
                    } else {  
                        throw new ClientProtocolException("Unexpected response status: " + status);  
                    }  
                }  
            };  
          //返回的json对象  
            JSONObject responseBody = httpclient.execute(httpPost, responseHandler);  
            System.out.println(responseBody);  
            httpclient.close();  
            return responseBody;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return null;  
	}
	
	
	/**
	 * 注：请求头为raw
	 * @param url 对接系统的链接
	 * @param jsonContext  JSON字符串
	 * @return  JSONObject 返回值是JSON对象  对接系统的返回值
	 * @author liwq111 2018-4-9
	 */
	/*public static JSONObject callRemoteWithRow(String url,String jsonContext) {
         try {  
             CloseableHttpClient httpclient = HttpClients.createDefault();  
             HttpPost httpPost= new HttpPost(url);  
             //发送json格式的数据  
             StringEntity myEntity = new StringEntity(jsonContext,ContentType.create("application/json", "UTF-8"));  
             //设置需要传递的数据  
             httpPost.setEntity(myEntity);  
             httpPost.setHeader("x-hzz-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyY29kZSI6IjEwMDAwMDI0MSIsInR5cGUiOiIyIiwiZXhwaXJlc0luIjoxNTI0NTM4OTE0MjQzLCJpYXQiOjE1MjQ1MzE3MTQsImV4cCI6MTUyNjA2MzQ0NTk1N30.3JG6cb83mF77mRV5xJGHWe4XzxHOym0kvX10DtyuHY8");
             httpPost.setHeader("Content-type", "application/json");
             // Create a custom response handler  
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {  
                //对访问结果进行处理  
                public JSONObject handleResponse(  
                        final HttpResponse response) throws ClientProtocolException, IOException {  
                    int status = response.getStatusLine().getStatusCode();  
                    if (status >= 200 && status < 300) {  
                        HttpEntity entity = response.getEntity();  
                        if(null!=entity){  
                            String result= EntityUtils.toString(entity);  
                            //根据字符串生成JSON对象  
                            JSONObject resultObj = JSONObject.fromObject(result);  
                            return resultObj;  
                        }else{  
                            return null;  
                        }  
                    } else {  
                        throw new ClientProtocolException("Unexpected response status: " + status);  
                    }  
                }  
            };  
          //返回的json对象  
            JSONObject responseBody = httpclient.execute(httpPost, responseHandler);  
            System.out.println(responseBody);  
            httpclient.close();  
            return responseBody;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return null;  
	}*/
	
	
	
	
	
	
	/** 工具类
	 * 根据属性名获取属性值 
	 * */  
	   private static String getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           String value = (String)method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	    	   e.printStackTrace();
	           return null;    
	       }    
	   }

}
