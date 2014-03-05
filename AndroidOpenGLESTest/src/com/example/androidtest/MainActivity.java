package com.example.androidtest;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {
	private final String LOG_TAG = "MainActivity";
	
	private GLSurfaceView glSurfaceView;
	private RenderBase mCurrentRender = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setRenderer();
		setViews();		
	}
	
	private void setRenderer() {
		mCurrentRender = new RenderTest3(this);	
	}
	
	private void setViews() {
		setGLSurfaceView();
	}
	
	private void setGLSurfaceView() {
		glSurfaceView = (GLSurfaceView)findViewById(R.id.gl_surface_view);
		glSurfaceView.setRenderer(mCurrentRender);		
	}	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.d(LOG_TAG, "onTouchEvent() : event : " + event);
		
		if(mCurrentRender != null) {
			mCurrentRender.handleTouchEvent(event);
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}
}
