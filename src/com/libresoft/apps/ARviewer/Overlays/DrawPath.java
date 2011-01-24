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
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class DrawPath extends View{
	
	private float inclination = 90;
	
	public DrawPath(Context context) {
		super(context);
	}
	
	public void setInclination(float inclination){
		this.inclination = inclination;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		
//		radius = MAX_RAD * (float) Math.sin(Math.toRadians(inclination));
//		float lateral_rad = radius * (float) Math.sin(Math.toRadians(inclination));
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setARGB(200, 240, 255, 240);
		
		int w = canvas.getWidth();
        int h = canvas.getHeight();
        float cx = ((float)w) / 2;
        float cy = ((float)h) / 2 -12.5f;
		
//        The movement of the objects can be modeled by an ellipse (approach) that changes its shape with the inclination variation:
//         - Y semiaxis (Minor): Máx = Screen y semiaxis / sin(alpha); where alpha is the Y screen sight semirange.
//        		The instantaneal Y semiaxis is : Máx * sin(inclination)
//         - X semiaxis (Mayor): Mín = Y semiaxis.
//        		The instantaneal X semiaxis is : Y semiaxis * sec(inclination) * signum(sec(inclination))
        
        float radius_y = 0;
        float radius_x = 0;
        if(inclination != 90){
        	radius_y = (cy/(float)Math.sin(Math.toRadians(20)))*(float) Math.sin(Math.toRadians(inclination));
        	radius_x = radius_y * 1/((float)Math.cos(Math.toRadians(inclination))) * 
        		(float)Math.signum(Math.cos(Math.toRadians(inclination)));
        }else{
        	radius_y = 0.001f;
        	radius_x = 10000;
        }
        
        RectF oval;
        if(inclination > 90){
        	oval = new RectF(cx - radius_x, cy - 2*radius_y, cx + radius_x , cy);
        	canvas.drawArc(oval, 0, 360, true, paint);
        }else{
        	oval = new RectF(cx - radius_x, cy, cx + radius_x, cy + 2*radius_y);

            canvas.drawArc(oval, 0, 360, true, paint);
        }
      
//		canvas.drawCircle(cx, cy + radius, radius, paint);
		super.onDraw(canvas);
	}
	
}