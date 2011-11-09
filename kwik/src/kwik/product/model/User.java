package kwik.product.model;

import java.net.URL;
import java.sql.Date;
import java.util.Queue;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class User {

	String token;
	int id;

	String username;
	String name;
	String password;
	String email;
	Date birth_date;
	Date created_date;
	Date last_login_date;
	Date last_password_change;

	public int signIn(String username, String password){

		/** Create a new textview array to display the results */
		/*TextView name[];
		TextView website[];
		TextView category[];
		 */


		String url="http://eiffel.itba.edu.ar/hci/service/Security.groovy?method=SignIn&username=" + username + "&password=" + password;
		/** Get result from UserXMLHandler SitlesList Object */
		Queue<String> info = parseUserXml(url);

		if(info!= null){
			if(info.poll().equals("ok")){
				token=info.poll();
				id=Integer.parseInt(info.poll());
				username=info.poll();
				name=info.poll();
				last_login_date=Date.valueOf(info.poll());
			}else{
				return Integer.parseInt(info.poll());
			}
		}

		return 0;
	}
	
	
	public int getAccount(){
		if(token==null || username == null)
			return -2;
		String url="http://eiffel.itba.edu.ar/hci/service/Security.groovy?method=GetAccount&username=" + username + "&authentication_token=" + token;
		/** Get result from UserXMLHandler SitlesList Object */
		Queue<String> info = parseUserXml(url);

		if(info!= null){
			if(info.poll().equals("ok")){
				username=info.poll();
				name=info.poll();
				email=info.poll();
				try{
					birth_date=Date.valueOf(info.poll());
					created_date=Date.valueOf(info.poll());
					last_login_date=Date.valueOf(info.poll());
					last_password_change=Date.valueOf(info.poll());
				}catch(Exception e){}
				return 0;
			}else
				return Integer.parseInt(info.poll());
		}
		return -1;
	}

	public int changePassword(String new_password){
		if(password==null || username == null)
			return -2;
		String url="http://eiffel.itba.edu.ar/hci/service/Security.groovy?method=ChangePassword&username=" + username + "&password=" + password + "&new_password=" + new_password;
		/** Get result from UserXMLHandler SitlesList Object */
		Queue<String> info = parseUserXml(url);

		if(info!= null){
			if(info.poll().equals("ok"))
				return 0;
			else
				return Integer.parseInt(info.poll());
		}
		return -1;
	}


	public int signOut(){
		if(token==null || username == null)
			return -2;
		String url="http://eiffel.itba.edu.ar/hci/service/Security.groovy?method=SignOut&username=" + username + "&authentication_token=" + token;
		/** Get result from UserXMLHandler SitlesList Object */
		Queue<String> info = parseUserXml(url);

		if(info!= null){
			if(info.poll().equals("ok"))
				return 0;
			else
				return Integer.parseInt(info.poll());
		}
		return -1;
	}


	public User(){


	}

	public User(String token, int id, String username, String name,
			String password, String email, Date birth_date, Date created_date,
			Date last_login_date, Date last_password_change) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.birth_date = birth_date;
		this.created_date = created_date;
		this.last_login_date = last_login_date;
		this.last_password_change = last_password_change;
	}

	public int updateAccount(String name, String email, String birth_date){
		//#TODO: POST methods
		return 0;
	}

	public int createAccount(String username, String name, String password, String email, String birth_date){
		//#TODO: POST methods
		return 0;
	}

	private Queue<String> parseUserXml(String url){
		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
			URL sourceUrl = new URL(url);

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			UserXMLHandler userXMLHandler = new UserXMLHandler();
			xr.setContentHandler(userXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));

		} catch (Exception e) {
			//System.out.println("XML Pasing Excpetion = " + e);
		}
		return UserXMLHandler.info;
	}

	/** Assign textview array lenght by arraylist size 
	name = new TextView[sitesList.getName().size()];
	website = new TextView[sitesList.getName().size()];
	category = new TextView[sitesList.getName().size()];
	 */

	/** Set the result text in textview and add it to layout 
	for (int i = 0; i < sitesList.getName().size(); i++) {
	name[i] = new TextView(this);
	name[i].setText("Name = "+sitesList.getName().get(i));
	website[i] = new TextView(this);
	website[i].setText("Website = "+sitesList.getWebsite().get(i));
	category[i] = new TextView(this);
	category[i].setText("Website Category = "+sitesList.getCategory().get(i));
	 */


}