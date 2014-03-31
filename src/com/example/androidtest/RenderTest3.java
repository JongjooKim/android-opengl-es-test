package com.example.androidtest;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

/**
 * This class also displays a grid in perpective mode.
 *  
 * @author Jongjoo Kim
 *
 */
public class RenderTest3 extends RenderBase {
	private final String LOG_TAG = "RenderTest3";
	
	private float[] lines = {
			0.0f, 0.0f, 0.0f,
			0.1f, 0.0f, 0.0f,
			0.1f, 0.1f, 0.0f,
			0.0f, 0.1f, 0.0f,
	};
	private float[] colors = {
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
	};
	
	private float[] origin = {
			-0.01f, -0.01f, 0.0f,
			-0.01f, +0.01f, 0.0f,
			+0.01f, +0.01f, 0.0f,
			+0.01f, -0.01f, 0.0f
	};
	
	private float[] redColor = { // RGBA
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f		
	};
	private float[] blueColor = {
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,					
	};
	
	private GL10 gl;
	private float[] eventPoint;
	private float[] MVPMatrix = new float[16];
	private float[] modelViewMatrix = new float[16];
	private float[] projectionMatrix = new float[16];
	private float[] perspectiveMatrix = new float[16];
	private int width, height;
	
	private FloatBuffer lineBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer originBuffer;
	private FloatBuffer originColorBuffer;
	private FloatBuffer blueColorBuffer;	

	public RenderTest3(Context context) {
		super(context);		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d(LOG_TAG, "onDrawFrame() : beginning...");
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// gl.glLoadIdentity();
		Matrix.setIdentityM(modelViewMatrix, 0);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				
		gl.glClearDepthf(1.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		
		Matrix.setLookAtM(modelViewMatrix, 0, 0.0f, 0.0f, 4.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		// GLU.gluLookAt(gl, 0.0f, 0.0f, 4.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
			
		// fills upper-left field
		gl.glPushMatrix();
		for(int i = 1; i <= 10; i++) {			
			for(int j = 0; j < 10; j++) {
				gl.glPushMatrix();
				gl.glTranslatef(-0.1f * i, +0.1f * j, 0.0f);
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
				gl.glPopMatrix();
			}
		}
		gl.glPopMatrix();
		// fills upper-right field
		gl.glPushMatrix();
		for(int i = 0; i < 10; i++) {			
			for(int j = 0; j < 10; j++) {
				gl.glPushMatrix();
				gl.glTranslatef(+0.1f * i, +0.1f * j, 0.0f);
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
				gl.glPopMatrix();
			}
		}
		gl.glPopMatrix();
		// fills lower-left field
		gl.glPushMatrix();
		for(int i = 1; i <= 10; i++) {			
			for(int j = 1; j <= 10; j++) {
				gl.glPushMatrix();
				gl.glTranslatef(-0.1f * i, -0.1f * j, 0.0f);
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
				gl.glPopMatrix();
			}
		}
		gl.glPopMatrix();
		// fills lower-right field
		gl.glPushMatrix();
		for(int i = 0; i < 10; i++) {			
			for(int j = 1; j <= 10; j++) {
				gl.glPushMatrix();
				gl.glTranslatef(+0.1f * i, -0.1f * j, 0.0f);
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
				gl.glPopMatrix();
			}
		}
		gl.glPopMatrix();
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, originBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, originColorBuffer);
		
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);		
		
		// draws a touch point		
		if(eventPoint != null) {
			float[] point = {
					eventPoint[0] - 0.01f, eventPoint[1] - 0.01f, eventPoint[2],
					eventPoint[0] + 0.01f, eventPoint[1] - 0.01f, eventPoint[2],
					eventPoint[0] - 0.01f, eventPoint[1] + 0.01f, eventPoint[2],
					eventPoint[0] + 0.01f, eventPoint[1] + 0.01f, eventPoint[2],
			};
			FloatBuffer pointBuffer = this.createFloatBuffer(point);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, blueColorBuffer);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pointBuffer);
			
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
		
		Log.d(LOG_TAG, "onDrawFrame() : ending...");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		
		this.width = width;
		this.height = height;
		
		// gl.glMatrixMode(GL10.GL_PROJECTION);
		// gl.glLoadIdentity();
		Matrix.setIdentityM(modelViewMatrix, 0);
		
		gl.glViewport(0, 0, width, height);		
		// GLU.gluPerspective(gl, 45.0f, 1.0f * width / height, 1.0f, 100.0f);
		Matrix.perspectiveM(perspectiveMatrix, 0, 45.0f, 1.0f * width / height, 1.0f, 100.0f);
		
		// gl.glMatrixMode(GL10.GL_MODELVIEW);
		// gl.glLoadIdentity();
		Matrix.setIdentityM(projectionMatrix, 0);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
		
		this.gl = gl;
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
				
		lineBuffer = this.createFloatBuffer(lines);
		colorBuffer = this.createFloatBuffer(colors);
		originBuffer = this.createFloatBuffer(origin);
		originColorBuffer = this.createFloatBuffer(redColor);
		blueColorBuffer = this.createFloatBuffer(blueColor);
	}
	
	@Override
	public boolean handleTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		
		if(action == MotionEvent.ACTION_DOWN ||
				action == MotionEvent.ACTION_MOVE ||
				action == MotionEvent.ACTION_UP ||
				action == MotionEvent.ACTION_CANCEL) {		
			// int width = 2.0 * Math.tan(0.5 * 45.0);
			// int height = 0;
			/*
			float[] point = convertSSC2WSCInPerspective2(gl, 
					event.getX(), event.getY(), width, height);
			eventPoint = point;
			*/
			return false;			
		}
		
		return super.handleTouchEvent(event);
	}
}
