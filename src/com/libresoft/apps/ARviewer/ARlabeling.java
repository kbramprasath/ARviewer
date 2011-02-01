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

package com.libresoft.apps.ARviewer;

/*
 * Intent extras:
 * LATITUDE: User's latitude coordinate (double). Optional.
 * LONGITUDE: User's longitude coordinate (double). Optional.
 * 
 * Return extra:
 * LABELED_NODES: ArrayList containing the GeoNode objects labeled.
 */


import com.libresoft.apps.ARviewer.ARTagManager.OnLocationChangeListener;
import com.libresoft.apps.ARviewer.ARTagManager.OnTaggingFinishedListener;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ARlabeling extends ARBase{ 
	
    private OnLocationChangeListener onTaggingLocationListener = new OnLocationChangeListener() {
		
		@Override
		public void onChange(float[] values) {
			setLocation(values);
		}
	};
	
    private OnTaggingFinishedListener onTaggingFinishedListener = new OnTaggingFinishedListener() {
		
		@Override
		public void onFinish(boolean success) {
			showMenu = true;
			if(success){
				Toast.makeText(getBaseContext(), "Ok", Toast.LENGTH_SHORT).show();
			}else
				Toast.makeText(getBaseContext(), "Fail", Toast.LENGTH_SHORT).show();
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		loadParameters();
        super.onCreate(savedInstanceState);
		
		tagManager = new ARTagManager(this, getLayers(), getMyLayer(), getLocation(), cam_altitude);
		tagManager.setOnLocationChangeListener(onTaggingLocationListener);
		tagManager.setOnTaggingFinishedListener(onTaggingFinishedListener);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.clear();
    	
    	if(showMenu){
    		tagManager.onCreateOptionsMenu(menu);
    	}
    	
        super.onCreateOptionsMenu(menu);        
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {

    	switch (item.getItemId()) {
    	}

    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {       
    	
    	Dialog diag = tagManager.onCreateDialog(id);
    	if(diag != null)
    		return diag;
    	
		return super.onCreateDialog(id);
    
	}
    
	protected void onActivityResult (int requestCode, int resultCode, Intent data) { 
		
    	if (tagManager.onActivityResult(requestCode, resultCode, data))
    		return;
    	super.onActivityResult(requestCode, resultCode, data);
	}
}
