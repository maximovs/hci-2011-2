package kwik.remote.api;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.auxiliar.Account;
import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.util.HTTPUtils;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Element(name = "account")
public class User implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1566957530120984763L;

	String	token;
	
	@Attribute(required = false)
	@Element(required = false)
	int		id;
	
	@Attribute(required = false)
	@Element(required = false)
	String	name;
	
	@Attribute(required = false)
	@Element(required = false)
	String	username;
	
	@Element(required = false)
	Date	created_date;
	
	@Element(required = false)
	Date	birth_date;
	
	@Element(required = false)
	String	email;
	
	@Element(required = false)
	Date	last_password_change;
	
	@Attribute(required = false)
	@Element(required = false)
	Date	last_login_date;
	

	public boolean signOut() {
		try {
			Map<String, String> headers = new HashMap<String, String>();
			
			headers.put("method", "SignOut");
			headers.put("username", this.username);
			headers.put("token", this.token);
			
			Response.get(Response.SECURITY, headers);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean changePassword(String old_password, String new_password) {
		if (old_password == new_password) {
			return false;
		}
		try {
			Map<String, String> headers = new HashMap<String, String>();
			
			headers.put("method", "ChangePassword");
			headers.put("username", this.username);
			headers.put("password", old_password);
			headers.put("new_password", new_password);
			
			Response.get(Response.SECURITY, headers);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean createAccount(String username, String name, String password, String email, Date birth_date) {
		Account account = new Account();
		account.username = username;
		account.name = name;
		account.password = password;
		account.email = email;
		account.birth_date = birth_date;
		try {
			String xmlAccount = HTTPUtils.serializeObjectToXML(account);
			
			Map<String, String> headers = new HashMap<String, String>();
			
			headers.put("method", "CreateAccount");
			headers.put("account", xmlAccount);
			
			Response.post(Response.SECURITY, headers);
			
			return true;
		} catch (XMLParseException e) {
			return false;
		} catch (APIBadResponseException e) {
			return false;
		} catch (HTTPException e) {
			return false;
		}
	}
	
	public static User getAccount(String username, String token) throws APIBadResponseException, XMLParseException,
			HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "GetAccount");
		headers.put("username", username);
		headers.put("authentication_token", token);
		
		Response r = Response.get(Response.SECURITY, headers);
		return r.account;
	}
	
	public boolean update() {
		Account account = new Account();
		account.username = username;
		account.name = name;
		account.email = email;
		account.birth_date = birth_date;
		
		try {
			String xmlAccount = HTTPUtils.serializeObjectToXML(account);
			
			Map<String, String> headers = new HashMap<String, String>();
			
			headers.put("method", "UpdateAccount");
			headers.put("username", username);
			headers.put("token", token);
			headers.put("account", xmlAccount);
			
			Response.post(Response.SECURITY, headers);
			
			return true;
		} catch (XMLParseException e) {
			return false;
		} catch (APIBadResponseException e) {
			return false;
		} catch (HTTPException e) {
			return false;
		}
	}
	
	public static User signIn(String username, String password) throws APIBadResponseException, XMLParseException, HTTPException {
		
		if (!Response.FAKE_RESPONSE) {
			Map<String, String> headers = new HashMap<String, String>();
			
			headers.put("method", "SignIn");
			headers.put("username", username);
			headers.put("password", password);
			
			Response r = Response.get(Response.SECURITY, headers);
			// Optional: Some caching
			
			r.authentication.user.token = r.authentication.token;
			
			return r.authentication.user;
		} else {
			User u = new User();
			u.username = "cristian";
			if (!password.equals("cristian")) {
				Error e = new Error();
				e.code = 1;
				e.message = "Invalid login";
				throw new APIBadResponseException(e);
			}
			u.id = 1;
			u.token = "abcd";
			u.name = "Cristian Pereyra";
			return u;
		}
	}
	
	public List<Address> getAddressList() throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "GetAddressList");
		headers.put("username", username);
		headers.put("authentication_token", token);
		
		Response r = Response.get(Response.SECURITY, headers);
		// Optional: Some caching
		for (Address addr : r.addresses) {
			addr.user = this;
		}
		return r.addresses;
	}
	
	public List<Order> getOrderList() throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "GetAddressList");
		headers.put("username", username);
		headers.put("authentication_token", token);
		
		Response r = Response.get(Response.SECURITY, headers);
		// Optional: Some caching
		for (Order order : r.orders) {
			order.user = this;
		}
		return r.orders;
	}
	
	public Address getAddress(int id) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "GetAddressList");
		headers.put("username", username);
		headers.put("authentication_token", token);
		headers.put("address_id", Integer.toString(id));
		
		Response r = Response.get(Response.SECURITY, headers);
		// Optional: Some caching
		r.address.user = this;
		
		return r.address;
	}
	
}
