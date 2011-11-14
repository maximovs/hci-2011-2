package kwik.remote.api;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Error implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4058459205376873903L;

	@Attribute
	public int code;
	
	@Attribute
	public String message;
}
