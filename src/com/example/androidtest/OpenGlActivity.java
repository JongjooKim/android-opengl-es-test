package com.example.androidtest;

import javax.microedition.khronos.opengles.GL;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

public class OpenGlActivity extends Activity {
	private final String LOG_TAG = "OpenGLActivity";
	
	private int statusBarHeight = 0;
	private int titleBarHeight = 0;
	
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
	}
	
	private boolean hasGLES20() {
		ActivityManager am = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return info.reqGlEsVersion >= 0x20000;
	}
	
	private void findWhichRendererToStart() {
		Intent intent = this.getIntent();
		int index = intent.getIntExtra("index", -1);
		
		switch(index) {
			case 0:
				mCurrentRender = new RenderTest1(this);
				setGLES10SurfaceView();
				break;
			case 1:
				mCurrentRender = new RenderTest2(this);
				setGLES20SurfaceView();
				break;
			case 2:
				mCurrentRender = new RenderTest3(this);
				setGLES20SurfaceView();
				break;			
			default:
				mCurrentRender = null;
		}
	}
	
	private void setGLES20SurfaceView() {
		if(hasGLES20()) {
			glSurfaceView = (GLSurfaceView)findViewById(R.id.gl_surface_view);
			glSurfaceView.setEGLContextClientVersion(2);
			glSurfaceView.setPreserveEGLContextOnPause(true);
			glSurfaceView.setRenderer(mCurrentRender);			
		}
	}	
	
	private void setGLES10SurfaceView() {
		glSurfaceView = (GLSurfaceView)findViewById(R.id.gl_surface_view);
		glSurfaceView.setRenderer(mCurrentRender);		
	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		if(hasFocus) {
			statusBarHeight = getStatusBarHeight();
			titleBarHeight = getTitleBarHeight();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.d(LOG_TAG, "onTouchEvent() : event : " + event);
		
		if(mCurrentRender != null) {
			MotionEvent event2 = MotionEvent.obtain(event);
			// converts (x, y) to (x,y) on viewport
			// subtracts height of title bar and status bar
			event2.setLocation(event2.getX(), event2.getY() - statusBarHeight - titleBarHeight);	
			mCurrentRender.handleTouchEvent(event2);
		}
		
		return super.onTouchEvent(event);
	}
	
	public int getTitleBarHeight() {
	    int viewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
	    return (viewTop - getStatusBarHeight());
	}
	
	public int getStatusBarHeight() {
		int result = 0;
       int resourceId = getResources().getIdentifier("status_bar_height", 
    		   "dimen", "android");
       if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
       
       return result;
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
