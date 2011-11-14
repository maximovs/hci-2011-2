package kwik.app.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.app.KwikApp.KwikAppData.KwikOrder;
import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import kwik.remote.api.Order;
import kwik.services.KwikAPIService;
import kwik.util.KwikResultReceiver;
import kwik.util.Util;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OrdersActivity extends KwikFragmentActivity implements OnItemClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Asociamos la vista del search list con la activity */
		this.setContentView(R.layout.item_list);
	
		
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this, KwikAPIService.class);
		
		intent.putExtra("command", KwikAPIService.GET_ORDERS_CMD);
		
		/*
		 * Se pasa un callback (ResultReceiver), con el cual se procesará la
		 * respuesta del servicio. Si se le pasa null como parámetro del
		 * constructor se usa uno de los threads disponibles del proceso. Dado
		 * que en el procesamiento del mismo se debe modificar la UI, es
		 * necesario que ejecute con el thread de UI. Es por eso que se lo
		 * instancia con un objeto Handler (usando el el thread de UI para
		 * ejecutarlo).
		 */
		intent.putExtra("receiver", new KwikResultReceiver(new Handler(), this) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				super.onReceiveResult(resultCode, resultData);
				if (resultCode == KwikAPIService.STATUS_OK) {
					boolean no_data_fetched = false;
					
					@SuppressWarnings("unchecked")
					List<Order> orderList = (List<Order>) resultData.getSerializable("return");
					populateOrders(orderList);
					no_data_fetched = orderList.size() == 0;
					
					if (!no_data_fetched) {
						ListView vi = (ListView) findViewById(R.id.listview);
						vi.setVisibility(View.VISIBLE);
					} else {
						View vi = (View) findViewById(R.id.textview);
						vi.setVisibility(View.VISIBLE);
					}
					View pg = (View) findViewById(R.id.progressbar);
					pg.setVisibility(View.GONE);
					Log.d("ORDERS", "Got Orders");
				}
			}
			
		});
	
		startService(intent);
	}

	protected void populateOrders(List<Order> orderList) {
		
		String[] map_fields = { "name", "id" };
		String[] desired_fields = { "name" };
		
		@SuppressWarnings("unchecked")
		final List<Map<String,Object>> mapped = (List<Map<String, Object>>) Util.getMapped(orderList, map_fields);
		
		
		int i = 0;
		for (Map<String, Object> map : mapped) {
			map.put("instance", orderList.get(i));
			map.put("name", getResources().getString(R.string.order) + "#" + orderList.get(i++).id);
		}
		
		ListAdapter adapter = new SimpleAdapter(this, mapped, R.layout.item_list_order,
				desired_fields, new int[] { R.id.title }) {
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				convertView = super.getView(position, convertView, parent);
				convertView.findViewById(R.id.toggleButton1).setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						Order o = (Order) mapped.get(position).get("instance");
						app.addSelectedOrder(o);
					}
				});
				
				CheckBox cb = (CheckBox) convertView.findViewById(R.id.toggleButton1);
				Order o = (Order) mapped.get(position).get("instance");
				
				for (KwikOrder order : app.getSelectedOrders()) {
					if(order.id == o.id) {
						cb.setChecked(true);
						break;
					}
				}
				
				
				
				return convertView;
			}
		};
		ListView vi = (ListView) findViewById(R.id.listview);
		vi.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position, long arg3) {
		ListView vi = (ListView) view;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) vi.getItemAtPosition(position);
		
		Order o = (Order)map.get("instance");
		app.addSelectedOrder(o);
	}
	
}
