package kwik.app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
					ImageView image = (ImageView) convertView.findViewById(R.id.image);
					image.setImageDrawable(getResources().getDrawable(R.drawable.setting_gray));
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
		
		final String title_label = (String) map.get("title");
		final Integer action = (Integer) map.get("action");
		
		Builder b = new Builder(v.getContext());
		
		switch (action) {
			case CONFIG_COLOR_SET_VALUE:
				b.setTitle(title_label);
				String[] options = { 
						getResources().getString(R.string.config_color_blue),
						getResources().getString(R.string.config_color_red),
						getResources().getString(R.string.config_color_orange),
						getResources().getString(R.string.config_color_green)
				};
				
				b.setSingleChoiceItems(options, 0, null);
				
				b.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> view, View v, int position, long arg3) {
						
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> view) {
						
					}
				});
				break;
		}
		
		b.create().show();
	}
}
