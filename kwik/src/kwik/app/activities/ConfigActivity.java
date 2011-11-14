package kwik.app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ConfigActivity extends KwikFragmentActivity implements OnItemClickListener {
	
	private static final int	CONFIG_TIMEOUT_MODE			= 1;
	private static final int	CONFIG_TIMEOUT_SET_VALUE	= 2;
	private static final int	CONFIG_COLOR_SET_VALUE		= 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		ListView vi = (ListView) findViewById(R.id.listview);
		vi.setOnItemClickListener(this);
		
		String[] desired_fields = { "title" };
		
		List<Map<String, ?>> items = new ArrayList<Map<String, ?>>();
		
		HashMap<String, Object> timeoutMap = new HashMap<String, Object>();
		
		timeoutMap.put("title", getResources().getString(R.string.config_timeout_mode));
		timeoutMap.put("action", CONFIG_TIMEOUT_MODE);
		
		items.add(timeoutMap);
		
		HashMap<String, Object> timeoutTimerMap = new HashMap<String, Object>();
		
		timeoutTimerMap.put("title", getResources().getString(R.string.config_timeout_value_set));
		timeoutTimerMap.put("action", CONFIG_TIMEOUT_SET_VALUE);
		
		items.add(timeoutTimerMap);
		
		HashMap<String, Object> colorMap = new HashMap<String, Object>();
		
		colorMap.put("title", getResources().getString(R.string.config_color_set));
		colorMap.put("action", CONFIG_COLOR_SET_VALUE);
		
		items.add(colorMap);
		
		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item_list_item, desired_fields,
				new int[] { R.id.title }) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = super.getView(position, convertView, parent);
				if (convertView != null) {
					if (position > 0) {
						ImageView image = (ImageView) convertView.findViewById(R.id.image);
						image.setImageDrawable(getResources().getDrawable(R.drawable.setting_gray));
					}
				}
				return convertView;
			}
		};
		
		vi.setAdapter(adapter);
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> view, View v, int position, long arg3) {
		ListView vi = (ListView) view;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) vi.getItemAtPosition(position);
		
		final ConfigActivity self = this;
		String title_label = (String) map.get("title");
		Integer action = (Integer) map.get("action");
		
		Builder b = new Builder(v.getContext());
		b.setTitle(title_label);
		switch (action) {
			case CONFIG_TIMEOUT_MODE:
				Intent intent = new Intent(v.getContext(), OrdersActivity.class);
				startActivityForResult(intent, 0);
				break;
			case CONFIG_COLOR_SET_VALUE:
				
				String[] options = { getResources().getString(R.string.config_color_blue),
						getResources().getString(R.string.config_color_red),
						getResources().getString(R.string.config_color_orange),
						getResources().getString(R.string.config_color_green) };
				
				int currentColor = self.app.getCurrentColor();
				
				int index = 0;
				switch (currentColor) {
					case Color.RED:
						index = 1;
						break;
					case Color.YELLOW:
						index = 2;
						break;
					case Color.GREEN:
						index = 3;
						break;
				}
				
				b.setSingleChoiceItems(options, index, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								self.app.setCurrentColor(Color.BLUE);
								break;
							case 1:
								self.app.setCurrentColor(Color.RED);
								break;
							case 2:
								self.app.setCurrentColor(Color.YELLOW);
								break;
							case 3:
								self.app.setCurrentColor(Color.GREEN);
								break;
						}
						Toast.makeText(self, getResources().getString(R.string.config_color_change_toast),
								Toast.LENGTH_SHORT).show();
					}
					
				});
				b.create().show();
				break;
			case CONFIG_TIMEOUT_SET_VALUE:
				String[] opts = { getResources().getString(R.string.a_minute),
						String.format(getResources().getString(R.string.n_minutes), 2),
						String.format(getResources().getString(R.string.n_minutes), 5),
						String.format(getResources().getString(R.string.n_minutes), 10),
						String.format(getResources().getString(R.string.n_minutes), 15),
						String.format(getResources().getString(R.string.n_minutes), 30),
						String.format(getResources().getString(R.string.n_minutes), 60)};
				
				int ind = 0;
				
				switch(app.getCurrentUpdateInterval()) {
					case 1:
						ind = 0;
						break;
					case 2:
						ind = 1;
						break;
					case 5:
						ind = 2;
						break;
					case 10:
						ind = 3;
						break;
					case 15:
						ind = 4;
						break;
					case 30:
						ind = 5;
						break;
					case 60:
						ind = 6;
						break;
				}
				
				b.setSingleChoiceItems(opts, ind, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which) {
							case 0:
								app.setCurrentUpdateInterval(1);
								break;
							case 1:
								app.setCurrentUpdateInterval(2);
								break;
							case 2:
								app.setCurrentUpdateInterval(5);
								break;
							case 3:
								app.setCurrentUpdateInterval(10);
								break;
							case 4:
								app.setCurrentUpdateInterval(15);
								break;
							case 5:
								app.setCurrentUpdateInterval(30);
								break;
							case 6:
								app.setCurrentUpdateInterval(60);
								break;
						}
						
						Toast.makeText(self, getResources().getString(R.string.config_timeout_value_set_toast),
								Toast.LENGTH_SHORT).show();
					}
				});
				b.create().show();
				break;
		}
		
		
	}
	
}
