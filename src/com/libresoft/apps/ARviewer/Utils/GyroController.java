
package com.libresoft.apps.ARviewer.Utils;

import java.util.ArrayList;

import android.util.Log;

public class GyroController{
	private static final int VAR_THRESHOLD = 10;
	private static final float GYRO_THRESHOLD = 0.04f;
	private static final int ERROR_THRESHOLD = 45;
	private static final int MAX_VALUES = 5;
	
	private boolean stable_phase = false;
	
	private float X = 0;
	private ArrayList<Float> last_values = null;
	
	public GyroController(){
		last_values = new ArrayList<Float>();
	}
	
	public float getValue(float new_value, float new_gyro){
		
//		if(!initial_phase){
//			initial_phase = doInitialPhase(new_value);
//		}else if(stable_phase){
//			stable_phase = doStablePhase(new_value, new_gyro);
//		}else{
//			stable_phase = doUnstablePhase(new_value);
//		}

		insertMeasure(new_value);
		if(stable_phase){
			stable_phase = doStablePhase(new_value, new_gyro);
		}else{
			stable_phase = doUnstablePhase(new_value);
		}
			
		return X;
	}
	
	private boolean doUnstablePhase(float new_value){
		float var = calculateVar(new_value);
		int num = last_values.size();
		if((num == MAX_VALUES) && (var < VAR_THRESHOLD)){
			Log.e("GyroController", "EXIT InitialPhase: num_values=" + Integer.toString(num) + "; VAR=" + Float.toString(var));
			X = calculateMean(new_value);
			return true;
		}
		X = X + .5f*(new_value - X);
		Log.e("GyroController", "InitialPhase: num_values=" + Integer.toString(num) + "; VAR=" + Float.toString(var));
		return false;
	}
	
	private boolean doStablePhase(float new_value, float new_gyro){
		
		if(Math.abs(new_gyro) < GYRO_THRESHOLD)
			return true;
		
		// Predicted angle
		X = (float) (X - Math.toDegrees(new_gyro)*0.125);
		if(X > 360)
			X += -360;
		else if(X < 0)
			X += 360;
		
		// Error
		float error = Math.abs(new_value - X);
		/* Transform signal */
		if(error >= 180)
			error = 360 - error;
		
		if(error > ERROR_THRESHOLD){
			Log.e("GyroController", "EXIT StablePhase: error=" + Float.toString(error));
			return false;
		}
		return true;
	}
	
//	private boolean doUnstablePhase(float new_value){
//		
//		// Calculate the variance of the last stored values
//		float var = calculateVar(new_value);
//		float mean = calculateMean(new_value);
//		
//		/* Transform signal */
//		if((mean - X) <= -180)
//			X += - 360;
//		else if((mean - X) >= 180)
//			X += 360;
//
//		/* Prediction phase: */
//		float X_pred = X;
//
//		/* Update phase: */
//		float error = mean - X_pred;
//		float gain = (1f/2f) *((float) -Math.exp(-Math.pow(error, 2)/(2*var)) + 1);
//		X = X_pred + gain * (error);
//		float new_error = mean - X;
//		
//		if(X > 360)
//			X += -360;
//		else if(X < 0)
//			X += 360;
//		
//		if(Math.abs(new_error) < VAR_THRESHOLD){
//			Log.e("GyroController", "EXIT UnstablePhase: error=" + Float.toString(error));
//			return true;
//		}
//		Log.e("GyroController", "UnstablePhase: error=" + Float.toString(new_error));
//		return false;
//	}
	
	private void insertMeasure(float new_value){
		if(last_values.size() < MAX_VALUES){
			last_values.add(new_value);
		}else{
			last_values.remove(0);
			last_values.add(new_value);
		}
	}
	
	private float calculateVar(float new_value){
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
		return var/num_values;
	}
	
	private float calculateMean(float new_value){
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
		
		if(mean > 360)
			mean += -360;
		else if(mean < 0)
			mean += 360;
		
		return mean;
	}
	
}