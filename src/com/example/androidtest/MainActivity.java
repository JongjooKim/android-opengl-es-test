package com.example.androidtest;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private final String LOG_TAG = "MainActivity";
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}
	
	private void init() {
		setViews();
	}
	
	private void setViews() {
		setListView();
	}
	
	private void setListView() {
		listView = (ListView)findViewById(R.id.listview);
		String[] stringArrayList = getResources().getStringArray(R.array.opengl_test_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, stringArrayList);
		listView.setAdapter(adapter);		
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d(LOG_TAG, "listView.onItemClick() : parent : " + parent + ", view : " + view + ", position : " + 
					position + ", id : " + id);
				Intent intent = new Intent(MainActivity.this, OpenGlActivity.class);
				intent.putExtra("index", position);
				startActivity(intent);
			}
			
		});
		listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {	
				Log.d(LOG_TAG, "listView.onItemSelected() : parent : " + parent + ", view : " + view + ", position : " + 
					position + ", id : " + id);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.d(LOG_TAG, "listView.onItemSelected() : parent : " + parent);
			}
		});
	}
}
