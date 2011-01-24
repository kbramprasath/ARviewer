/*
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

import com.libresoft.apps.ARviewer.ARUtils;
import com.libresoft.apps.ARviewer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class CustomViews{
	
	private static double seekbarValue;
	
	public static double getSeekbarValue(){
		return seekbarValue;
	}
	
	public static View createButton(Context mContext, OnClickListener buttonListener){
		LayoutInflater factory = LayoutInflater.from(mContext);
		View view = factory.inflate(R.layout.ar_button, null);
		
		Button button = (Button)view.findViewById(R.id.tag_button_alone);
		
		button.setOnClickListener(buttonListener);
		return view;
	}
	
	public static View createSeekBar(final Context mContext,int max, double current_dist, final String units, OnClickListener buttonListener){
		LayoutInflater factory = LayoutInflater.from(mContext);
		View seekbar = factory.inflate(R.layout.seekbar_choice, null);
		
		SeekBar sb = (SeekBar)seekbar.findViewById(R.id.tag_bar);
		Button button = (Button)seekbar.findViewById(R.id.tag_button);
		RelativeLayout rl = (RelativeLayout)seekbar.findViewById(R.id.tag_container);
		
		button.setOnClickListener(buttonListener);
		
		seekbarValue = current_dist;
		
		double dist = current_dist;
		int max_bar = max;
		if(units.equals("Km.") || units.equals("º")){
			dist *= 10;
			max_bar *= 10;
		}
		sb.setMax(max_bar - 1);
		sb.setProgress((int)dist - 1);
		sb.setKeyProgressIncrement(1);
		
		float box_x = ((float) (dist - 1 ))/((float) max_bar - 1) * ARUtils.transformPixInDip(mContext, 400) + ARUtils.transformPixInDip(mContext, 10);
		final DrawTextBox tb = new DrawTextBox(mContext, box_x, 10);
		tb.setText(Double.toString(current_dist) + " " + units);
		
		rl.addView(tb);
		
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekbarValue = (double)seekBar.getProgress() + 1;
				if(units.equals("Km.") || units.equals("º"))
					seekbarValue = seekbarValue / 10;
				
				float box_x = ((float)seekBar.getProgress())/((float) seekBar.getMax()) * (seekBar.getWidth() - 10) + seekBar.getPaddingLeft();
				tb.setCenter(box_x, 10);
				tb.setText(Double.toString(seekbarValue) + " " + units);
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				seekbarValue = (double)seekBar.getProgress() + 1;
				if(units.equals("Km.") || units.equals("º"))
					seekbarValue = seekbarValue / 10;

				float box_x = ((float)seekBar.getProgress())/((float) seekBar.getMax()) * (seekBar.getWidth() - 10) + seekBar.getPaddingLeft();
				tb.setCenter(box_x, 10);

				tb.setText(Double.toString(seekbarValue) + " " + units);
			}
		});
		
		return seekbar;
	}
	
}
