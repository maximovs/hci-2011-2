package kwik.app.activities.custom;

import kwik.app.KwikApp;
import kwik.app.R;
import kwik.app.activities.ConfigActivity;
import kwik.app.activities.SignInActivity;
import kwik.remote.api.User;
import kwik.services.KwikAPIService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

public class KwikFragmentActivity extends FragmentActivity {
	
	protected String	TAG	= getClass().getSimpleName();
	
	protected KwikApp	app	= (KwikApp) getApplication();
	
	protected int		color;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		app = (KwikApp) getApplication();
		
		switch (app.getCurrentColor()) {
			case Color.RED:
				this.setTheme(R.style.Theme_Custom_Red);
				break;
			case Color.GREEN:
				this.setTheme(R.style.Theme_Custom_Green);
				break;
			case Color.YELLOW:
				this.setTheme(R.style.Theme_Custom_Orange);
				break;
			case Color.BLUE:
			default:
				this.setTheme(R.style.Theme_Custom_Blue);
				break;
		}
		
		color = app.getCurrentColor();
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (color != app.getCurrentColor()) {
			color = app.getCurrentColor();
			switch (app.getCurrentColor()) {
				case Color.RED:
					this.setTheme(R.style.Theme_Custom_Red);
					break;
				case Color.GREEN:
					this.setTheme(R.style.Theme_Custom_Green);
					break;
				case Color.YELLOW:
					this.setTheme(R.style.Theme_Custom_Orange);
					break;
				case Color.BLUE:
				default:
					this.setTheme(R.style.Theme_Custom_Blue);
					break;
			}
			this.reload();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		final KwikFragmentActivity self = this;
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
		
		if (app.getCurrentUser() != null) {
			MenuItem item2 = menu.add("Icon");
			item2.setIcon(R.drawable.config);
			item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			
			item2.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					if (!(self instanceof ConfigActivity)) {
						Intent intent = new Intent(self, ConfigActivity.class);
						startActivityForResult(intent, 0);
					}
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
					if (!(self instanceof SignInActivity)) {
						Intent intent = new Intent(self, SignInActivity.class);
						intent.putExtra("receiver", new ResultReceiver(new Handler()) {
							protected void onReceiveResult(int resultCode, Bundle resultData) {
								if (resultCode == KwikAPIService.STATUS_OK) {
									User u = (User) resultData.getSerializable("return");
									KwikApp app = (KwikApp) getApplication();
									app.setCurrentUser(u);
									Toast.makeText(self, getResources().getString(R.string.sign_in_toast),
											Toast.LENGTH_SHORT).show();
									self.reload();
								}
							};
						});
						startActivity(intent);
					}
					return false;
				}
			});
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public void reload() {
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		
		overridePendingTransition(0, 0);
		startActivity(intent);
	}
	
	@Override
	public void finish() {
		Intent resultIntent = new Intent();
		setResult(Activity.RESULT_OK, resultIntent);
		super.finish();
	}
}
