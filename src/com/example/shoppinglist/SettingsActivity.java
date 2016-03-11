package com.example.shoppinglist;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PreferenceManager manager = getPreferenceManager();

		manager.setSharedPreferencesName("my_prefs");

		addPreferencesFromResource(R.xml.prefs);
	}
}
