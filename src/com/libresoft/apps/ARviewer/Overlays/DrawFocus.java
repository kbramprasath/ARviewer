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

package com.libresoft.apps.ARviewer.Overlays;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class DrawFocus extends View{

	public static final int CENTER = 0;
	public static final int SIDE = 1;
	
	private static final float fRADIUS = 5;
	private int position = CENTER;
	
	public DrawFocus(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setFakeBoldText(true);
		
		int w = canvas.getWidth();
        int h = canvas.getHeight();
        float cx;
        float cy;
        
        switch (position){
        
        case CENTER:
            cx = ((float)w) / 2 ;
            cy = ((float)h) / 2 ;
            break;
            
        case SIDE:
        	cx = ((float)w) / 4;
            cy = ((float)h) / 2 ;
        	break;
        	
        default:
        	cx = 0;
        	cy = 0;
        }

		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(cx, cy, fRADIUS, paint);
		
		super.onDraw(canvas);
	}
}