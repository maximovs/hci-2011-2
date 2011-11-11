package kwik.remote.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.auxiliar.OrderItem;
import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.util.Util;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Element
public class Order {
	
	public static Order createOrder(User u) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "CreateOrder");
		headers.put("username", u.username);
		headers.put("authentication_token", u.token);
		
		Response r = Response.post(Response.ORDER, headers);
		
		return getOrder(r.order.id, u);
	}
	
	public static Order getOrder(int order_id, User user) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "GetOrder");
		headers.put("username", user.username);
		headers.put("authentication_token", user.token);
		
		Response r = Response.post(Response.ORDER, headers);
		
		r.order.user = user;
		
		
		return r.order;
	}
	
	
	@Element
	@Attribute
	public int id;

	@Element
	public int address_id;
	
	@Element
	public int status;
	
	@Element
	public Date created_date;
	
	@Element
	public Date confirmed_date;
	
	@Element
	public Date shipped_date;
	
	@Element
	public Date delivered_date;
	
	@Element
	public double latitude;
	
	@Element
	public double longitude;
	
	@ElementList
	List<Item> items;
	
	User user;
	
	public boolean addItem(Product p, int count) throws XMLParseException, APIBadResponseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		
		OrderItem item = new OrderItem();
		
		item.product_id = p.id;
		item.count = count;
		
		headers.put("method", "AddOrderItem");
		headers.put("username", this.user.username);
		headers.put("authentication_token", this.user.token);
		headers.put("order_id", Integer.toString(this.id));
		headers.put("order_item", Util.serializeObjectToXML(item));
		
		Response.post(Response.ORDER, headers);		

		return true;
	}
	
	public boolean deleteItem(Product p, int count) throws XMLParseException, APIBadResponseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		
		OrderItem item = new OrderItem();
		
		item.product_id = p.id;
		item.count = count;
		
		headers.put("method", "DeleteOrderItem");
		headers.put("username", this.user.username);
		headers.put("authentication_token", this.user.token);
		headers.put("order_id", Integer.toString(this.id));
		headers.put("order_item", Util.serializeObjectToXML(item));
		
		Response.post(Response.ORDER, headers);		

		return true;
	}
	
	public boolean confirm() throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "ConfirmOrder");
		headers.put("username", this.user.username);
		headers.put("authentication_token", this.user.token);
		headers.put("order_id", Integer.toString(this.id));
		headers.put("address_id", Integer.toString(this.address_id));
		
		Response.post(Response.ORDER, headers);		

		return true;
	}
	
	public boolean changeAddress(Address address) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "ChangeOrderAddress");
		headers.put("username", this.user.username);
		headers.put("authentication_token", this.user.token);
		headers.put("order_id", Integer.toString(this.id));
		headers.put("address_id", Integer.toString(address.id));
		
		Response.post(Response.ORDER, headers);		

		return true;		
	}
	
	public boolean delete() throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.put("method", "DeleteOrder");
		headers.put("username", this.user.username);
		headers.put("authentication_token", this.user.token);
		headers.put("order_id", Integer.toString(this.id));
		
		Response.post(Response.ORDER, headers);		

		return true;
	}
}
