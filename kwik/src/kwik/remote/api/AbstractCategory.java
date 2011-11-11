package kwik.remote.api;

import java.util.List;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

/*
 * Represents a basic instance of a Category.
 */
public abstract class AbstractCategory extends AbstractProductFeed {
	
	
	
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
	public abstract List<? extends AbstractCategory> getSubCategoryList(int language_id) throws APIBadResponseException, XMLParseException, HTTPException;
	

	public abstract String getCode();

	
	
}
