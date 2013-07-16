package com.brianrojas.view3dflipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public final class ClockFlipActivity extends Activity {

private View3DFlipper mFlipper;
	
    private boolean mFlipRight = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock);
		
		mFlipper = (View3DFlipper) findViewById(R.id.flipper);
		mFlipper.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mFlipRight) {
					mFlipRight = true;
					mFlipper.flip(View3DFlipper.Direction.LEFT);
				} else {
					mFlipRight = false;
					mFlipper.flip(View3DFlipper.Direction.RIGHT);
				}
			}
		});
	}
	
}
