package kwik.app;

import kwik.remote.api.User;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.util.HTTPUtils;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;

public class KwikApp extends Application {
	
	public static KwikApp instance;

	private User current_user;
	
	public User getCurrentUser() {
		return current_user;
	}

	public void setCurrentUser(User current_user) {
		try {

			SharedPreferences prefs = this.getSharedPreferences("app_prefs", MODE_APPEND);
			Editor ed = prefs.edit();
			if (current_user != null) {
				ed.putString("user", HTTPUtils.serializeObjectToXML(current_user));
			} else {
				ed.remove("user");
			}			
			this.current_user = current_user;
			ed.commit();
		} catch (XMLParseException e) {
			// Highly unlikely...
			e.printStackTrace();
		}
	}

	public KwikApp () {
		if (instance != null) {
			SharedPreferences prefs = this.getSharedPreferences("app_prefs", MODE_WORLD_READABLE);
			String xml = prefs.getString("user", null);
			if (xml != null) {
				instance.current_user = User.fromXML(xml);
			}
			
			this.current_user = instance.current_user;
		}
	}
		
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {	
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onTerminate() {
		
		super.onTerminate();
	}
}
