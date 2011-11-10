package kwik.remote.api;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.api.exceptions.APIBadResponseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class Response {
	public static final String COMMON   = "http://eiffel.itba.edu.ar/hci/service/Common.groovy";
	public static final String SECURITY = "http://eiffel.itba.edu.ar/hci/service/Security.groovy";
	public static final String CATALOG  = "http://eiffel.itba.edu.ar/hci/service/Catalog.groovy";
	public static final String ORDER    = "http://eiffel.itba.edu.ar/hci/service/Order.groovy";
	
	
	@Attribute
	public String				status;
	
	@Element(required = false)
	public Authentication		authentication;
	
	@ElementList(required = false)
	public List<Language>		languages;
	
	@ElementList(required = false)
	public List<Category>		categories;
	
	@ElementList(required = false, name="subcategories")
	public List<SubCategory>	subCategories;
	
	@ElementList(required = false)
	public List<Country>		countries;
	
	@ElementList(required = false)
	public List<State>			states;

	@ElementList(required = false)
	public List<Order>			orders;
	
	@ElementList(required = false)
	public List<Product>		products;
	
	@Element(required = false)
	public Product				product;
	
	@Element(required = false)
	public Address				address;
	
	@Element(required = false)
	public List<Address>		addresses;
	
	@Element(required = false) // And also not desired :p
	public Error				error;
	
	@Element(required = false)
	public User					account;
	
	@Element(required = false)
	public Order				order;
	
	/*
	 * get
	 * 
	 * @brief Gets an API response from the given URL
	 * 
	 * @param url Url to get the Response object from.
	 * 
	 * @return Response object containing the data
	 */
	public static Response get(String url, Map<String, String> headers) throws APIBadResponseException, XMLParseException, HTTPException {
		String responseXML = Util.getRequest(url, headers);
		return fromString(responseXML);
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
	public static Response post(String url, Map<String, String> headers) throws APIBadResponseException, XMLParseException, HTTPException {
		String responseXML = Util.postRequest(url, headers);
		return fromString(responseXML);
	}
	
	/*
	 * Little helper for making the actual parse
	 */
	private static Response fromString(String s) throws APIBadResponseException, XMLParseException {
		Serializer serializer = new Persister();
		Reader reader = new StringReader(s);
		Response r = null;
		try {
			r = serializer.read(Response.class, reader, false);
		} catch (Exception e) {
			throw new XMLParseException();
		}
		if (!r.status.equals("ok")) {
			throw new APIBadResponseException(r.error);
		}
		return r;
	}
}
