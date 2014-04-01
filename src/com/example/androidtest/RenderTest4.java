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
public class RenderTest4 extends RenderBase {
	private final String LOG_TAG = "RenderTest5";
	
	private final float[] mtrxProjection = new float[16];
	private final float[] mtrxView = new float[16];
	private final float[] mtrxProjectionAndView = new float[16];
	
	private float vertices[];
	private short indices[];
	public FloatBuffer vertextBuffer;
	public ShortBuffer drawListBuffer;	
	
	private int mScreenWidth;
	private int mScreenHeight;
	
	private int mProgram;

	public RenderTest4(Context context) {
		super(context);		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d(LOG_TAG, "onDrawFrame() : beginning...");
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);		
		
		Log.d(LOG_TAG, "onDrawFrame() : ending...");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		mScreenWidth = width;
		mScreenHeight = height;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
		
		vertices = new float[] {
				10.0f, 200f,  0.0f,
				10.0f, 100f, 0.0f,
				100f, 100f, 0.0f,
		};
		indices = new short[] {0, 1, 2};
		
		vertextBuffer = createFloatBuffer(vertices);
		drawListBuffer = createShortBuffer(indices);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
	}	
}
