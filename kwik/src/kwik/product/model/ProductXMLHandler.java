/**This file is used to handle the XML tags. So we need to extends with DefaultHandler.
we need to override startElement, endElement & characters method .
startElemnt method called when the tag starts.
endElemnt method called when the tag ends
characres method to get characters inside tag.

[sourcecode language="java"]
 */
package kwik.product.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProductXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static HashMap<String,String> info = null;

	public static HashMap<String,String> getInfo() {
		return info;
	}

	public static void setUser(HashMap<String,String> info) {
		ProductXMLHandler.info = info;
	}


	/** Called when tag starts ( ex:- <name>AndroidPeople</name>
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("response"))
		{
			info = new HashMap<String,String>();
			if(attributes.getValue("status").equals("ok")){
				/** Start */
				info.put("status", "ok");
			}else{
				info.put("status", "error");
				info.put("code",attributes.getValue("code"));
			}
		}else if(info!=null){ 

			if (localName.equals("product")) {
				/** Get attribute value */
				info.put("product_id",attributes.getValue("id"));
			}
		}
	}

	/** Called when tag closing ( ex:- <name>AndroidPeople</name>
	 * -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;
		if(info!=null){
			/** set value */
			if (localName.equalsIgnoreCase("category_id")){
				info.put("category_id",currentValue);
			}	else if (localName.equalsIgnoreCase("subcategory_id")){
				info.put("subcategory_id",currentValue);
			}	else if (localName.equalsIgnoreCase("name")){
				info.put("name",currentValue);
			}	else if (localName.equalsIgnoreCase("sales_rank")){
				info.put("sales_rank",currentValue);
			}	else if (localName.equalsIgnoreCase("price")){
				info.put("price",currentValue);
			}	else if (localName.equalsIgnoreCase("image_url")){
				info.put("image_url",currentValue);
			}	else if (localName.equalsIgnoreCase("actors")){
				info.put("actors",currentValue);
			}	else if (localName.equalsIgnoreCase("format")){
				info.put("format",currentValue);
			}	else if (localName.equalsIgnoreCase("language")){
				info.put("language",currentValue);
			}	else if (localName.equalsIgnoreCase("subtitles")){
				info.put("subtitles",currentValue);
			}	else if (localName.equalsIgnoreCase("region")){
				info.put("region",currentValue);
			}	else if (localName.equalsIgnoreCase("aspect_ratio")){
				info.put("aspect_ratio",currentValue);
			}	else if (localName.equalsIgnoreCase("number_discs")){
				info.put("number_discs",currentValue);
			}	else if (localName.equalsIgnoreCase("run_time")){
				info.put("run_time",currentValue);
			}	else if (localName.equalsIgnoreCase("ASIN")){
				info.put("ASIN",currentValue);
				info.put("type","movie");
			}	else if (localName.equalsIgnoreCase("authors")){
				info.put("authors",currentValue);
			}	else if (localName.equalsIgnoreCase("publisher")){
				info.put("publisher",currentValue);
			}	else if (localName.equalsIgnoreCase("published_date")){
				info.put("published_date",currentValue);
			}	else if (localName.equalsIgnoreCase("ISBN_10")){
				info.put("ISBN_10",currentValue);
				info.put("type","book");
			}	else if (localName.equalsIgnoreCase("ISBN_13")){
				info.put("ISBN_13",currentValue);
			}

		}
	}

	/** Called to get tag characters ( ex:- <name>AndroidPeople</name>
	 * -- to get AndroidPeople Character ) */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

}

