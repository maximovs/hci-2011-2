package kwik.remote.api;

import org.simpleframework.xml.Element;

@Element
public class Authentication {
	@Element
	String 	token;
	
	@Element
	User 	user;
}
