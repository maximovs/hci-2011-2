package kwik.services;

import java.util.List;

import kwik.app.KwikApp;
import kwik.app.KwikApp.KwikAppData.KwikOrder;
import kwik.app.R;
import kwik.app.activities.SplashActivity;
import kwik.remote.api.Order;
import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class KwikNotificationService extends IntentService {
	
	public static final String NOTIFY_ORDERS_CMD = "Start_Polling_Orders";
	
	public static final int STATUS_CONNECTION_ERROR = -1;
	public static final int STATUS_ERROR = -2;
	public static final int STATUS_ILLEGAL_ARGUMENT = -3;
	public static final int STATUS_OK = 0;
	
	private static KwikNotificationService instance;
	
	
	private static boolean checking = false;
	private KwikApp app;

	/*
	 * Se debe crear un constructor sin parametros y asignarle un nombre al
	 * servicio.
	 */
	public KwikNotificationService() {
		super("KwikNotificationService");
	}

	/*
	 * Evento que se ejecuta cuando se invocó el servicio por medio de un
	 * Intent.
	 */
	@Override
	protected void onHandleIntent(final Intent intent) {
		if (instance != null) {
			return;
		}
		checking = true;
		app = (KwikApp) getApplication();
		instance = this;
		Log.d("Notifications", "Starting service...");
		
		
		
		onCheckingNotification();
		try {
			while (true) {
				
				
				List<Order> last = app.getCurrentUser().getOrderList();
				SystemClock.sleep(1000 * 60 * app.getCurrentUpdateInterval());
				
				while (last.size() == 0) {
					last = app.getCurrentUser().getOrderList();
					SystemClock.sleep(5000);
				}
				
				List<Order> _new = app.getCurrentUser().getOrderList();
				
				for (Order order : last) {
					for (KwikOrder korder : app.getSelectedOrders()) {
						if (korder.id == order.id) {
							for (Order n : _new) {
								if (n.latitude != order.latitude || n.longitude != order.longitude) {
									onOrderLocationChangeNotification(order.id);
								}
								if (n.status != order.status) {
									onOrderStatusChangeNotification(order.id);
								}
							}
						}
					}
				}
				
				last.clear();
				for (Order order : _new) {
					last.add(order);
				}
				
				app.saveAppData();
				
				Log.d("Notifications", "Checking service");
			}
			
			
		} catch (APIBadResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HTTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private void onOrderStatusChangeNotification(int orderId) {
		String order_changed = String.format(
					getResources().getString(R.string.order_changed_notification),
					orderId);
		String app_title = getResources().getString(R.string.notification_service_start);
		sendNotification(order_changed,  app_title,order_changed,  SplashActivity.class, 0);
	}
	
	private void onOrderLocationChangeNotification(int orderId) {
		String order_changed = String.format(
					getResources().getString(R.string.order_changed_notification),
					orderId);
		String app_title = getResources().getString(R.string.notification_service_start);
		sendNotification(order_changed,  app_title,order_changed,  SplashActivity.class, 0);
	}

	private void onCheckingNotification() {
		String order_checks = getResources().getString(R.string.notification_service_start_top);
		String app_title = getResources().getString(R.string.notification_service_start);
		sendNotification(order_checks,  app_title,order_checks,  SplashActivity.class, 0);
	}

	private void sendNotification(String tickText, String contTitle, String contText,Class<?> retActivity, int notifID){
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		
//		Instantiate the Notification:
		int icon = R.drawable.logo;
		CharSequence tickerText = tickText;
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		
//		Define the notification's message and PendingIntent:
		Context context = getApplicationContext();
		CharSequence contentTitle = contTitle;
		CharSequence contentText = contText;
		Intent notificationIntent = new Intent(this, retActivity);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		notification.flags|=Notification.FLAG_AUTO_CANCEL;
		notification.flags|=Notification.FLAG_INSISTENT;
	
//		Pass the Notification to the NotificationManager:
		mNotificationManager.notify(notifID, notification);
	}

}