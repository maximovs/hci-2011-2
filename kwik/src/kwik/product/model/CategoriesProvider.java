package kwik.product.model;

import java.util.List;
import java.util.Map;

public interface CategoriesProvider {
	
	public List<Category> getCategories();

	public List<? extends Map<String, ?>> getCategoriesAsMap();

	public String[] getMapKeys();
}
