package kwik.remote.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Category extends AbstractCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Attribute
	public int id;
	
	@Element
	public String code;
	
	@Element
	public String name;
	
	/*
	 * getCategoryList
	 * @description Returns a list of categories with their names setted to the given language.
	 */
	public static List<Category> getCategoryList(int language_id) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetCategoryList");
		headers.put("language_id", Integer.toString(language_id));
		
		Response r = Response.get(Response.CATALOG, headers);
		// Optional: Some caching
		return r.categories;
	}
	
	
	/*
	 * @see kwik.remote.api.AbstractCategory#getSubCategoryList(int)
	 */
	public List<? extends AbstractCategory> getSubCategoryList(int language_id) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetSubcategoryList");
		headers.put("language_id", Integer.toString(language_id));
		headers.put("category_id", Integer.toString(this.id));
		
		Response r = Response.get(Response.CATALOG, headers);
		// Optional: Some caching
		return r.subCategories;		
	}
	

	/*
	 * @see kwik.remote.api.AbstractCategory#getId()
	 */
	@Override
	public int getId() {
		return id;
	}
	
	/*
	 * @see kwik.remote.api.AbstractCategory#getId()
	 */
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
	 * @see kwik.remote.api.AbstractCategory#getProducts(int, java.lang.String, int, int)
	 */
	@Override
	public List<Product> getProducts(int language_id, String order, int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetSubcategoryList");
		headers.put("language_id", Integer.toString(language_id));
		headers.put("category_id", Integer.toString(this.id));

		headers.put("order", order);
		
		if (items_per_page != AbstractCategory.NO_PARAM && page != AbstractCategory.NO_PARAM) {
			headers.put("items_per_page", Integer.toString(items_per_page));
			headers.put("page", Integer.toString(page));			
		}
		
		Response response = Response.get(Response.CATALOG, headers);
		return response.products;
	}
	
	@Override
	public int getParentId() {
		return AbstractCategory.NO_PARAM;
	}
}
