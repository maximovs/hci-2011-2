package kwik.remote.api;

import java.util.List;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

/*
 * Represents a basic instance of a Category.
 */
public abstract class AbstractCategory {
	
	protected static final int NO_PARAM = -1;
	
	/*
	 * getId
	 * @description Gets the ID of a given Category
	 */
	public abstract int getId();
	
	/*
	 * getParentId
	 * @description Gets the ID of the parent of the given Category,
	 * returns NO_PARAM if it's already a root category
	 */
	public abstract int getParentId();
	
	/*
	 * getName
	 * @description Gets the name of the category... redundant comments ftw.
	 */
	public abstract String getName();
	
	/*
	 * getSubCategoryList
	 * @description Fetches the subcategories for the given category and language, 
	 * returns null if it's already a subcategory
	 */
	public abstract List<SubCategory> getSubCategoryList(int language_id) throws APIBadResponseException, XMLParseException, HTTPException;
	
	/*
	 * Gets the list of products of the category with the given language
	 */
	public List<Product> getProducts(int language_id) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(language_id, "DESC", NO_PARAM, NO_PARAM);
	}
	
	/*
	 * Gets the list of products of the category with the given language and order
	 */
	public List<Product> getProducts(int language_id, String order) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(language_id, order, NO_PARAM, NO_PARAM);
	}
	
	/*
	 * Gets the list of products of the category with the given language, items per page and page
	 */
	public List<Product> getProducts(int language_id, int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(language_id, "DESC", items_per_page, page);
	}

	/*
	 * Gets the list of products of the category with the given language, order, items per page and page
	 */
	public abstract List<Product> getProducts(int language_id, String order, int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException;
	
	
}
