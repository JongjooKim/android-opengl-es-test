package com.example.androidtest;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.R.integer;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

/**
 * from "http://androidblog.reindustries.com/opengl-es-2-0-2d-shaders-series-001-basic-shaders/"
 *	
 */
public class RenderTest5 extends RenderBase {
	private final String LOG_TAG = "RenderTest5";

	private float[] viewMatrix = new float[16];

	public RenderTest5(Context context) {
		super(context);		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d(LOG_TAG, "onDrawFrame() : beginning...");
		
		
		
		Log.d(LOG_TAG, "onDrawFrame() : ending...");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		Matrix.setLookAtM(viewMatrix, 0, 0.0f, 0.0f, 4.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		
	}
}
