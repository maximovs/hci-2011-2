package kwik.app.activities;

import kwik.app.KwikApp;
import kwik.app.R;
import kwik.app.activities.custom.KwikFragmentActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuInflater;
import android.support.v4.view.MenuItem;
import android.support.v4.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

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
				intent.putExtra("category_id", 1);
				intent.putExtra("category_name", R.string.dvds);
				startActivityForResult(intent, 0);
			}
		});
		dvds.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(final View v) {
				Builder b = new Builder(v.getContext());
				b.setTitle(getResources().getString(R.string.dvds));
				String[] options = { getResources().getString(R.string.show_products) };
				
				b.setItems(options, new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (arg1 == 0) {
							Intent intent = new Intent(v.getContext(), ProductsActivity.class);
							intent.putExtra("category_id", 1);
							intent.putExtra("category_name", R.string.dvds);
							startActivityForResult(intent, 0);
						}
					}
				});
				
				b.create().show();
				return false;
			}
		});
		
		Button books = (Button) findViewById(R.id.home_books);
		books.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), CategoriesActivity.class);
				intent.putExtra("category_id", 2);
				intent.putExtra("category_name", R.string.books);
				startActivityForResult(intent, 0);
			}
		});
		books.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(final View v) {
				Builder b = new Builder(v.getContext());
				b.setTitle(getResources().getString(R.string.books));
				String[] options = { getResources().getString(R.string.show_products) };
				
				b.setItems(options, new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (arg1 == 0) {
							Intent intent = new Intent(v.getContext(), ProductsActivity.class);
							intent.putExtra("category_id", 2);
							intent.putExtra("category_name", R.string.books);
							startActivityForResult(intent, 0);
						}
					}
				});
				
				b.create().show();
				return false;
			}
		});
		
		getMenuInflater();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		final SplashActivity self = this;
		KwikApp app = (KwikApp) getApplication();
		MenuInflater inflater = new MenuInflater(this, getMenuInflater());
		inflater.inflate(R.menu.home_menu, menu);
		if (app.getCurrentUser() == null) {
			menu.removeItem(R.id.sign_out);
		} else {
			MenuItem so = (MenuItem) menu.findItem(R.id.sign_out);
			so.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					KwikApp app = (KwikApp) getApplication();
					app.discardSettings();
					Toast.makeText(self, getResources().getString(R.string.sign_out_toast), Toast.LENGTH_SHORT).show();
					self.reload();
					return false;
				}
			});
		}
		
		return true;
	}
	
}