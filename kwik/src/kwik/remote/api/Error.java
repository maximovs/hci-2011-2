package kwik.remote.api;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Error {
	@Attribute
	int code;
	
	@Attribute
	String message;
}
