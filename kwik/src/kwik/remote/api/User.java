package kwik.remote.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

public class User {	
	
	int id;
	
	
	// TODO: Dejo esto a medias porque me tengo que ir, agrego categories.
	public static List<Language> signIn(String username, String password) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "SignIn");
		headers.put("username", username);
		headers.put("password", password);
		Response r = Response.get(Response.COMMON, headers);
		// Optional: Some caching
		return r.languages;
	}
}
