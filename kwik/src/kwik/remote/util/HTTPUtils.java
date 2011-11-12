package kwik.remote.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.util.Log;

/*
 * Util
 * @description Helper class for manipulating HTTP requests.
 */
public class HTTPUtils {
	
	/*
	 * postRequest
	 * 
	 * @brief Makes a POST
	 * 
	 * @param url Url to make post to
	 * 
	 * @param headers Map of headers to add to the POST request.
	 * 
	 * Returns the resultant response string.
	 */
	public static String postRequest(String url, Map<String, String> headers) throws HTTPException {
		HttpPost postRequest = new HttpPost(url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		// Solution comes from:
		// http://stackoverflow.com/questions/4424425/cant-get-httpparams-working-with-postrequest
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		for (String key : headers.keySet()) {
			postParameters.add(new BasicNameValuePair(key, headers.get(key)));
		}
		
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(postParameters);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		postRequest.setEntity(formEntity);
		
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
			Log.w(HTTPUtils.class.getSimpleName(), "Error for URL " + url, e);
			throw new HTTPException();
		}
		return null;
	}
	
	public static String URLEncode(Map<String, String> headers) {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		
		for (String key : headers.keySet()) {
			params.add(new BasicNameValuePair(key, headers.get(key)));
		}
		
		return URLEncodedUtils.format(params, "utf-8");
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
	public static String getRequest(String url, final Map<String, String> headers) throws HTTPException {
		HttpGet getRequest = new HttpGet(url + "?" + URLEncode(headers));
		DefaultHttpClient client = new DefaultHttpClient();
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
			Log.w(HTTPUtils.class.getSimpleName(), "Error for URL " + url, e);
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
