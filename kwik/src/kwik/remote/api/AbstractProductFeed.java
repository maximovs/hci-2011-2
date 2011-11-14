package kwik.remote.api;

import java.util.List;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

public abstract class AbstractProductFeed {
	protected static final int NO_PARAM = -1;
	
	/*
	 * Gets the list of products of the category with the given language and criteria
	 */
	public List<Product> getProducts(String order, String criteria) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(order, NO_PARAM, NO_PARAM, criteria);
	}
	
	/*
	 * Gets the list of products of the category with the given language
	 */
	public List<Product> getProducts() throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts("DESC", NO_PARAM, NO_PARAM, null);
	}
	
	/*
	 * Gets the list of products of the category with the given language and order
	 */
	public List<Product> getProducts(String order) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(order, NO_PARAM, NO_PARAM, null);
	}
	
	/*
	 * Gets the list of products of the category with the given language, items per page and page
	 */
	public List<Product> getProducts(int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts("DESC", items_per_page, page, null);
	}

	/*
	 * Gets the list of products of the category with the given language, order, items per page and page
	 */
	public abstract List<Product> getProducts(String order, int items_per_page, int page, String criteria) throws APIBadResponseException, XMLParseException, HTTPException;
}
