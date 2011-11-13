package kwik.app.activities;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import android.os.Bundle;
import android.view.Menu;

public class SignInActivity extends KwikFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		boolean val = super.onMenuOpened(featureId, menu);
		menu.getItem(1).setEnabled(false);
		
		return val;
	}
}
