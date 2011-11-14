package kwik.app;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kwik.app.KwikApp.KwikAppData.KwikOrder;
import kwik.remote.api.Order;
import kwik.remote.api.User;
import kwik.remote.api.exceptions.XMLParseException;
import kwik.remote.util.HTTPUtils;
import kwik.services.KwikNotificationService;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;

public class KwikApp extends Application {
	
	public static KwikApp	instance;
	
	@Root
	public static class KwikAppData {
		
		@Element
		public static class KwikOrder {
			@Attribute
			public int id;
		}
		
		@Element
		int		update_interval;
		
		@Element
		User	current_user;
		
		@ElementList
		List<KwikOrder> orders;
		
		int		language_id;
		
		@Element
		int		color;
		
		public KwikAppData() {
			color = Color.BLUE;
			language_id = 1;
			current_user = null;
			orders = new ArrayList<KwikOrder>();
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
					String serialized = HTTPUtils.serializeObjectToXML(this);
					Log.d("XML Save", serialized);
					ed.putString("data", serialized);
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
	
	public void saveAppData() {
		data.save();
	}
	
	private KwikAppData	data;
	
	public List<KwikOrder> getSelectedOrders() {
		return data.orders;
	}
	
	public void removeSelectedOrder(Order o) {
		int index = 0;
		int i = 0;
		for (KwikOrder ord : data.orders) {
			if (ord.id == o.id) {
				index = i;
				break;
			}
			i++;
		}
		data.orders.remove(index);
		saveAppData();
	}
	
	public void addSelectedOrder(Order o) {
		KwikOrder order = new KwikOrder();
		order.id = o.id;
		data.orders.add(order);
		saveAppData();
		startNotificationService();
	}
	
	public User getCurrentUser() {
		return data.current_user;
	}
	
	public int getCurrentLanguage() {
		return data.language_id;
	}
	
	public void setCurrentUser(User current_user) {
		data.current_user = current_user;
		saveAppData();
		if (instance.data.current_user != null) {
			startNotificationService();
		}
	}
	
	public int getCurrentUpdateInterval() {
		return data.update_interval;
	}
	
	public void setCurrentUpdateInterval(int interval) {
		data.update_interval = interval;
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
		} else {
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
		instance.data.language_id = (Locale.getDefault().getISO3Language().equals("spa")) ? 2 : 1;
		
		Log.d("App orders count", Integer.toString(instance.data.orders.size()));
		Log.d("LANGUAGE",Locale.getDefault().getISO3Language());
		
		if (instance.data.current_user != null) {
			startNotificationService();
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
	
	public void startNotificationService() {
		if (instance.data.orders.size() > 0) {
			Intent notifications = new Intent(Intent.ACTION_MAIN, null, this, KwikNotificationService.class);
			startService(notifications);
		}
	}
}
