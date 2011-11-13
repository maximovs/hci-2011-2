package kwik.app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ConfigActivity extends KwikFragmentActivity implements OnItemClickListener {
	
	private static final int CONFIG_TIMEOUT_ENABLE = 1;
	private static final int CONFIG_TIMEOUT_SET_VALUE = 2;
	private static final int CONFIG_COLOR_SET_VALUE = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		ListView vi = (ListView) findViewById(R.id.listview);
		vi.setOnItemClickListener(this);
		
		String[] desired_fields = { "title" };
		
		List<Map<String, ?>> items = new ArrayList<Map<String,?>>();
		
		HashMap<String, Object> timeoutMap = new HashMap<String, Object>();
		
		timeoutMap.put("title", getResources().getString(R.string.config_timeout_enabled));
		timeoutMap.put("action", CONFIG_TIMEOUT_ENABLE);
		
		items.add(timeoutMap);

		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item_list_item, desired_fields, new int[] { R.id.title }) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = super.getView(position, convertView, parent);
				if (convertView != null) {
					if (position == 0) {
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
}
