package com.brianrojas.view3dflipper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String[] titles = new String[] { getResources().getString(R.string.title_section_1),
				getResources().getString(R.string.title_section_2) };
		
		getListView().setAdapter(new ItemsAdapter(this, titles));
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent;
				if (position == 0) {
					intent = new Intent(MainActivity.this, ClockFlipActivity.class);
				} else {
					intent = new Intent(MainActivity.this, GridFlipActivity.class);
				}
				startActivity(intent);
			}
		});
	}
	
	private static final class ItemsAdapter extends ArrayAdapter<String> {

		public ItemsAdapter(Context context, String[] titles) {
			super(context, android.R.layout.simple_list_item_1, 
					android.R.id.text1, titles);
		}
		
	};
	
}
