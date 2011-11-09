package kwik.remote.api;

import java.util.List;

public abstract class AbstractCategory {
	
	private static final int NO_PARAM = -1;
	
	public abstract int getId();
	
	public abstract String getName();
	
	public List<Product> getProducts(int language_id) {
		return getProducts(language_id, "DESC", NO_PARAM, NO_PARAM);
	}
	
	public List<Product> getProducts(int language_id, String order) {
		return getProducts(language_id, order, NO_PARAM, NO_PARAM);
	}
	
	public List<Product> getProducts(int language_id, int items_per_page, int page) {
		return getProducts(language_id, "DESC", items_per_page, page);
	}

	public abstract List<Product> getProducts(int language_id, String order, int items_per_page, int page);
	

}
