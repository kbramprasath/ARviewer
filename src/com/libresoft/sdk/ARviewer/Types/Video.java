/*
 *
 *  Copyright (C) 2009-2011 GSyC/LibreSoft, Universidad Rey Juan Carlos
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
 *  Author : Juan Francisco Gato Luis <jfcogato@libresoft.es>
 *
 */


package com.libresoft.sdk.ARviewer.Types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.libresoft.sdk.ARviewer.Utils.BitmapUtils;

public class Video extends GeoNode implements Serializable {

	// Serializable UID
	private static final long serialVersionUID = -9121943636715457236L;
	
	private String mName;
	private String mDescription;
	private String mPath;
	private String mInfo_url, mVideo_url, mVideo_thumb_url;
	
	byte[]  mByteBitMapImageThumb;
	
	
	public Video(Integer id, String Name, String Description,
				 String info_url, String video_url,String video_thumb_url, String Path,
			     Double latitude, Double longitude,
				 Double altitude, Double radius, String since,
				 String position_since, Double distance) {
		
		super(id, latitude, longitude, altitude, radius, since, position_since);
		
		mName = Name;
		mDescription = Description;
		mPath = Path;
		mInfo_url = info_url;
		mVideo_url = video_url;
		mVideo_thumb_url = video_thumb_url;
		
	}
	
	public String getInfo_url(){
		return mInfo_url;
	}
	
	public String getVideo_url(){
		return mVideo_url;
	}
	
	public String getVideo_thumb_url(){
		return mVideo_thumb_url;
	}
	
	public String getName() {
		return mName;
	}


	public void setName(String mName) {
		this.mName = mName;
	}


	public String getDescription() {
		return mDescription;
	}


	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}


	public String getPath() {
		return mPath;
	}


	public void setPath(String mPath) {
		this.mPath = mPath;
	}
		
	public Bitmap getBitmapImageThumb() {
		
		if (mByteBitMapImageThumb == null)
		{		
			try{
				Bitmap bitmapImage = null;

				bitmapImage = BitmapUtils.loadBitmap(mVideo_thumb_url);

				if((bitmapImage.getHeight()*bitmapImage.getWidth()) > 153600){ // 480x320
					if(bitmapImage.getWidth() > bitmapImage.getHeight())
						bitmapImage = Bitmap.createScaledBitmap(bitmapImage, 480, (int)(((double)bitmapImage.getHeight()/(double)bitmapImage.getWidth())*480), true);
					else
						bitmapImage = Bitmap.createScaledBitmap(bitmapImage, (int)(((double)bitmapImage.getWidth()/(double)bitmapImage.getHeight())*480), 480, true);
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				if (!bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos))
				{
					Log.e("getBitmapImageThumb","Error: Don't compress de image");
					return null;
				}
				mByteBitMapImageThumb = baos.toByteArray();
			}catch(Exception e){
				Log.e("Video", "", e);
				mByteBitMapImageThumb = null;
				return null;
			}
			
		}
		
		return BitmapFactory.decodeStream( new ByteArrayInputStream( mByteBitMapImageThumb) );
		
	}
	
	public boolean isBitmapImageThumbLoaded(){
		return (mByteBitMapImageThumb != null);
	}
}

