package com.example.androidtest;

import javax.microedition.khronos.opengles.GL;

import com.example.android.apis.graphics.spritetext.MatrixTrackingGL;

import android.app.Activity;
import android.content.Intent;
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
			case 3:
				mCurrentRender = new RenderTest4(this);
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
		glSurfaceView.setGLWrapper(new GLSurfaceView.GLWrapper() {			
			@Override
			public GL wrap(GL gl) {
				Log.d(LOG_TAG, "glSurfaceView.wrap() : gl : " + gl);
				
				return new MatrixTrackingGL(gl);
			}
		});
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
			// TODO converts (x, y) to (x,y) on viewport
			// TODO subtracts height of title bar and status bar
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
