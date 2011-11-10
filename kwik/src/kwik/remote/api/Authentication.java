package kwik.remote.api;

import org.simpleframework.xml.Element;

@Element
public class Authentication {
	@Element
	public String 	token;
	
	@Element
	public User 	user;
}
