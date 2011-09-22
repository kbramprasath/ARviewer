
package com.libresoft.apps.ARviewer.Utils;

import java.util.ArrayList;

import android.util.Log;


public class GaussianFilter{
	
	private static final float MEASURED_STDEV = 7f;
	private static final float MEASURED_DEV = MEASURED_STDEV*MEASURED_STDEV;
	
	private ArrayList<Float> last_values = null;
	
	private static final float INIT_VALUE = -1000;
	
	private float X = INIT_VALUE;
	
	public float getValue(float new_value){
		if(X == INIT_VALUE){
			X = new_value;
			last_values = new ArrayList<Float>();
			last_values.add(new_value);
			return new_value;
		}
		
		// Calculate the variance of the last stored values
		float mean = 0;
		int num_values = last_values.size();
		for(Float num : last_values){
			mean += num;
			if((new_value - num) <= -180)
				mean += - 360;
			else if((new_value - num) >= 180)
				mean += 360;
		}
		mean = mean/num_values;
		
		float var = 0;
		for(Float num : last_values){
			float diff = mean - num;
			if(diff <= -180)
				diff += 360;
			else if(diff >= 180)
				diff += -360;
			var += Math.pow(diff, 2);
		}
		var = var/num_values;
		
		/* Transform signal */
		if((new_value - X) <= -180)
			X += - 360;
		else if((new_value - X) >= 180)
			X += 360;
		
		if((new_value - X) < 20){
			/* Prediction phase: */
			float X_pred = X;
			
			/* Update phase: */
			float gain = (1f/2f) *((float) -Math.exp(-Math.pow(new_value - X_pred, 2)/(2*var)) + 1);
			X = X_pred + gain * (new_value - X_pred);
		}else
			X = new_value;
		
		if(X > 360)
			X += -360;
		else if(X < 0)
			X += 360;
		
		// Storing the new value
		if(num_values < 10){
			last_values.add(new_value);
		}else{
			last_values.remove(0);
			last_values.add(new_value);
		}
		
		return X;
	}
	
	
}