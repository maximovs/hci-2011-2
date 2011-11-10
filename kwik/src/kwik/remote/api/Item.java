package kwik.remote.api;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Item {
	@Attribute
	public int 		id;
	
	@Element
	public int 		product_id;
	
	@Element
	public int 		count;
	
	@Element
	public double 	price;
}