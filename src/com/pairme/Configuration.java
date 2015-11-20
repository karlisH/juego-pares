package com.pairme; 
 
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Configuration extends PreferenceActivity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.configuration);
	}
}
