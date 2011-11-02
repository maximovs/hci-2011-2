package kwik.services;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import kwik.product.model.CategoriesXMLHandler;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class GetCategoriesService extends IntentService {

	private final String TAG = getClass().getSimpleName();

	public static final String GET_CATEGORIES_CMD = "GetCategories";

	public static final int STATUS_CONNECTION_ERROR = -1;
	public static final int STATUS_ERROR = -2;
	public static final int STATUS_ILLEGAL_ARGUMENT = -3;
	public static final int STATUS_OK = 0;

	/*
	 * Se debe crear un constructor sin parametros y asignarle un nombre al
	 * servicio.
	 */
	public GetCategoriesService() {
		super("GetCategoriesService");
	}

	/*
	 * Evento que se ejecuta cuando se invocó el servicio por medio de un
	 * Intent.
	 */
	@Override
	protected void onHandleIntent(final Intent intent) {
		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		final String command = intent.getStringExtra("command");

		final Bundle b = new Bundle();
		try {
			if (command.equals(GET_CATEGORIES_CMD)) {
				getCategories(receiver, b);
			} 
		} catch (SocketTimeoutException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_CONNECTION_ERROR, b);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ERROR, b);
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ERROR, b);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ILLEGAL_ARGUMENT, b);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ERROR, b);
		}

		// Es importante terminar el servicio lo antes posible.
		this.stopSelf();
	}

	private void getCategories(ResultReceiver receiver, Bundle b) throws ClientProtocolException, IOException, JSONException {
		//final DefaultHttpClient client = new DefaultHttpClient();
		//final HttpResponse response = client.execute(new HttpGet("http://eiffel.itba.edu.ar/hci/service/Catalog.groovy?method=GetCategoryList&language_id=1"));
	//	if ( response.getStatusLine().getStatusCode() != 200 ) {
	//		throw new IllegalArgumentException(response.getStatusLine().toString());
	//	}
		//----
		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
			URL sourceUrl = new URL("http://eiffel.itba.edu.ar/hci/service/Catalog.groovy?method=GetCategoryList&language_id=1");

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			CategoriesXMLHandler categoriesXMLHandler = new CategoriesXMLHandler();
			xr.setContentHandler(categoriesXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));

		} catch (Exception e) {
			
			//System.out.println("XML Pasing Excpetion = " + e);
		}
		//return ProductXMLHandler.info;
		//----
		
		
		b.putSerializable("return", CategoriesXMLHandler.info);

		receiver.send(STATUS_OK, b);
	}
	/*
	private List<Tweet> fromJSONtoTweets(final String jsonToParse) throws JSONException {
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		Log.d(TAG, "Json received: " + jsonToParse);
		
		JSONObject parsedJson = new JSONObject(jsonToParse);
		if ( !parsedJson.has("results")) {
			throw new JSONException("results not found");
		}
		
		JSONArray results = parsedJson.getJSONArray("results");
		for ( int i = 0; i < results.length(); i++ ) {
			JSONObject bornToBeTweet = results.getJSONObject(i);
			tweets.add(TweetImpl.fromJSON(bornToBeTweet));			
		}
		
		return tweets;
	}*/

}
