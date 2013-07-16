View3DFlipper
=============

Android 3D View Flipper

Contains a class, View3DFlipper, which implements a view flip transition animation like seen in iOS.  This class uses only tween animations and has been tested down to API level 8.

View3DFlipper is a subclass of FrameLayout so to use simply add 2 views as child views and then call the flip method.  In repo is sample project demonstrating functionality.

To use first add 2 subviews in XML or in Java code, XML sample below:

    <com.brianrojas.view3dflipper.View3DFlipper
        android:id="@+id/flipper"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerInParent="true" >

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:background="@android:color/darker_gray"
            android:gravity="center"
            android:padding="40dp"
            android:text="View 1" />

         <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:background="@android:color/darker_gray"
            android:gravity="center"
            android:padding="40dp"
            android:text="View 2" />

    </com.brianrojas.view3dflipper.View3DFlipper>

Then from Java start animation like this:

    private View3DFlipper mFlipper;
	
    private boolean mFlipRight = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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

You can optionally pass the direction to the flip method, otherwise it defaults to left