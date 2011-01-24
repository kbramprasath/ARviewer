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

package com.libresoft.apps.ARviewer;


import com.libresoft.apps.ARviewer.Utils.GeoNames.AltitudeManager;
import com.libresoft.apps.ARviewer.Utils.GeoNames.AltitudePreferences;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class ARPreferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {


	public static final String KEY_HEIGHT				= "height";
	public static final String KEY_IS_DIST_FILTER		= "useDistFilter";
	public static final String KEY_DIST_FILTER			= "distFilter";
	public static final String KEY_MEASURES				= "showMeasures";
	public static final String KEY_MOVE_LABELS			= "moveLabels";
	public static final String KEY_CENTER_LABELS		= "centerLabels";
	public static final String KEY_NAMES_SHOWING		= "namesShowing";
	public static final String KEY_IMAGE_ICON			= "imageIcon";
	public static final String KEY_SEARCH_SYSTEM		= "searchSystem";
	public static final String KEY_ROTATING_COMPASS		= "rotatingCompass";
	
	
	EditTextPreference userTestPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setPreferenceScreen(createPreferenceHierarchy());
	      
	    // Set up a listener whenever a key changes            
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	    
	    blockVisibility();
	}

	private PreferenceScreen createPreferenceHierarchy() {
	    // Root
	    PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
	    
	    /* Height Properties */
	    PreferenceCategory dialogBasedPrefCat = new PreferenceCategory(this);
	    dialogBasedPrefCat.setTitle("Altitude properties");
	    root.addPreference(dialogBasedPrefCat);
	    
	    // Intent preference
        PreferenceScreen userAltitudePref = getPreferenceManager().createPreferenceScreen(this);
        userAltitudePref.setIntent(new Intent(this,AltitudePreferences.class));
        userAltitudePref.setTitle("User's altitude preferences");
        userAltitudePref.setSummary("Configure the user's altitude preferences");
        dialogBasedPrefCat.addPreference(userAltitudePref);
	    
	    // Altitude status options
	    ListPreference altitudePref = new ListPreference(this);
	    altitudePref.setEntries(R.array.height_options);
	    altitudePref.setEntryValues(R.array.values_height_options);
	    altitudePref.setDialogTitle("Altitude status");
	    altitudePref.setKey(KEY_HEIGHT);
        altitudePref.setTitle("Altitude status");
        altitudePref.setSummary("Select if you want to use the altitude info");
        dialogBasedPrefCat.addPreference(altitudePref);

	    
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    
        
        /* Height Threshold*/
	    
	    CheckBoxPreference useThresholdPref = new CheckBoxPreference(this);
	    useThresholdPref.setKey(KEY_IS_DIST_FILTER);
	    useThresholdPref.setTitle("Active altitude threshold");
	    useThresholdPref.setSummary("Use threshold to see far objects at the horizon");
	    dialogBasedPrefCat.addPreference(useThresholdPref);
	    
        // threshold
        String thrhld = sharedPreferences.getString(KEY_DIST_FILTER, null);
		if (thrhld == null)
			thrhld = "0";
		
		boolean is_enabled = sharedPreferences.getBoolean(KEY_IS_DIST_FILTER, false);
        
		EditTextPreference thresholdTestPref = new EditTextPreference(this);
		thresholdTestPref.setEnabled(is_enabled);
		thresholdTestPref.setDialogTitle("Threshold (meters)");
		thresholdTestPref.setKey(KEY_DIST_FILTER);
		thresholdTestPref.setTitle("Threshold");
		thresholdTestPref.setSummary(thrhld);
        dialogBasedPrefCat.addPreference(thresholdTestPref);
        
        /* Tools */
	    PreferenceCategory dialogBasedPrefCat2 = new PreferenceCategory(this);
	    dialogBasedPrefCat2.setTitle("Tools");
	    root.addPreference(dialogBasedPrefCat2);
	    
	    CheckBoxPreference rotatingCompassPref = new CheckBoxPreference(this);
	    rotatingCompassPref.setKey(KEY_ROTATING_COMPASS);
	    rotatingCompassPref.setTitle("Rotating compass");
	    rotatingCompassPref.setSummary("Rotate the compass instead of the visual range");
	    rotatingCompassPref.setDefaultValue(true);
	    dialogBasedPrefCat2.addPreference(rotatingCompassPref);

	    CheckBoxPreference searchSystemPref = new CheckBoxPreference(this);
	    searchSystemPref.setKey(KEY_SEARCH_SYSTEM);
	    searchSystemPref.setTitle("Clicked node search system");
	    searchSystemPref.setSummary("Active the clicked node search system");
	    searchSystemPref.setDefaultValue(true);
	    dialogBasedPrefCat2.addPreference(searchSystemPref);
	    
	    /* Show objects labels */
	    PreferenceCategory dialogBasedPrefCat5 = new PreferenceCategory(this);
	    dialogBasedPrefCat5.setTitle("Labels");
	    root.addPreference(dialogBasedPrefCat5);
	    
	    CheckBoxPreference moveLabelsPref = new CheckBoxPreference(this);
	    moveLabelsPref.setKey(KEY_MOVE_LABELS);
	    moveLabelsPref.setTitle("Dinamic summary");
	    moveLabelsPref.setSummary("Set the summary as a dinamic box");
	    dialogBasedPrefCat5.addPreference(moveLabelsPref);
	    
	    CheckBoxPreference centerLabelsPref = new CheckBoxPreference(this);
	    centerLabelsPref.setKey(KEY_CENTER_LABELS);
	    centerLabelsPref.setTitle("Central labels");
	    centerLabelsPref.setSummary("Open the summary box automatically for the central node");
	    dialogBasedPrefCat5.addPreference(centerLabelsPref);
	    
	    CheckBoxPreference imageIconPref = new CheckBoxPreference(this);
	    imageIconPref.setKey(KEY_IMAGE_ICON);
	    imageIconPref.setTitle("Image icon");
	    imageIconPref.setDefaultValue(true);
	    imageIconPref.setSummary("Set the resource own image as icon, if any");
	    dialogBasedPrefCat5.addPreference(imageIconPref);
	    
	    /* Show names options */
	    PreferenceCategory dialogBasedPrefCat6 = new PreferenceCategory(this);
	    dialogBasedPrefCat6.setTitle("Object names");
	    root.addPreference(dialogBasedPrefCat6);

        ListPreference listPref = new ListPreference(this);
        listPref.setEntries(R.array.draw_name_options);
        listPref.setEntryValues(R.array.values_draw_name_options);
        listPref.setDialogTitle("Show");
        listPref.setKey(KEY_NAMES_SHOWING);
        listPref.setTitle("Show");
        listPref.setSummary("Object names showing");
        dialogBasedPrefCat6.addPreference(listPref);
	    

	    /* Debug preferences */
	    PreferenceCategory dialogBasedPrefCat4 = new PreferenceCategory(this);
	    dialogBasedPrefCat4.setTitle("Debug");
	    root.addPreference(dialogBasedPrefCat4);
	    
	    // 
	    CheckBoxPreference measuresPref = new CheckBoxPreference(this);
	    measuresPref.setKey(KEY_MEASURES);
	    measuresPref.setTitle("Show sensors data");
	    measuresPref.setSummary("Show the sensors measures info on screen");
	    dialogBasedPrefCat4.addPreference(measuresPref);
        
	    return root;
	        
	}

	public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key) 
	{
		if (key.equals(KEY_HEIGHT)){
			blockVisibility();
			
		}else if (key.equals(KEY_IS_DIST_FILTER)) {

			Preference pref = this.findPreference(KEY_DIST_FILTER);
			if(pref == null) 
				return;
			
			pref.setEnabled(sharedPreferences.getBoolean(key, false));
		}else if(key.equals(KEY_DIST_FILTER)) {

			Preference pref = this.findPreference(key);
			if(pref == null) 
				return;
			
			pref.setSummary(sharedPreferences.getString(key, ""));
		}
	    
	}
	
	private void blockVisibility(){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		Preference pref = this.findPreference(KEY_HEIGHT);
		if(pref == null) 
			return;
		
		boolean visible = !sharedPreferences.getString(KEY_HEIGHT, 
				AltitudeManager.EXISTING_HEIGHTS).equals(AltitudeManager.NO_HEIGHTS);

		pref = this.findPreference(KEY_IS_DIST_FILTER);
		pref.setEnabled(visible);

		pref = this.findPreference(KEY_DIST_FILTER);
		if(visible && sharedPreferences.getBoolean(KEY_IS_DIST_FILTER, false))
			pref.setEnabled(true);
		else
			pref.setEnabled(false);
	}
	
}