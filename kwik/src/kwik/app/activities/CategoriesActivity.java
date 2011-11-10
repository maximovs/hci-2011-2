package kwik.app.activities;


import java.util.HashMap;
import java.util.List;

import kwik.Util;
import kwik.app.R;
import kwik.remote.api.Category;
import kwik.remote.api.SubCategory;
import kwik.services.KwikAPIService;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CategoriesActivity extends ListActivity implements OnItemClickListener {

	private String TAG = getClass().getSimpleName();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		/* Asociamos la vista del search list con la activity */
		setContentView(R.layout.categories_list);

		
		
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				KwikAPIService.class);
		final boolean subcategory_activity;
		
		if	(this.getIntent() != null && this.getIntent().getExtras() != null) {
			intent.putExtra("category_id", this.getIntent().getExtras().getInt("category_id"));
			intent.putExtra("command", KwikAPIService.GET_SUBCATEGORIES_CMD);
			subcategory_activity = true;
		}
		else {
			subcategory_activity = false;
			intent.putExtra("command", KwikAPIService.GET_CATEGORIES_CMD);
		}
		
		
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
				if (resultCode == KwikAPIService.STATUS_OK) {

					Log.d(TAG, "OK - se recibieron las categorias");

					
					if (!subcategory_activity) {
						@SuppressWarnings("unchecked")
						List<Category> catList = (List<Category>) resultData.getSerializable("return");
						populateCatList(catList);
					} else {
						@SuppressWarnings("unchecked")
						List<SubCategory> catList = (List<SubCategory>) resultData.getSerializable("return");
						populateSubCatList(catList);
					}

				} else if (resultCode == KwikAPIService.STATUS_CONNECTION_ERROR) {
					Log.d(TAG, "Connection error.");
				} else {
					Log.d(TAG, "Unknown error.");
				}
			}

		});
		
		ListView vi = getListView();
		
		vi.setOnItemClickListener(this);
		startService(intent);
	}
	
	private void populateSubCatList(List<SubCategory> categories) {
		String[] map_fields = { "name", "id", "category_id" };
		String[] desired_fields = { "name" };
		
		
		ListAdapter adapter = new SimpleAdapter(this,
				Util.getMapped(categories, map_fields), R.layout.search_item,
				desired_fields , new int[] { R.id.title });
		
		setListAdapter(adapter);
	}
	private void populateCatList(List<Category> categories) {
	
		
		String[] map_fields = { "name", "id", "category_id" };
		String[] desired_fields = { "name" };
		
		
		ListAdapter adapter = new SimpleAdapter(this,
				Util.getMapped(categories, map_fields), R.layout.search_item,
				desired_fields , new int[] { R.id.title });
		
		setListAdapter(adapter);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		ListView vi = (ListView) arg0;
		@SuppressWarnings("unchecked")
		HashMap<String,Object> map = (HashMap<String,Object>) vi.getItemAtPosition(position);
		
		Integer id = (Integer) map.get("id");
		Integer category_id = (Integer) map.get("category_id");

		if(category_id != null) {
			
		}
		else {
			Intent intent = new Intent(arg1.getContext(), CategoriesActivity.class);
			intent.putExtra("category_id", id);
			startActivity(intent);
		}
		
	}

	
}
