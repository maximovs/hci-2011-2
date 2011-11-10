package kwik.remote.api.auxiliar;

import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Account {
	
	@Element
	public String	username;
	
	@Element
	public String	name;
	
	@Element
	public String 	password;
	
	@Element
	public String	email;
	
	@Element
	public Date		birth_date;
}
