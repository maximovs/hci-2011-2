package kwik.services;

import java.io.Serializable;
import java.util.List;

import kwik.remote.api.Category;
import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class GetCategoriesService extends IntentService {

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
		if (command.equals(GET_CATEGORIES_CMD)) {
			getCategories(receiver, b);
		} 


		// Es importante terminar el servicio lo antes posible.
		this.stopSelf();
	}

	private void getCategories(ResultReceiver receiver, Bundle b)  {

		List<Category> categories = null;
		
		try {
			categories = Category.getCategoryList(1);
		} catch (APIBadResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HTTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		b.putSerializable("return", (Serializable) categories);

		receiver.send(STATUS_OK, b);
	}


}
