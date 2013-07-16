package com.brianrojas.view3dflipper;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

public class View3DFlipper extends FrameLayout {
	
	public enum Direction {
		LEFT, RIGHT
	}
	
	public interface View3DFlipperListener {
		void onFlipAnimationStart(View3DFlipper flipper);
		void onFlipAnimationEnd(View3DFlipper flipper);
	}
	
	private View mFrontView;
	private int mFrontCenterX;
	private View mRearView;
	private int mRearCenterX;
	private int mDuration = 1000;
	private View3DFlipperListener mListener;

	public View3DFlipper(Context context) {
		super(context);
	}

	public View3DFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public View3DFlipper(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		if (getChildCount() != 2) {
			throw new IllegalStateException("View3DFlipper must have exactly 2 child views to flip");
		}
		
		mFrontView = getChildAt(0);
		mFrontCenterX = mFrontView.getWidth() / 2;
		
		mRearView = getChildAt(1);
		mRearView.setVisibility(View.INVISIBLE);
		mRearCenterX = mRearView.getWidth() / 2;
	}
	
	public void flip() {
		flip(Direction.LEFT);
	}
	
	public void flip(final Direction direction) {
		float frontFromAngle = 0.0f, frontToAngle;
		if (direction == Direction.LEFT) {
			frontToAngle = -90.0f;
		} else {
			frontToAngle = 90.0f;
		}
		
		FlipAnimation flipAnimPart1 = new FlipAnimation(frontFromAngle, frontToAngle, mFrontCenterX);
		flipAnimPart1.setDuration(mDuration / 2);
		flipAnimPart1.setInterpolator(new AccelerateInterpolator());
		flipAnimPart1.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				float rearFromAngle, rearToAngle = 0.0f;
				if (direction == Direction.LEFT) {
					rearFromAngle = 90.0f;
				} else {
					rearFromAngle = -90.0f;
				}
				
				mFrontView.setVisibility(View.INVISIBLE);
				mRearView.setVisibility(View.VISIBLE);
				
				FlipAnimation flipAnimPart2 = new FlipAnimation(rearFromAngle, rearToAngle, mRearCenterX);
				flipAnimPart2.setDuration(mDuration / 2);
				flipAnimPart2.setInterpolator(new DecelerateInterpolator());
				flipAnimPart2.setFillAfter(true);
				flipAnimPart2.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
						View tempView = mFrontView;
						mFrontView = mRearView;
						mRearView = tempView;
						
						int tempInt = mFrontCenterX;
						mFrontCenterX = mRearCenterX;
						mRearCenterX = tempInt;
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						if (mListener != null) {
							mListener.onFlipAnimationEnd(View3DFlipper.this);
						}
					}
				});
				mRearView.startAnimation(flipAnimPart2);
			}
		});
		mFrontView.startAnimation(flipAnimPart1);
		
		if (mListener != null) {
			mListener.onFlipAnimationStart(this);
		}
	}
	
	public void setDuration(int duration) {
		mDuration = duration;
	}
	
	public void setAnimationListener(View3DFlipperListener listener) {
		mListener = listener;
	}
	
	private class FlipAnimation extends Animation {

		private Camera mCamera = new Camera();
		private float mFromDegrees;
		private float mToDegrees;
		private int mCenterX;
		
		public FlipAnimation(float fromDegrees, float toDegrees, int centerX) {
			mFromDegrees = fromDegrees;
			mToDegrees = toDegrees;
			mCenterX = centerX;
		}
		
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			t.clear();
			t.setTransformationType(Transformation.TYPE_MATRIX);
			final Matrix matrix = t.getMatrix();
			
			mCamera.save();
			mCamera.rotateY(mFromDegrees + (mToDegrees - mFromDegrees) * interpolatedTime);
			mCamera.getMatrix(matrix);
			mCamera.restore();
			
			matrix.preTranslate(-mCenterX, 0.0f);
			matrix.postTranslate(mCenterX, 0.0f);
		}
		
	}
	
}
