package kwik.app.activities;

import java.util.HashMap;
import java.util.List;

import kwik.Util;
import kwik.app.R;
import kwik.remote.api.Category;
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

public class ProductsActivity extends ListActivity implements OnItemClickListener {
	
	private String TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent localIntent = this.getIntent();
		
		Bundle extras = localIntent.getExtras();
		
		final Integer category_id = extras.getInt("category_id");
		final Integer subcategory_id = extras.getInt("subcategory_id");
		
		/* Asociamos la vista del search list con la activity */
		setContentView(R.layout.categories_list);
		
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this, KwikAPIService.class);
		
		if (subcategory_id != null) {
			intent.putExtra("command", KwikAPIService.GET_SUBCAT_PRODUCTS_CMD);
			intent.putExtra("category_id", category_id);
			intent.putExtra("subcategory_id", subcategory_id);
		} else {
			// Falta esto.
			intent.putExtra("command", KwikAPIService.GET_CAT_PRODUCTS_CMD);
			intent.putExtra("category_id", category_id);
		}
		
		intent.putExtra("receiver", new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				super.onReceiveResult(resultCode, resultData);
				if (resultCode == KwikAPIService.STATUS_OK) {

					Log.d(TAG, "OK - se recibieron las categorias");
					
						@SuppressWarnings("unchecked")
						List<Category> prodList = (List<Category>) resultData.getSerializable("return");
						populateProdList(prodList);
			
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

	private void populateProdList(List<Category> products) {
		String[] map_fields = { "name", "id" };
		String[] desired_fields = { "name" };
		
		
		ListAdapter adapter = new SimpleAdapter(this,
				Util.getMapped(products, map_fields), R.layout.search_item,
				desired_fields , new int[] { R.id.title });
		
		setListAdapter(adapter);		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		ListView vi = (ListView) arg0;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) vi.getItemAtPosition(position);
		
		Integer id = (Integer) map.get("id");
		
		Intent intent = new Intent(arg1.getContext(), CategoriesActivity.class);
		intent.putExtra("category_id", id);
		startActivity(intent);
	}
}
