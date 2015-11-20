package com.pairme;

import android.app.ListActivity;
import android.os.Bundle;

public class ScoreList extends ListActivity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.score);
	        setListAdapter(
	             new ScoreListAdapter(this, MainActivity.storage.listaPuntuaciones(10)));

	    }
}
