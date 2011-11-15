package kwik.app.activities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import kwik.remote.api.Product;
import kwik.remote.util.DrawableManager;
import kwik.services.KwikAPIService;
import kwik.util.KwikResultReceiver;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ProductActivity extends KwikFragmentActivity {
	
	private static DrawableManager	imageManager	= new DrawableManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent localIntent = this.getIntent();
		
		Bundle extras = localIntent.getExtras();
		
		final Activity self = this;
		
		final Integer product_id = extras.getInt("product_id", -1);
		final String product_name = extras.getString("product_name");
		
		setTitle(product_name);
		
		setContentView(R.layout.product);
		
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this, KwikAPIService.class);
		
		intent.putExtra("command", KwikAPIService.GET_PRODUCT_CMD);
		intent.putExtra("product_id", product_id);
		
		intent.putExtra("receiver", new KwikResultReceiver(new Handler(), this) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				super.onReceiveResult(resultCode, resultData);
				if (resultCode == KwikAPIService.STATUS_OK) {
					Product prod = (Product) resultData.getSerializable("return");
					
					View progressBar = ((View) findViewById(R.id.progressbar));
					progressBar.setVisibility(View.GONE);
					
					View p = ((View) findViewById(R.id.product_view));
					p.setVisibility(View.VISIBLE);
					
					ImageView image = ((ImageView) findViewById(R.id.product_image));
					
					ListView li = ((ListView) findViewById(android.R.id.list));
					
					List<Map<String, ?>> items = new ArrayList<Map<String, ?>>();
					
					for (Field f : Product.class.getFields()) {
						try {
							if (!f.getName().toLowerCase().equals("id")
									&& !f.getName().toLowerCase().equals("image_url")
									&& !f.getName().toLowerCase().equals("category_id")
									&& !f.getName().toLowerCase().equals("subcategory_id")
									&& !f.getName().toLowerCase().equals("type") && f.get(prod) != null) {
								HashMap<String, Object> dataMap = new HashMap<String, Object>();
								Field label = R.string.class.getField("label_" + f.getName());
								
								Object o_value = (Object) f.get(prod);
								String value = null;
								if (!(o_value instanceof Date)) {
									value = o_value.toString();
									if (f.getName().equals("price")) {
										value += "$";
									} else if (f.getName().equals("run_time")) {
										value = String.format(getResources().getString(R.string.n_minutes), 
																Integer.parseInt(value));
									}

									dataMap.put("key", getResources().getString((Integer) label.get(null)));
									dataMap.put("value", value);
									
									items.add(dataMap);
								}
								
							}
						} catch (Exception e) {
							Log.d("FAIL", "Failed with " + f.getName());
							Log.d("FAIL", e.toString());
						}
					}
					
					li.setAdapter(new SimpleAdapter(self, items, R.layout.item_list_product_data, new String[] { "key",
							"value" }, new int[] { R.id.key, R.id.value }));
					
					imageManager.fetchDrawableOnThread(prod.image_url, image);
				}
			}
		});
		
		startService(intent);
	}
	
}
