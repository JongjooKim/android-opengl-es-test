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
	private final String LOG_TAG = "RenderTest4";
	
	private final float[] projectionMatrix = new float[16];
	private final float[] viewMatrix = new float[16];
	private final float[] MVPMatrix = new float[16];
	
	private float vertices[] = {
			10.0f, 200f,  0.0f,
			10.0f, 100f, 0.0f,
			100f, 100f, 0.0f,
	};
	private short indices[] = {0, 1, 2};
	public FloatBuffer vertextBuffer;
	public ShortBuffer indexBuffer;	
	
	private int program;
	
	/*
	 * SHADER Solid
	 * This shader is for rendering a colored primitive, i.e. triangle primitive.
	 */
	private String vertexShaderCode = 
			"uniform	mat4	uMVPMatrix;" +
			"attribute vec4	vPosition;" +
			"void main() {" +
			"	gl_Position = uMVPMatrix * vPosition;" +
			"}";
	private String fragmentShaderCode =
			"precision mediump float;" +
			"void main() {" +
			"	gl_FragColor = vec4(0.0, 1.0, 0, 1);" +
			"}";

	public RenderTest4(Context context) {
		super(context);		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d(LOG_TAG, "onDrawFrame() : beginning...");
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, 3, 
				GLES20.GL_FLOAT, false, 0, vertextBuffer);
		int mtrxHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
		GLES20.glUniformMatrix4fv(mtrxHandle, 1, false, MVPMatrix, 0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, 
				GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		
		Log.d(LOG_TAG, "onDrawFrame() : ending...");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(LOG_TAG, "onSurfaceChanged() : gl : " + gl + ", width : " + width + ", height : " + height);
		
		GLES20.glViewport(0, 0, width, height);
		for(int i = 0; i < 16; i++) {
			projectionMatrix[i] = 0.0f;
			viewMatrix[i] = 0.0f;
			MVPMatrix[i] = 0.0f;
		}
				
		Matrix.orthoM(projectionMatrix, 0, 0f, width, 0.0f, height, 0, 50);
		Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 4f, 0f, 0f, 0f, 0f, 1.0f, 0f);
		Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "onSurfaceCreated() : gl : " + gl);
		
		vertextBuffer = createFloatBuffer(vertices);
		indexBuffer = createShortBuffer(indices);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		
		program = GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertexShader);
		GLES20.glAttachShader(program, fragmentShader);
		GLES20.glLinkProgram(program);
		GLES20.glUseProgram(program);
	}	
	
	private int loadShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);
		
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		
		return shader;
	}
}