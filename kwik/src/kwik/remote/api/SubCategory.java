package kwik.remote.api;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class SubCategory {
	@Attribute
	int id;
	
	@Element
	int category_id;
	
	@Element
	String code;
	
	@Element
	String name;
}
