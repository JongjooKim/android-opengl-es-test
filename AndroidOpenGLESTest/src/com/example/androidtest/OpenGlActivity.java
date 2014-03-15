package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class OpenGlActivity extends Activity {
	private final String LOG_TAG = "OpenGLActivity";
	
	private GLSurfaceView glSurfaceView;
	private RenderBase mCurrentRender = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opengl_activity);
		
		init();		
	}
	
	private void init() {
		findWhichRendererToStart();
		// setRenderer();
		setViews();
	}
	
	private void findWhichRendererToStart() {
		Intent intent = this.getIntent();
		int index = intent.getIntExtra("index", -1);
		
		switch(index) {
			case 0:
				mCurrentRender = new RenderTest1(this);
				break;
			case 1:
				mCurrentRender = new RenderTest2(this);
				break;
			case 2:
				mCurrentRender = new RenderTest3(this);
				break;
			default:
				mCurrentRender = null;
		}
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
