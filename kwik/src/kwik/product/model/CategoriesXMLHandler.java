/**This file is used to handle the XML tags. So we need to extends with DefaultHandler.
we need to override startElement, endElement & characters method .
startElemnt method called when the tag starts.
endElemnt method called when the tag ends
characres method to get characters inside tag.

[sourcecode language="java"]
 */
package kwik.product.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class CategoriesXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static HashMap<String,Category> info = null;
	String currentCategory=null;

	public static HashMap<String,Category> getInfo() {
		return info;
	}

	public static void setInfo(HashMap<String,Category> info) {
		CategoriesXMLHandler.info = info;
	}
	
	public static final String[] fields = { "id", "code", "name" };

	public static List<? extends Map<String, ?>> getCategoriesAsMap() {
		List<Category> catList = new LinkedList<Category>();
		for(String key: info.keySet()){
			catList.add(info.get(key));
		}
		List<Map<String, String>> transformedCategories = new ArrayList<Map<String, String>>();
		for (Category c : catList) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(fields[0], c.getId());
			map.put(fields[1], c.getCode());
			map.put(fields[2], c.getName());
			transformedCategories.add(map);
		}
		return transformedCategories;
	}

	
	public static String[] getMapKeys() {
		return fields;
	}
	

	/** Called when tag starts ( ex:- <name>AndroidPeople</name>
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		
		Log.e("Report","Localname:" + localName + " qname:" + qName);
		if (localName.equals("response"))
		{
			info = new HashMap<String,Category>();
			/*if(attributes.getValue("status").equals("ok")){
				// Start 
				info.put("status", "ok");
			}else{
				info.put("status", "error");
				info.put("code",attributes.getValue("code"));
			}*/
		}else if(info!=null){ 

			if (localName.equals("category")) {
				/** Get attribute value */
				currentCategory=attributes.getValue("id");
				info.put(currentCategory, new Category());
				info.get(currentCategory).setId(currentCategory);
			}
		}
	}

	/** Called when tag closing ( ex:- <name>AndroidPeople</name>
	 * -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;
		if(info!=null && currentCategory!=null){
			/** set value */
			if (localName.equalsIgnoreCase("code")){
				info.get(currentCategory).setCode(currentValue);
			}	else if (localName.equalsIgnoreCase("name")){
				info.get(currentCategory).setName(currentValue);
			}

		}
	}

}

