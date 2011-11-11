package kwik.remote.api;

import java.util.HashMap;
import java.util.Map;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.util.HTTPUtils;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
@Element
public class Address {
	
	@Attribute (required=false)
	public int id = -1;
	
	@Element
	public String 	full_name;
	@Element
	public String	address_line_1;
	@Element
	public String	address_line_2;
	@Element
	public int		country_id;
	@Element
	public int		state_id;
	@Element
	public String	city;
	@Element
	public String	zip_code;
	@Element
	public String	phone_number;
	
	User user;
	
	public Address() { }
	
	public Address(String full_name, String address_line_1, 
				   String address_line_2, int country_id, int state_id, String city, 
				   String zip_code, String phone_number, User u) {
		this.full_name = full_name;
		this.address_line_1 = address_line_1;
		this.address_line_2 = address_line_2;
		this.country_id = country_id;
		this.state_id = state_id;
		this.zip_code = zip_code;
		this.phone_number = phone_number;
		this.user = u;
	}
	
	public boolean update() throws APIBadResponseException, XMLParseException, HTTPException {
		String selfToXML;
		try {
			selfToXML = HTTPUtils.serializeObjectToXML(this);
			
		} catch (XMLParseException e) {
			e.printStackTrace();
			return false;
		}
		
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "UpdateAddress");
		headers.put("username", user.username);
		headers.put("authentication_token", user.token);
		headers.put("address", selfToXML);
		Response.post(Response.ORDER, headers);
	
		return true;
	}
	
	public boolean create() throws APIBadResponseException, HTTPException, XMLParseException {
		String selfToXML;
		try {
			selfToXML = HTTPUtils.serializeObjectToXML(this);
			
		} catch (XMLParseException e) {
			e.printStackTrace();
			return false;
		}
		
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "CreateAddress");
		headers.put("username", user.username);
		headers.put("authentication_token", user.token);
		headers.put("address", selfToXML);
		Response r = Response.post(Response.ORDER, headers);
		
		this.id = r.address.id;
		return true;
	}
}
