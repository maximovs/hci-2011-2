package kwik.remote.api.auxiliar;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class OrderItem {
	@Element
	public int product_id;
	@Element
	public int count;
}
