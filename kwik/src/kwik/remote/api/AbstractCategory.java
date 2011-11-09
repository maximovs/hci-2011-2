package kwik.remote.api;

import java.util.List;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

public abstract class AbstractCategory {
	
	protected static final int NO_PARAM = -1;
	
	public abstract int getId();
	
	public abstract String getName();
	
	public abstract List<SubCategory> getSubCategoryList(int language_id) throws APIBadResponseException, XMLParseException, HTTPException;
	
	public List<Product> getProducts(int language_id) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(language_id, "DESC", NO_PARAM, NO_PARAM);
	}
	
	public List<Product> getProducts(int language_id, String order) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(language_id, order, NO_PARAM, NO_PARAM);
	}
	
	public List<Product> getProducts(int language_id, int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException {
		return getProducts(language_id, "DESC", items_per_page, page);
	}

	public abstract List<Product> getProducts(int language_id, String order, int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException;
	
	
}
