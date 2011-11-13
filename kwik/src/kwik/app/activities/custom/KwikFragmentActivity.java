package kwik.app.activities.custom;

import kwik.app.KwikApp;
import kwik.app.R;
import kwik.app.activities.SignInActivity;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.MenuItem.OnMenuItemClickListener;

public class KwikFragmentActivity extends FragmentActivity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		final Activity self = this;
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
					Intent intent = new Intent(self, SignInActivity.class);
					startActivity(intent);
					return false;
				}
			});
		}
		
		return super.onCreateOptionsMenu(menu);
	}	
}
