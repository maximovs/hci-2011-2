package kwik.app.activities;

import kwik.app.KwikApp;
import kwik.app.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.MenuItem.OnMenuItemClickListener;

public class SplashActivity extends FragmentActivity {
	
	public static final int	STOP			= 0;
	
	private Handler			splashHandler	= new Handler() {
												@Override
												public void handleMessage(Message msg) {
													switch (msg.what) {
														case SplashActivity.STOP:
															Intent intent = new Intent(SplashActivity.this,
																	CategoriesActivity.class);
															
															// startActivity(intent);
															// SplashActivity.this.finish();
															
															break;
													}
													super.handleMessage(msg);
												}
											};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Message message = new Message();
		message.what = SplashActivity.STOP;
		
		splashHandler.sendMessage(message);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		KwikApp app = (KwikApp) getApplication();
		
		MenuItem item = menu.add("Icon");
		item.setIcon(R.drawable.search);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				onSearchRequested();
				return false;
			}
		});
		
		if (app.current_user != null) {
			MenuItem item2 = menu.add("Icon");
			item2.setIcon(R.drawable.config);
			item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			
			item2.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO: Show Settings Activity
					return false;
				}
			});
		} else {
			MenuItem item2 = menu.add("Icon");
			item2.setIcon(R.drawable.key);
			item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			item2.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO: Show Log In Activity
					return false;
				}
			});
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
}