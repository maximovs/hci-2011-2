package kwik.user.activities;


import java.util.List;

import kwik.remote.api.Category;
import kwik.services.GetCategoriesService;

import twitter.search.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class CategoriesActivity extends ListActivity {

	private String TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Asociamos la vista del search list con la activity */
		setContentView(R.layout.categories_list);

		Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				GetCategoriesService.class);

		intent.putExtra("command", GetCategoriesService.GET_CATEGORIES_CMD);
		/* Se pasa un callback (ResultReceiver), con el cual se procesará la
		 * respuesta del servicio. Si se le pasa null como parámetro del constructor
		 * se usa uno de los threads disponibles del proceso. Dado que en el procesamiento
		 * del mismo se debe modificar la UI, es necesario que ejecute con el thread de UI.
		 * Es por eso que se lo instancia con un objeto Handler (usando el el thread de UI
		 * para ejecutarlo).
		 */
		intent.putExtra("receiver", new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				super.onReceiveResult(resultCode, resultData);
				if (resultCode == GetCategoriesService.STATUS_OK) {

					Log.d(TAG, "OK - se recibieron las categorias");

					@SuppressWarnings("unchecked")
					List<Category> catList = (List<Category>) resultData.getSerializable("return");
					
					populateList(catList);

				} else if (resultCode == GetCategoriesService.STATUS_CONNECTION_ERROR) {
					Log.d(TAG, "Connection error.");
				} else {
					Log.d(TAG, "Unknown error.");
				}
			}

		});
		startService(intent);
	}
	
	private void populateList(List<Category> l) {
		ListAdapter adapter = new SimpleAdapter(this,
				Category.getCategoriesAsMap(l), R.layout.search_item,
				Category.fields, new int[] { R.id.date,
			R.id.title, R.id.description });
		
		setListAdapter(adapter);	
	}
	
}
