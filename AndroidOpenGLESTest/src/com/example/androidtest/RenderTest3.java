package com.example.androidtest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;

public class RenderTest3 extends RenderBase {
	private final String LOG_TAG = "RenderTest3";	

	public RenderTest3(Context context) {
		super(context);
	}

	@Override
	public void onDrawFrame(GL10 gl) {

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
	}
}
