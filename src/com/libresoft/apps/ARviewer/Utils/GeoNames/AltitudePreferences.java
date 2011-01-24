/*
 *
 *  Copyright (C) 2010 GSyC/LibreSoft, Universidad Rey Juan Carlos.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/. 
 *
 *  Author : Raúl Román López <rroman@gsyc.es>
 *
 */

package com.libresoft.apps.ARviewer.Utils.GeoNames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class AltitudePreferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {


	public static final String KEY_USER_HEIGHT			= "userHeight";
	public static final String KEY_FLOOR				= "floor";
	public static final String KEY_USE_FLOOR			= "useFloor";
	public static final String KEY_GPS					= "useGps";
	
	
	EditTextPreference userTestPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setPreferenceScreen(createPreferenceHierarchy());
	      
	    // Set up a listener whenever a key changes            
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	private PreferenceScreen createPreferenceHierarchy() {
	    // Root
	    PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
	    
	    /* Height Properties */
	    PreferenceCategory dialogBasedPrefCat = new PreferenceCategory(this);
	    dialogBasedPrefCat.setTitle("User's altitude properties");
	    root.addPreference(dialogBasedPrefCat);
	    

	    // GPS height active
	    CheckBoxPreference gpsHeightPref = new CheckBoxPreference(this);
	    gpsHeightPref.setKey(KEY_GPS);
	    gpsHeightPref.setTitle("Active location service altitude");
	    gpsHeightPref.setSummary("Take altitude data from the current location service");
	    dialogBasedPrefCat.addPreference(gpsHeightPref);
	    
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    
		// User height 
		String height = sharedPreferences.getString(KEY_USER_HEIGHT, null); 
		 
		if (height == null)
			height = "1.75";
			     
		EditTextPreference userHeightTestPref2 = new EditTextPreference(this);
        userHeightTestPref2.setDialogTitle("User tall (meters)");
        userHeightTestPref2.setKey(KEY_USER_HEIGHT);
        userHeightTestPref2.setTitle("User tall");
        userHeightTestPref2.setSummary(height);
        dialogBasedPrefCat.addPreference(userHeightTestPref2);
        
        
        CheckBoxPreference useFloorPref = new CheckBoxPreference(this);
        useFloorPref.setKey(KEY_USE_FLOOR);
        useFloorPref.setDefaultValue(false);
        useFloorPref.setTitle("Active floor info");
        useFloorPref.setSummary("Use floor number");
	    dialogBasedPrefCat.addPreference(useFloorPref);
	    
        // actual floor
        String floor = sharedPreferences.getString(KEY_FLOOR, null);
		if (floor == null)
			floor = "0";
		
		boolean is_enabled = sharedPreferences.getBoolean(KEY_USE_FLOOR, false);
        
		EditTextPreference floorTestPref = new EditTextPreference(this);
		floorTestPref.setEnabled(is_enabled);
        floorTestPref.setDialogTitle("Number of Floor");
        floorTestPref.setKey(KEY_FLOOR);
        floorTestPref.setTitle("Floor");
        floorTestPref.setSummary(floor);
        dialogBasedPrefCat.addPreference(floorTestPref);
        
	    return root;
	        
	}

	public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key) 
	{
		if (key.equals(KEY_GPS)){
			AltitudeManager.setLocationServiceAltitude(sharedPreferences.getBoolean(key, false));
			if(AltitudeManager.isLocationServiceAltitude()){
				stopService(new Intent(this,LocationService.class));
				startService(new Intent(this,LocationService.class));
			}
		}else if (key.equals(KEY_USER_HEIGHT)) {

			Preference pref = this.findPreference(key);
			if(pref == null) 
				return;
			String height = sharedPreferences.getString(key, "");
			if(height.contains(",")){
				height = height.replace(",", ".");

				Editor editor = sharedPreferences.edit();
				editor.putString(KEY_USER_HEIGHT, height);
				editor.commit();
			}
			
			pref.setSummary(height);
		}else if (key.equals(KEY_USE_FLOOR)) {

			Preference pref = this.findPreference(KEY_FLOOR);
			if(pref == null) 
				return;
			
			pref.setEnabled(sharedPreferences.getBoolean(key, false));
			
		}else if (key.equals(KEY_FLOOR)) {

			Preference pref = this.findPreference(key);
			if(pref == null) 
				return;
			
			pref.setSummary(sharedPreferences.getString(key, ""));
		}
	    
	}
	
}