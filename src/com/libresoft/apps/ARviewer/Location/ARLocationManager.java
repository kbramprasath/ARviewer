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

import android.location.Location;

public class ARLocationManager{
	
	public static final int MODE_MANUAL = 2;
	public static final int MODE_NETWORK = 1;
	public static final int MODE_GPS = 0;
	
	private static ARLocationManager arLocationManager = null;
	
	private Location mLocation = null;
	
	private ARLocationManager(){}
	
	public static ARLocationManager getInstance(){
		if(arLocationManager == null)
			arLocationManager = new ARLocationManager();
		return arLocationManager;
	}
	
}