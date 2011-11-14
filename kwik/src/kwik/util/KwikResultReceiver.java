package kwik.util;

import kwik.app.R;
import kwik.services.KwikAPIService;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

public class KwikResultReceiver extends ResultReceiver {

	private Activity owner;
	
	public KwikResultReceiver(Handler handler, Activity act) {
		super(handler);
		owner = act;
	}
	
	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		super.onReceiveResult(resultCode, resultData);
		if (resultCode == KwikAPIService.API_BAD_RESPONSE_ERROR) {
			kwik.remote.api.Error e = (kwik.remote.api.Error) resultData.getSerializable("return");
			Log.d("ErrorReceiver", "ERROR CODE: " + Integer.toString(e.code));
			Log.d("ErrorReceiver", "ERROR: " + e.message);
			Toast.makeText(owner, owner.getResources().getString(R.string.API_bad_response), Toast.LENGTH_SHORT).show();
		}else if (resultCode == KwikAPIService.STATUS_ERROR) {
			String s = (String) resultData.getSerializable("return");
			Log.d("ErrorReceiver", s);			
			Toast.makeText(owner, owner.getResources().getString(R.string.HTML_error), Toast.LENGTH_SHORT).show();
		}else if (resultCode == KwikAPIService.STATUS_ILLEGAL_ARGUMENT) {
			String s = (String) resultData.getSerializable("return");
			Log.d("ErrorReceiver", s);
			Toast.makeText(owner, owner.getResources().getString(R.string.XML_parser_error), Toast.LENGTH_SHORT).show();					
		}
	}
	
}
