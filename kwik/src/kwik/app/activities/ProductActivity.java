package kwik.app.activities;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import kwik.remote.api.Product;
import kwik.remote.util.DrawableManager;
import kwik.services.KwikAPIService;
import kwik.util.KwikResultReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class ProductActivity extends KwikFragmentActivity {
	
	
	private static DrawableManager imageManager = new DrawableManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent localIntent = this.getIntent();
		
		Bundle extras = localIntent.getExtras();
		
		
		final Integer product_id = extras.getInt("product_id", -1);
		final String  product_name = extras.getString("product_name");
		
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
					
					View progressBar = ((View)findViewById(R.id.progressbar));
					progressBar.setVisibility(View.GONE);
					
					
					View p = ((View)findViewById(R.id.product_view));
					p.setVisibility(View.VISIBLE);
					
					ImageView image = ((ImageView)findViewById(R.id.product_image));
					
					imageManager.fetchDrawableOnThread(prod.image_url, image);
				}
			}
		});
		
		
		
		startService(intent);
	}

}
