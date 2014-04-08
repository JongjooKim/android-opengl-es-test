package com.example.androidtest;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

/**
 * This class displays a grid onto OpenGL world coordinate system in orthorgonal mode. 
 * 
 * On Galaxy S3, fps is 60.
 * @author Jongjoo Kim
 *
 */
public class RenderTest2 extends RenderBase {
	private final String LOG_TAG = "RenderTest2";
	
	private float[] modelMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private float[] projectionMatrix = new float[16];
	private float[] MVPMatrix = new float[16];
	
	private PointF eventPointF;
	private int width, height;
		
	private float[] squareVerticesData = {
			0.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.1f, 0.0f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.1f, 0.1f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 0.1f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
	};
	
	private float[] originVerticesData = {
		-0.01f, -0.01f, 0.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
		+0.01f, -0.01f, 0.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
		-0.01f, +0.01f, 0.0f,
		1.0f, 0,0f, 0.0f, 1.0f,
		+0.01f, +0.01f, 0.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
	};
	
	private float[] touchPointVerticesData;
	/*
	private float[] blueCoor = {
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
	};
	*/
	
	FloatBuffer squareVerticesBuffer;
	FloatBuffer originVerticesBuffer;
	FloatBuffer touchPointVerticesBuffer;
	
	// vertex shader -- start
	private String vertexShader = 
			"uniform mat4 u_MVPMatrix;" +
	
			"attribute vec4 a_Position;" + 
			"attribute vec4 a_Color;" +
			
			"varying vec4 v_Color;"+
			
			"void main()" + 
			"{" +
			"	v_Color = a_Color;" +
			
			"	gl_Position = u_MVPMatrix * a_Position;" +
			"}";
	// vertex shader -- end
	
	// fragment shader -- start
	private String fragmentShader =
			"precision mediump float;" +
			
			"varying vec4 v_Color;" +
			
			"void main()" +
			"{" +
			"	gl_FragColor = v_Color;" +
			"}";
	// fragment shader -- end

	public RenderTest2(Context context) {
		super(context);	
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// Log.d(LOG_TAG, "onDrawFrame() : gl : " + gl);
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);		
		
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
		for(int i = 0; i <= 10; i++) {			
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

		gl.glColorPointer(4, GL10.GL_FLOAT, 0, redColorBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, originBuffer);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		
		if(eventPointF != null) {
			float[] eventPoint = {
					eventPointF.x - 0.01f, eventPointF.y - 0.01f, 0.0f,
					eventPointF.x + 0.01f, eventPointF.y - 0.01f, 0.0f,
					eventPointF.x - 0.01f, eventPointF.y + 0.01f, 0.0f,
					eventPointF.x + 0.01f, eventPointF.y + 0.01f, 0.0f,
			};
			FloatBuffer eventPointBuffer = createFloatBuffer(eventPoint);
			
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, eventPointBuffer);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, blueColorBuffer);
			
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		
		this.width = width;
		this.height = height;
		
		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
		
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Matrix.orthoM(viewMatrix, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
		
		squareVerticesBuffer = this.createFloatBuffer(squareVerticesData);
		originVerticesBuffer = this.createFloatBuffer(originVerticesData);
		
		// blueColorBuffer = createFloatBuffer(blueCoor);		
	}
	
	@Override
	public boolean handleTouchEvent(MotionEvent event) {
		Log.d(LOG_TAG, "handleTouchEvent(): event : " + event);		
		final int action = event.getAction();
		
		if(action == MotionEvent.ACTION_DOWN || 
				action == MotionEvent.ACTION_MOVE ||
				action == MotionEvent.ACTION_UP ||
				action == MotionEvent.ACTION_CANCEL) {
			float[] point = convertSSC2WSCInOrthogonal(event.getX(), event.getY(), 
					width, height);		
			eventPointF = new PointF();
			eventPointF.x = point[0];	// x
			eventPointF.y = point[1];	// y	
			return false;
		} 		
		
		return super.handleTouchEvent(event);
	}
}
