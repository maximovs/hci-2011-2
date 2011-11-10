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
public class Language {
	
	@Attribute
	public int id;

	@Element
	public String code;
	
	@Element
	public String name;
	
	public static List<Language> getLanguageList() throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetLanguageList");
		Response r = Response.get(Response.COMMON, headers);
		// Optional: Some caching
		return r.languages;
	}
}
