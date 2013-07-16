package com.brianrojas.view3dflipper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class GridFlipActivity extends Activity {

	private LinearLayout mRow1Layout;
	private LinearLayout mRow2Layout;
	private LinearLayout mRow3Layout;
	
	private List<View3DFlipper> mFlipperViews = new ArrayList<View3DFlipper>();
	
	private Handler mHandler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		
		mRow1Layout = (LinearLayout) findViewById(R.id.row1_layout);
		mRow2Layout = (LinearLayout) findViewById(R.id.row2_layout);
		mRow3Layout = (LinearLayout) findViewById(R.id.row3_layout);
		
		addViews(mRow1Layout);
		addViews(mRow2Layout);
		addViews(mRow3Layout);
		
		Button startButton = (Button) findViewById(R.id.start_anim_button);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (final View3DFlipper flipper : mFlipperViews) {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							flipper.flip();
						}
					}, 100 * mFlipperViews.indexOf(flipper));
				}
			}
		});
	}
	
	private void addViews(LinearLayout layout) {
		Random rnd = new Random();
		for (int i=0; i<3; i++) {
			View3DFlipper flipper = new View3DFlipper(this);
			flipper.setDuration(800);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 
					LayoutParams.MATCH_PARENT);
			layoutParams.weight = 1.0f;
			flipper.setLayoutParams(layoutParams);
			
			layout.addView(flipper);
			
			TextView side1TextView = new TextView(this);
			side1TextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
					LayoutParams.MATCH_PARENT));
			side1TextView.setText("Side 1");
			side1TextView.setGravity(Gravity.CENTER);
			side1TextView.setBackgroundColor(Color.argb(255, rnd.nextInt(256), 
					rnd.nextInt(256), rnd.nextInt(256)));
			flipper.addView(side1TextView);
			
			TextView side2TextView = new TextView(this);
			side2TextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
					LayoutParams.MATCH_PARENT));
			side2TextView.setText("Side 2");
			side2TextView.setGravity(Gravity.CENTER);
			side2TextView.setBackgroundColor(Color.argb(255, rnd.nextInt(256), 
					rnd.nextInt(256), rnd.nextInt(256)));
			flipper.addView(side2TextView);
			
			mFlipperViews.add(flipper);
		}
	}

}
