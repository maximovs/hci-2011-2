package kwik.remote.api;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;


/*
 * Util
 * @description Helper class for manipulating HTTP requests.
 */
public class Util {
	
	private static DefaultHttpClient	client	= new DefaultHttpClient();
	
	/*
	 * postRequest
	 * 
	 * @brief Makes a POST
	 * 
	 * @param url Url to make post to
	 * @param headers Map of headers to add to the POST request.
	 * 
	 * Returns the resultant response string.
	 */
	public static String postRequest(String url, Map<String, String> headers) {
		HttpPost postRequest = new HttpPost(url);
		
		for (String key : headers.keySet()) {
			postRequest.addHeader(key, headers.get(key));
		}
		
		try {
			HttpResponse getResponse = client.execute(postRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}
		} catch (IOException e) {
			postRequest.abort();
			Log.w(Util.class.getSimpleName(), "Error for URL " + url, e);
		}
		return null;
	}
	
	/*
	 * getRequest
	 * 
	 * @brief Makes a GET
	 * 
	 * @param url Url go get data from.
	 * 
	 * Returns the resultant response string.
	 */
	public static String getRequest(String url) {
		HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}
		} catch (IOException e) {
			getRequest.abort();
			Log.w(Util.class.getSimpleName(), "Error for URL " + url, e);
		}
		
		return null;		
	}
}
