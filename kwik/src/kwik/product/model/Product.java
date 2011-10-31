package kwik.product.model;

import java.net.URL;
import java.sql.Date;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class Product {
	int product_id;
	int category_id;
	int subcategory_id;
	String name;
	int sales_rank;
	Double price;
	String image_url;
	
	String type;
	
	public Product(int product_id, int category_id, int subcategory_id,
			String name, int sales_rank, Double price, String image_url,
			String type) {
		super();
		this.product_id = product_id;
		this.category_id = category_id;
		this.subcategory_id = subcategory_id;
		this.name = name;
		this.sales_rank = sales_rank;
		this.price = price;
		this.image_url = image_url;
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public static Product getProduct(int product_id){
		String url = "http://eiffel.itba.edu.ar/hci/service/Catalog.groovy?method=GetProduct&product_id=" + product_id;
		HashMap<String,String> info = parseProductXml(url);
		if(info.get("type").equals("book")){
			return new BookProduct(Integer.parseInt(info.get("product_id")),Integer.parseInt(info.get("category_id")),Integer.parseInt(info.get("subcateogry_id")),info.get("name")
					,Integer.parseInt(info.get("sales_rank")),Double.parseDouble(info.get("price")),info.get("image_url"),info.get("type"),info.get("authors"),info.get("publisher")
					,Date.valueOf(info.get("published_date")),info.get("isbn_10"),info.get("isbn_13"),info.get("language"));
			
		} else if(info.get("type").equals("movie")){
			return new MovieProduct(Integer.parseInt(info.get("product_id")),Integer.parseInt(info.get("category_id")),Integer.parseInt(info.get("subcateogry_id")),info.get("name")
					,Integer.parseInt(info.get("sales_rank")),Double.parseDouble(info.get("price")),info.get("image_url"),info.get("type"),info.get("number_discs")
					,info.get("actors"),info.get("format"),info.get("language"),info.get("subtitles"),info.get("region"),info.get("aspect_ratio")
					,Date.valueOf(info.get("release_date")),info.get("run_time"),info.get("ASIN"));
		
		} else if(info.get("type").equals("error")){
			return null;
		}
		return null;
	}
	/**
    // We couldn't find a way to make inheritance work on a model....
    if ($(data).find("ISBN_10").length) {
   book
    } else if ($(data).find("ASIN").length) {

        this.type = "movie";
	 */

	private static HashMap<String, String> parseProductXml(String url){
		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
			URL sourceUrl = new URL(url);

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			ProductXMLHandler productXMLHandler = new ProductXMLHandler();
			xr.setContentHandler(productXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));

		} catch (Exception e) {
			//System.out.println("XML Pasing Excpetion = " + e);
		}
		return ProductXMLHandler.info;
	}
}
