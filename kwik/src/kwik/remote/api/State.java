package kwik.remote.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.api.exceptions.APIBadResponseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class State {
	@Attribute
	int id;
	@Element
	int country_id;
	@Element
	String code;
	@Element
	String name;
	
	public static List<State> getStateList(int language_id, int country_id) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetStateList");
		headers.put("language_id", Integer.toString(language_id));
		headers.put("country_id", Integer.toString(country_id));
		Response r = Response.get(Response.COMMON, headers);
		// Optional: Some caching
		return r.states;
	}
}
