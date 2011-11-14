package kwik.app;

import java.io.Reader;
import java.io.StringReader;

import kwik.remote.api.User;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.util.HTTPUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;

public class KwikApp extends Application {
	
	public static KwikApp	instance;
	
	@Root
	public static class KwikAppData {
		@Element
		User	current_user;
		
		@Element
		int		color;
		
		public KwikAppData() {
			color = Color.BLUE;
			current_user = null;
		}
		
		public static KwikAppData fromXML(String xml) {
			KwikAppData data = new KwikAppData();
			Serializer serializer = new Persister();
			Reader reader = new StringReader(xml);
			try {
				data = serializer.read(KwikAppData.class, reader, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}
		
		private void save() {
			try {
				SharedPreferences prefs = instance.getSharedPreferences("app_prefs", MODE_APPEND);
				Editor ed = prefs.edit();
				if (this != null) {
					ed.putString("data", HTTPUtils.serializeObjectToXML(this));
				} else {
					ed.remove("data");
				}
				ed.commit();
				
			} catch (XMLParseException e) {
				// Highly unlikely...
				e.printStackTrace();
			}
		}
	}
	
	private void saveAppData() {
		data.save();
	}

	private KwikAppData	data;
	
	public User getCurrentUser() {
		return data.current_user;
	}
	
	public void setCurrentUser(User current_user) {
		data.current_user = current_user;
		saveAppData();
	}
	
	public int getCurrentColor() {
		return data.color;
	}
	
	public void setCurrentColor(int c) {
		data.color = c;
		saveAppData();
	}

	
	

	
	@Override
	public void onCreate() {
		super.onCreate();
		if (instance != null) {
			SharedPreferences prefs = this.getSharedPreferences("app_prefs", MODE_WORLD_READABLE);
			String xml = prefs.getString("data", null);
			if (xml != null) {
				this.data = KwikAppData.fromXML(xml);
			} else {
				this.data = new KwikAppData();
			}
			this.data = instance.data;			
		}
		else {
			try {
				SharedPreferences prefs = this.getSharedPreferences("app_prefs", MODE_WORLD_READABLE);
				String xml = prefs.getString("data", null);
				if (xml != null) {
					this.data = KwikAppData.fromXML(xml);
				} else {
					this.data = new KwikAppData();
				}
			} catch (Exception e) {
				this.data = new KwikAppData();
			}
			
			instance = this;
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onTerminate() {
		saveAppData();
		super.onTerminate();
	}

	public void discardSettings() {
		SharedPreferences prefs = instance.getSharedPreferences("app_prefs", MODE_APPEND);
		Editor ed = prefs.edit();
		ed.remove("data");
		ed.commit();
		data = new KwikAppData();
	}
}
