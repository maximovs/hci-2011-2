package kwik.app.activities;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import kwik.remote.api.User;
import kwik.services.KwikAPIService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
				
				
				intent.putExtra("receiver", new ResultReceiver(new Handler()) {
					@Override
					protected void onReceiveResult(int resultCode, Bundle resultData) {
						super.onReceiveResult(resultCode, resultData);
						if (resultCode == KwikAPIService.STATUS_OK) {
							ResultReceiver res = localIntent.getParcelableExtra("receiver");
							localIntent.putExtra("return", (User)resultData.getSerializable("return"));
							res.send(resultCode, resultData);
							self.onBackPressed();
							
						}else if (resultCode == KwikAPIService.STATUS_CONNECTION_ERROR) {
							Toast.makeText(self, getResources().getString(R.string.API_bad_response), Toast.LENGTH_SHORT).show();
							Log.d(TAG, "Connection error.");
						}else if (resultCode == KwikAPIService.STATUS_ERROR) {
							Log.d(TAG, "Unavailable to connect, please try again.");
							Toast.makeText(self, getResources().getString(R.string.HTML_error), Toast.LENGTH_SHORT).show();
						}else if (resultCode == KwikAPIService.STATUS_ILLEGAL_ARGUMENT) {
							Log.d(TAG, "An error occurs while processing your request.");
							Toast.makeText(self, getResources().getString(R.string.XML_parser_error), Toast.LENGTH_SHORT).show();					
						} else {
							Log.d(TAG, "Unknown error.");
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
