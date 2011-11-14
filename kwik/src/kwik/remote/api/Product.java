package kwik.remote.api;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Product extends AbstractProductFeed implements Serializable {
	
	private static final long	serialVersionUID	= 5782315891664949385L;
	
	@Attribute
	public int					id;
	@Element
	public int					category_id;
	@Element
	public int					subcategory_id;
	@Element
	public String				name;
	@Element
	public int					sales_rank;
	@Element
	public Double				price;
	@Element
	public String				image_url;
	
	@Element(required = false)
	public String				type;
	
	@Element(required = false)
	public String				actors;
	@Element(required = false)
	public String				format;
	@Element(required = false)
	public String				language;
	@Element(required = false)
	public String				subtitles;
	@Element(required = false)
	public String				region;
	@Element(required = false)
	public String				aspect_ratio;
	@Element(required = false)
	public String				number_discs;
	@Element(required = false)
	public Date					release_date;
	@Element(required = false)
	public String				run_time;
	@Element(required = false)
	public String				ASIN;
	
	@Element(required = false)
	public String				authors;
	@Element(required = false)
	public String				publisher;
	@Element(required = false)
	public Date					published_date;
	@Element(required = false)
	public String				isbn_10;
	@Element(required = false)
	public String				isbn_13;
	
	public static Product getProduct(int product_id) throws APIBadResponseException, XMLParseException, HTTPException {
		if (!Response.FAKE_RESPONSE) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("method", "GetProduct");
			headers.put("product_id", new Integer(product_id).toString());
			Response r = Response.get(Response.CATALOG, headers);
			return r.product;
		} else {
			Product p = new Product();
			p.name = "Harabara";
			p.id = 1;
			p.price = 14.99;
			p.sales_rank = 1;
			p.image_url = "http://2.bp.blogspot.com/_a_kfggseBks/TNEnSu7kT-I/AAAAAAAAA4I/DOLr4FWLcm8/s1600/Pink_Floyd_The_Wall_Scream.jpg";
			return p;
		}
		
	}
	
	@Override
	public List<Product> getProducts(String order, int items_per_page, int page, String criteria)
			throws APIBadResponseException, XMLParseException, HTTPException {
		if (!Response.FAKE_RESPONSE) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("method", "GetProductListByName");
			headers.put("criteria", criteria);
			Response r = Response.get(Response.CATALOG, headers);
			return r.products;
		} else {
			List<Product> prods = new ArrayList<Product>();
			
			Product p = new Product();
			p.name = "Harabara";
			p.id = 1;
			p.price = 14.99;
			p.sales_rank = 1;
			
			prods.add(p);
			
			return prods;
		}
	}
	
}
