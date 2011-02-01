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
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.view.View;


public class DrawTextBox extends View{

	private float[] center = {0, 0};
	private String text;
	
	public DrawTextBox(Context context, double x, double y) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.center[0] = (float) x;
		this.center[1] = (float) y;
	}
	
	public void setCenter(double x, double y){
		this.center[0] = (float) x;
		this.center[1] = (float) y;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas){
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setARGB(150,10,10,10);
		paint.setAntiAlias(true);
		
		Paint paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(8);
		paintText.setAntiAlias(true);
		paintText.setFakeBoldText(true);
		paintText.setTextAlign(Align.CENTER);
		
		RectF back = new RectF (center[0] - 25, center[1] - 10, center[0] + 25, center[1] + 10);
		
        canvas.drawRoundRect(back, 15, 15, paint);
        
        if(text != null){
        	Path path = new Path();
    		path.moveTo(center[0] - 20, center[1] + 2.5f);
    		path.lineTo(center[0] + 20, center[1] + 2.5f);
    		canvas.drawTextOnPath(text, path, 0, 0, paintText);
        }
		
		super.onDraw(canvas);
	}
}