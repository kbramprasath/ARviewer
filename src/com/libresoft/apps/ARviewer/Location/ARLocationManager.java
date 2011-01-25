/*
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

import com.libresoft.apps.ARviewer.Utils.GeoNames.AltitudeManager;

import android.location.Location;

public class ARLocationManager{
	public static final int NO_LATLONG = -360;
	
	public static final int MODE_MANUAL = 2;
	public static final int MODE_NETWORK = 1;
	public static final int MODE_GPS = 0;
	
	private static ARLocationManager arLocationManager = null;
	
	private boolean ls_altitude = false;
	private Location mLocation = null;
	
	private ARLocationManager(){
		mLocation = new Location("Manual");
		mLocation.setLatitude(NO_LATLONG);
		mLocation.setLongitude(NO_LATLONG);
		mLocation.setAltitude(AltitudeManager.NO_ALTITUDE_VALUE);
	}
	
	public static ARLocationManager getInstance(){
		if(arLocationManager == null)
			arLocationManager = new ARLocationManager();
		return arLocationManager;
	}
	
	public Location getLocation(){
		return mLocation;
	}
	
	public void setLocation(Location location){
		mLocation = location;
	}
	
	public void setLocation(float latitude, float longitude, float altitude){
		mLocation.setLatitude(latitude);
		mLocation.setLongitude(longitude);
		mLocation.setAltitude(altitude);
	}
	
	public void setLocationServiceAltitude(boolean ls_altitude){
		this.ls_altitude = ls_altitude;
	}
	
}