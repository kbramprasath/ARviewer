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
 * 
 * Intent extras:
 * LAYER: The layer that must contain the AR nodes (GenericLayer). Mandatory.
 * LATITUDE: User's latitude coordinate (double). Optional.
 * LONGITUDE: User's longitude coordinate (double). Optional.
 * 
 */

import com.libresoft.apps.ARviewer.Overlays.CustomViews;
import com.libresoft.sdk.ARviewer.Types.GenericLayer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class ARviewer extends ARBase{ 

	private static final int MENU_DISTANCE_FILTER = 101;

	OnClickListener distFiltClickListener = new OnClickListener() {
		
		public void onClick(View v) {
			showMenu = true;
    		getLayers().removeExtraElement((View) v.getParent());
    		float dist = (float)(CustomViews.getSeekbarValue()*1E3);
    		if(distanceFilter != dist){
    			distanceFilter = dist;
    			showResources();
    		}
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!loadParameters()){
			Toast.makeText(getBaseContext(), R.string.no_layer, Toast.LENGTH_LONG).show();
		}
        loadConfig(false);
        showResources();
    }
	
	@Override
	protected boolean loadParameters(){
		super.loadParameters();
		if(!getIntent().hasExtra("LAYER"))
			return false;
		else{
			setMyLayer((GenericLayer) getIntent().getSerializableExtra("LAYER"));
		}
		
		return true;
	}
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.clear();
    	
    	if(showMenu){
    		menu.add(0, MENU_DISTANCE_FILTER, 0, "Distance filter")
    		.setIcon(R.drawable.meter);
    	}
    	
        super.onPrepareOptionsMenu(menu);        
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {

    	switch (item.getItemId()) {

    	case MENU_DISTANCE_FILTER:
    		View view = CustomViews.createSeekBars(this, distanceFilter/1E3, 50, " Km.", 10, 0, distFiltClickListener);

    		getLayers().addExtraElement(view, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

    		showMenu = false;
    		break;
    	}

    	return super.onOptionsItemSelected(item);
    }
}
