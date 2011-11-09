package kwik.remote.api;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class Response {
	@Attribute
	String			status;
	
	@ElementList(required = false)
	List<Product>	products;
	
	@Element(required = false)
	Product			product;
	
	/*
	 * get
	 * 
	 * @brief Gets an API response from the given URL
	 * 
	 * @param url Url to get the Response object from.
	 * 
	 * @return Response object containing the data
	 */
	public static Response get(String url) throws ResponseException {
		try {
			String responseXML = Util.getRequest(url);
			return fromString(responseXML);
		} catch (Exception e) {
			throw new ResponseException();
		}
	}
	
	/*
	 * post
	 * 
	 * @brief Posts and retreives an API response from the given URL
	 * 
	 * @param url Url to post and retreive the Response object from;
	 * 
	 * @return Response object containing the answer data.
	 */
	public static Response post(String url, Map<String, String> headers) throws ResponseException {
		try {
			String responseXML = Util.postRequest(url, headers);
			return fromString(responseXML);
		} catch (Exception e) {
			throw new ResponseException();
		}
	}
	
	/*
	 * Little helper for making the actual parse
	 */
	private static Response fromString(String s) throws Exception {
		Serializer serializer = new Persister();
		Reader reader = new StringReader(s);
		return serializer.read(Response.class, reader, false);
	}
}
