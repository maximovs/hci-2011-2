package kwik.services;

import kwik.app.R;
import kwik.app.activities.CategoriesActivity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;

public class KwikNotificationService extends IntentService {
	
	private String TAG = getClass().getSimpleName();

	public static final String NOTIFY_ORDERS_CMD = "Start_Polling_Orders";
	public static boolean singleton = false;
	
	public static final int STATUS_CONNECTION_ERROR = -1;
	public static final int STATUS_ERROR = -2;
	public static final int STATUS_ILLEGAL_ARGUMENT = -3;
	public static final int STATUS_OK = 0;

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
		if(singleton){
			this.stopSelf();
			return;
		}
		singleton=true;
		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		final String command = intent.getStringExtra("command");
		final String token    = intent.getStringExtra("token");

		final Bundle b = new Bundle();
		if (command.equals(NOTIFY_ORDERS_CMD)) {
			int notif_id=1;
			for(notif_id=1;notif_id<3;notif_id++){
			sendNotification(getResources().getString(R.string.new_order_update) + notif_id, "My notification", "Hello World!1", CategoriesActivity.class,notif_id);
			
			SystemClock.sleep(5000); //TODO: look for new orders :)
			}
			
		}


		// Es importante terminar el servicio lo antes posible.
		this.stopSelf();
	}

	private void sendNotification(String tickText, String contTitle, String contText,Class<?> retActivity, int notifID){
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
//		Instantiate the Notification:
		int icon = R.drawable.icon;
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