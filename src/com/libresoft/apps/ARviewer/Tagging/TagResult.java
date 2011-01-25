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

package com.libresoft.apps.ARviewer.Tagging;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.libresoft.apps.ARviewer.R;
import com.libresoft.apps.ARviewer.Maps.Overlays.ResourceOverlay;

public class TagResult extends MapActivity{
	
	private static Location res_location;
	
	private static String distance;
	private static String height;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_data);
        
        TextView tvDistance = (TextView) findViewById (R.id.tagDistance);
		TextView tvHeight = (TextView) findViewById (R.id.tagHeight);
		
        
        distance = getIntent().getExtras().getString("DISTANCE");
        height = getIntent().getExtras().getString("HEIGHT");
        
        res_location = new Location("");
        res_location.setLatitude(getIntent().getExtras().getDouble("LATITUDE"));
        res_location.setLongitude(getIntent().getExtras().getDouble("LONGITUDE"));
        res_location.setAltitude(getIntent().getExtras().getDouble("ALTITUDE"));
        
        tvDistance.setText("Distance: " + distance + " m.");
        tvHeight.setText("Height: " + height + " m.");
    }
	
    private void setMap(){
    	MapView map = (MapView) findViewById(R.id.tagMapView);
    	
    	map.setSatellite(true);
        map.setClickable(true);
        map.setBuiltInZoomControls(true);
		map.getController().setZoom(18);
		map.getController().setCenter(new GeoPoint( (int)(res_location.getLatitude() * 1E6),
				(int) (res_location.getLongitude() * 1E6)));
		
		ResourceOverlay myposOverlay = new ResourceOverlay("resource");
        myposOverlay.setLocation(res_location);
        myposOverlay.setOrientation(0);
        myposOverlay.setRange(false);
        
        map.getOverlays().clear();
	    final List<Overlay> overlays = map.getOverlays();	   
	    overlays.add( myposOverlay );
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	
    	setMap();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        super.onCreateOptionsMenu(menu);        
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
    		
        return super.onOptionsItemSelected(item);
    }
	
    @Override
    protected Dialog onCreateDialog(int id) {       
    	
    	
		return null;
    
	}
    
    protected void onActivityResult (int requestCode, int resultCode, Intent data) { 
    	
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
