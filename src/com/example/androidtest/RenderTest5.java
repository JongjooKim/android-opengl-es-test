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
 * 	
 */
public class RenderTest5 extends RenderBase {
	private final String LOG_TAG = "RenderTest5";

	private float[] viewMatrix = new float[16];
	private float[] projectionMatrix = new float[16];
	
	private final int strideBytes = 7 * 4;
	
	private float[] squareVerticesData = {
			0.0f, 0.0f, 0.0f,			// vertex
			1.0f, 1.0f, 1.0f, 1.0f,	// vertext color
			0.1f, 0.0f, 0.0f,	
			1.0f, 1.0f, 1.0f, 1.0f,
			0.1f, 0.1f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 0.1f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
	};
	
	private float[] originVerticesData = {
			-0.01f, -0.01f, 0.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			-0.01f, +0.01f, 0.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			+0.01f, +0.01f, 0.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			+0.01f, -0.01f, 0.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
	};
	
	private FloatBuffer squareVerticesBuffer;
	private FloatBuffer originVerticesBuffer;
	
	private int MVPMatrixHandle;
	private int positionHandle;
	private int colorHandle;
	
	// vertext Shader -- start
	final String vertexShader = 
			"uniform mat4 u_MVPMatrix;" +
	
			"attribute vec4 a_Position;" +
			"attribute vec4 a_Color;" +
			
			"varying vec4 v_Color;" +
			
			"void main()" +
			"{" +
			"	v_Color = a_Color;" +
			
			"	gl_Position = u_MVPMatrix * a_Position;" +
			"}";
	// vertex shader -- end
	
	// fragment shader -- start
	final String fragmentShader = 
			"precision mediump float;" +
			
			"varying vec4 v_Color;" +
			
			"void main()" +
			"{" +
			"	gl_FragColor = v_Color;" +
			"}";
	// fragment shader -- end

	public RenderTest5(Context context) {
		super(context);				
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d(LOG_TAG, "onDrawFrame() : beginning...");
		
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
	
		// draws
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
		
		Log.d(LOG_TAG, "onDrawFrame() : ending...");
	}
	
	private void drawSquare(final FloatBuffer squareBuffer){
		Log.d(LOG_TAG, "drawSquare() is called...");
		
		squareBuffer.position(0);
		GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, 
				false, strideBytes, squareBuffer);
		GLES20.glEnableVertexAttribArray(positionHandle);
		
		squareBuffer.position(3);
		GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, 
				false, strideBytes, squareBuffer);
		GLES20.glEnableVertexAttribArray(colorHandle);
		
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		
		GLES20.glViewport(0, 0, width, height);
		
		// TODO These must be modified sometime later
		float ratio = (float)width / height;
		float left = -ratio;
		float right = ratio;
		float bottom = -1.0f;
		float top = 1.0f;
		float near = 1.0f;
		float far = 10.0f;
		
		Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
	
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);		
		Matrix.setLookAtM(viewMatrix, 0, 0.0f, 0.0f, 4.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		
		squareVerticesBuffer = this.createFloatBuffer(squareVerticesData);
		originVerticesBuffer = this.createFloatBuffer(originVerticesData);
		
		int vertexShaderHandle = this.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
		int fragmentShaderHandle = this.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
		int programHandle = this.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, 
				new String[]{"a_Position", "a_Color"});
		
		MVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
		positionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
		colorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
		
		GLES20.glUseProgram(programHandle);
	}
}
