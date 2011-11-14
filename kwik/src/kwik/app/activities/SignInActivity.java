package kwik.app.activities;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import kwik.remote.api.User;
import kwik.services.KwikAPIService;
import kwik.util.KwikResultReceiver;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends KwikFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		
		final Activity self = this;
		
		final Intent localIntent = self.getIntent();
		
		
		
		Button sign_in = (Button) findViewById(R.id.sign_in_button);
		
		sign_in.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_SYNC, null, v.getContext(), KwikAPIService.class);
				
				intent.putExtra("command", KwikAPIService.SIGN_IN_CMD);
				
				EditText username_input = (EditText) findViewById(R.id.editText1);
				EditText password_input = (EditText) findViewById(R.id.editText2);
				
				intent.putExtra("username", username_input.getText().toString());
				intent.putExtra("password", password_input.getText().toString());
				
				
				intent.putExtra("receiver", new KwikResultReceiver(new Handler(), self) {
					@Override
					protected void onReceiveResult(int resultCode, Bundle resultData) {
						super.onReceiveResult(resultCode, resultData);
						if (resultCode == KwikAPIService.STATUS_OK) {
							ResultReceiver res = localIntent.getParcelableExtra("receiver");
							localIntent.putExtra("return", (User)resultData.getSerializable("return"));
							res.send(resultCode, resultData);
							self.onBackPressed();
							
						}
					}
				});
				
				startService(intent);
			}
		});
		
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		boolean val = super.onMenuOpened(featureId, menu);
		
		
		return val;
	}
}
