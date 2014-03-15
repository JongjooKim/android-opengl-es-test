package com.example.androidtest;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.util.Log;

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
	
	private FloatBuffer lineBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer originBuffer;
	private FloatBuffer originColorBuffer;

	public RenderTest3(Context context) {
		super(context);
	}

	@Override
	public void onDrawFrame(GL10 gl) {		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				
		gl.glClearDepthf(1.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		
		GLU.gluLookAt(gl, 0.0f, 0.0f, 4.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
			
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
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glViewport(0, 0, width, height);
		GLU.gluPerspective(gl, 45.0f, 1.0f * width / height, 1.0f, 100.0f);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		lineBuffer = this.createFloatBuffer(lines);
		colorBuffer = this.createFloatBuffer(colors);
		originBuffer = this.createFloatBuffer(origin);
		originColorBuffer = this.createFloatBuffer(redColor);		
	}
}
