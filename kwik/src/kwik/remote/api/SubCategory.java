package kwik.remote.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.app.KwikApp;
import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/*
 * SubCategory
 * @description Represents an API subcategory
 */
@Element(name = "subcategory")
public class SubCategory extends AbstractCategory implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	@Attribute
	public int					id;
	
	@Element
	public int					category_id;
	
	@Element
	public String				code;
	
	@Element
	public String				name;
	
	/*
	 * @see kwik.remote.api.AbstractCategory#getId()
	 */
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	/*
	 * @see kwik.remote.api.AbstractCategory#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/*
	 * @see kwik.remote.api.AbstractCategory#getProducts(int, java.lang.String,
	 * int, int)
	 */
	@Override
	public List<Product> getProducts(String order, int items_per_page, int page, String criteria)
			throws APIBadResponseException, XMLParseException, HTTPException {
		
		if (!Response.FAKE_RESPONSE) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("method", "GetProductListBySubcategory");
			headers.put("language_id", Integer.toString(KwikApp.instance.getCurrentLanguage()));
			headers.put("category_id", Integer.toString(this.category_id));
			headers.put("subcategory_id", Integer.toString(this.id));
			
			headers.put("order", order);
			
			if (items_per_page != AbstractCategory.NO_PARAM && page != AbstractCategory.NO_PARAM) {
				headers.put("items_per_page", Integer.toString(items_per_page));
				headers.put("page", Integer.toString(page));
			}
			
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
	
	/*
	 * @see kwik.remote.api.AbstractCategory#getSubCategoryList(int)
	 */
	@Override
	public List<SubCategory> getSubCategoryList() throws APIBadResponseException, XMLParseException,
			HTTPException {
		return null; // A subcategory doesn't have any subcategories dude.
	}
	
	@Override
	public int getParentId() {
		return this.category_id;
	}
	
	public static final String[]	fields	= { "id", "code", "name" };
	
	public static List<? extends Map<String, ?>> getCategoriesAsMap(List<SubCategory> l) {
		
		List<Map<String, String>> transformedCategories = new ArrayList<Map<String, String>>();
		for (SubCategory c : l) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(fields[0], String.valueOf(c.getId()));
			map.put(fields[1], c.getCode());
			map.put(fields[2], c.getName());
			transformedCategories.add(map);
		}
		return transformedCategories;
	}
	
	public static String[] getMapKeys() {
		return fields;
	}
}
