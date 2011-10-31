package twitter.search.activities;

import java.util.List;

import twitter.search.R;
import twitter.search.model.api.Tweet;
import twitter.search.model.api.TweetProvider;
import twitter.search.model.impl.SimpleTweetProvider;
import twitter.search.services.TwitterSearchService;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class SearchActivity extends ListActivity {

	private String TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Asociamos la vista del search list con la activity */
		setContentView(R.layout.search_list);

		Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				TwitterSearchService.class);

		intent.putExtra("command", TwitterSearchService.GET_TWEETS_CMD);
		/* Se pasa un callback (ResultReceiver), con el cual se procesará la
		 * respuesta del servicio. Si se le pasa null como parámetro del constructor
		 * se usa uno de los threads disponibles del proceso. Dado que en el procesamiento
		 * del mismo se debe modificar la UI, es necesario que ejecute con el thread de UI.
		 * Es por eso que se lo instancia con un objeto Handler (usando el el thread de UI
		 * para ejecutarlo).
		 */
		intent.putExtra("receiver", new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				super.onReceiveResult(resultCode, resultData);
				if (resultCode == TwitterSearchService.STATUS_OK) {

					Log.d(TAG, "OK");

					@SuppressWarnings("unchecked")
					List<Tweet> list = (List<Tweet>) resultData
							.getSerializable("return");

					populateList(new SimpleTweetProvider(list));

				} else if (resultCode == TwitterSearchService.STATUS_CONNECTION_ERROR) {
					Log.d(TAG, "Connection error.");
				} else {
					Log.d(TAG, "Unknown error.");
				}
			}

		});
		startService(intent);
	}
	
	private void populateList(TweetProvider tweetProvider) {
		ListAdapter adapter = new SimpleAdapter(this,
				tweetProvider.getTweetsAsMap(), R.layout.search_item,
				tweetProvider.getMapKeys(), new int[] { R.id.date,
			R.id.title, R.id.description });
		
		setListAdapter(adapter);	
	}
	
}
