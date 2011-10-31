/**This file is used to handle the XML tags. So we need to extends with DefaultHandler.
we need to override startElement, endElement & characters method .
startElemnt method called when the tag starts.
endElemnt method called when the tag ends
characres method to get characters inside tag.

[sourcecode language="java"]
 */
package kwik.user.model;

import java.util.LinkedList;
import java.util.Queue;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static Queue<String> info = null;

	public static Queue<String> getInfo() {
		return info;
	}

	public static void setUser(Queue<String> info) {
		UserXMLHandler.info = info;
	}


	/** Called when tag starts ( ex:- <name>AndroidPeople</name>
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;
		
		if (localName.equals("response"))
		{
			info = new LinkedList<String>();
			if(attributes.getValue("status").equals("ok")){
				/** Start */
				info.offer("ok");
			}else{
				info.offer("error");
				info.offer(attributes.getValue("code"));
			}
		}else if(info!=null){ 
			/**
			 * <account id="1">
			<username>hci</username>
			<name>HCI 2009</name> <email>hci@it.itba.edu.ar </email> <birth_date>2009-08-18</birth_date> <created_date>2009-08-18</created_date> <last_login_date>2009-08-21</last_login_date> <last_password_change/>
  			</account>*/
			if (localName.equals("user")) {
				/** Get attribute value */
				info.offer(attributes.getValue("id"));
				info.offer(attributes.getValue("name"));
				info.offer(attributes.getValue("last_login_date"));
			} else if(localName.equals("account")){
				info.offer(attributes.getValue("id"));
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
			if (localName.equalsIgnoreCase("token")){
				info.offer(currentValue);

			}	else if (localName.equalsIgnoreCase("username")){
				info.offer(currentValue);
			}	else if (localName.equalsIgnoreCase("name")){
				info.offer(currentValue);
			}	else if (localName.equalsIgnoreCase("email")){
				info.offer(currentValue);
			}	else if (localName.equalsIgnoreCase("birth_date")){
				info.offer(currentValue);
			}	else if (localName.equalsIgnoreCase("created_date")){
				info.offer(currentValue);
			}	else if (localName.equalsIgnoreCase("last_login_date")){
				info.offer(currentValue);
			}	else if (localName.equalsIgnoreCase("last_password_change")){
				info.offer(currentValue);
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

