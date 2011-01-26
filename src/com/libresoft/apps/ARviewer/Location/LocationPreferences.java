/*
 *
 *  Copyright (C) 2011 GSyC/LibreSoft, Universidad Rey Juan Carlos.
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

package com.libresoft.apps.ARviewer.Location;

import com.libresoft.apps.ARviewer.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

public class LocationPreferences extends PreferenceActivity 
implements OnSharedPreferenceChangeListener 
{
	public static String KEY_LOCATION_PROVIDERS  = "location_providers";
	public static String KEY_LOCATION_UNITS 	 = "location_unit";
	public static String KEY_LOCATION_PERIOD   = "location_period";
	public static String KEY_LOCATION_DISTANCE	 = "location_distance";
	
	private boolean restartService = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.location_preferences);
        
        // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    
	public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key) {
		restartService = true;
    }
    
    protected void onDestroy(){
    	if(restartService){
    		// Stop Location Service
        	stopService(new Intent(this,LocationService.class));
    		
    		/* Start location service */
    		startService(new Intent(this,LocationService.class));	
    	}
    	super.onDestroy();
    }
}