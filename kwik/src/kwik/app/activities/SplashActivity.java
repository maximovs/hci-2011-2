package kwik.app.activities;

import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashActivity extends KwikFragmentActivity {
	
	public static final int	STOP	= 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button dvds = (Button) findViewById(R.id.home_dvds);
		dvds.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), CategoriesActivity.class);
				intent.putExtra("category_id",	1);
				intent.putExtra("category_name",R.string.dvds);
				startActivity(intent);
			}
		});
		
		Button books = (Button) findViewById(R.id.home_books);
		books.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), CategoriesActivity.class);
				intent.putExtra("category_id",	2);
				intent.putExtra("category_name",R.string.books);
				startActivity(intent);
			}
		});
	}
	
	
	
}