package kwik.remote.api;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.api.exceptions.APIBadResponseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Product {
	
	@Attribute
	public int		id;
	@Element
	public int		category_id;
	@Element
	public int		subcategory_id;
	@Element
	public String	name;
	@Element
	public int		sales_rank;
	@Element
	public Double	price;
	@Element
	public String	image_url;
	
	@Element(required = false)
	public String	type;
	
	@Element(required = false)
	public String	actors;
	@Element(required = false)
	public String	format;
	@Element(required = false)
	public String	language;
	@Element(required = false)
	public String	subtitles;
	@Element(required = false)
	public String	region;
	@Element(required = false)
	public String	aspect_ratio;
	@Element(required = false)
	public String	number_discs;
	@Element(required = false)
	public Date		release_date;
	@Element(required = false)
	public String	run_time;
	@Element(required = false)
	public String	ASIN;
	
	@Element(required = false)
	public String	authors;
	@Element(required = false)
	public String	publisher;
	@Element(required = false)
	public Date		published_date;
	@Element(required = false)
	public String	isbn_10;
	@Element(required = false)
	public String	isbn_13;
	
	public static Product getProduct(int product_id) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetProductById");
		headers.put("product_id", new Integer(product_id).toString());
		Response r = Response.get(Response.COMMON, headers);
		return r.product;
	}
}
