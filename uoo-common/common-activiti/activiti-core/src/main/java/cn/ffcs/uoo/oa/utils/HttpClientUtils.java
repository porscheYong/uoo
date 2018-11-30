package cn.ffcs.uoo.oa.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient客户端工具
 */
public class HttpClientUtils {
	
	public static JSONObject postHttp(String restServiceUrl,
			List<BasicNameValuePair> params) throws Exception {

		String resultCode = null;
		JSONObject result = null;
		HttpPost httpPost = new HttpPost(restServiceUrl);
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = client.execute(httpPost);
			resultCode = String.valueOf(response.getStatusLine().getStatusCode());
			if("200".equals(resultCode)){
				String entityStr = EntityUtils.toString(response.getEntity());
				result = JSON.parseObject(entityStr);
				return result; 
			}
			else {
				throw new RuntimeException("状态有误，请联系管理员!");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return result;
	}
	
	
	public static JSONObject getHttp(String restServiceUrl,Map<String, Object> params) {
		String entityStr = null; 
		JSONObject jsonObj = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = null;
		
		try {
			if(params !=null && !params.isEmpty()){  
			    
			    List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(params.size());  
			      
			    for (String key :params.keySet()){  
			        pairs.add(new BasicNameValuePair(key, (String) params.get(key)));  
			    }
			    restServiceUrl +="?"+EntityUtils.toString(new UrlEncodedFormEntity(pairs), "UTF-8");
			    
			}  
			  
			httpGet = new HttpGet(restServiceUrl);	  
			CloseableHttpResponse response = client.execute(httpGet);  
			int statusCode = response.getStatusLine().getStatusCode();  
			if(statusCode !=200){  
			    httpGet.abort();  
			    throw new RuntimeException("HttpClient,error status code :" + statusCode);  
			}  
			HttpEntity entity = response.getEntity();  
			 
			if (entity != null) {
				entityStr = EntityUtils.toString(entity, "utf-8");  
			    jsonObj = JSON.parseObject(entityStr);
			    EntityUtils.consume(entity);  
			    response.close();  
			    return jsonObj;  
			}else{  
			    return null;  
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			httpGet.releaseConnection();
		}
		return jsonObj; 
	}

}
