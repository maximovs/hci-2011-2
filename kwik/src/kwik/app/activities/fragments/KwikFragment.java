package kwik.app.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KwikFragment extends Fragment {
	
	public View mainView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainView = super.onCreateView(inflater, container, savedInstanceState); 
		return mainView;
	}
	
}
