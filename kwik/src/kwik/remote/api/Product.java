package kwik.remote.api;

import java.sql.Date;

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
	
	public static Product getProduct(int product_id) throws ResponseException {
		String url = "http://eiffel.itba.edu.ar/hci/service/Catalog.groovy?method=GetProduct&product_id=" + product_id;
		Response r = Response.get(url);
		return r.product;
	}
}
