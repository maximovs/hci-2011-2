package kwik.remote.api;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
	public static String postRequest(String url, Map<String, String> headers) throws HTTPException {
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
			throw new HTTPException();
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
	public static String getRequest(String url, Map<String, String> headers) throws HTTPException {
		HttpGet getRequest = new HttpGet(url);
		
		for (String key : headers.keySet()) {
			getRequest.addHeader(key, headers.get(key));
		}
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
			throw new HTTPException();
		}
		
		return null;		
	}
	
	public static String serializeObjectToXML(Object o) throws XMLParseException {
		StringWriter writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			serializer.write(o, writer);
		} catch (Exception e) {
			throw new XMLParseException();
		}
		return writer.toString();
	}
}
