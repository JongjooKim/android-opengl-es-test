package com.example.androidtest;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLU;
import android.opengl.GLUtils;

/**
 * This class is for rendering textures at a specific position. 
 * Not completely implemented yet. Need to be filled out sometime later.
 * @author Jongjoo Kim
 *
 */
public class RenderTest1 extends RenderBase {
	private String LOG_TAG = "RenderTest1";	

	private int mTextureID;

	private float vertices1[] = {
		-0.75f, -0.75f,  -1.0f,
		 0.75f, -0.75f,  -1.0f,
		-0.75f,  0.75f,  -1.0f,
		 0.75f,  0.75f,  -1.0f,		
	};
	private float vertices2[] = {
		+0.25f, -0.25f, 0.0f,
		+0.25f, +0.25f, 0.0f,
		-0.25f, -0.25f, 0.0f,		
		-0.25f, +0.25f, 0.0f,
	};

	private float textureCoordinate1[] = {
		 0.0f, 0.0f,
		 1.0f, 0.0f,
		 0.0f, 1.0f,
		 1.0f, 1.0f,		
	};	
	private float textureCoordinate2[] = {
		1.0f, 0.0f,
		1.0f, 1.0f,
		0.0f, 0.0f,		
		0.0f, 1.0f,
	};

	private FloatBuffer mVertexBuffer1;
	private FloatBuffer mTextureBuffer1;
	private FloatBuffer mVertexBuffer2;
	private FloatBuffer mTextureBuffer2;
	
	public RenderTest1(Context context) {
		super(context);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		mVertexBuffer1 = createFloatBuffer(vertices1); 
		mTextureBuffer1 = createFloatBuffer(textureCoordinate1);
		mVertexBuffer2 = createFloatBuffer(vertices2);
		mTextureBuffer2 = createFloatBuffer(textureCoordinate2);		
		
		gl.glEnable(GL10.GL_TEXTURE_2D);

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepthf(1.0f);

		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		gl.glViewport(0, 0, width, height);
		GLU.gluPerspective(gl, 45.0f, 1.0f * width / height, 1.0f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		GLU.gluLookAt(gl, 0.0f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		loadTextures(gl, R.drawable.texture);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer1);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer1);		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		loadTextures(gl, R.drawable.nehe_texture_glass);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer2);		
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer2);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);		
	}
	
	private boolean loadTextures(GL10 gl, int res) {
		Bitmap bmp = getTextureFromBitmapResource(mContext, res);

		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureID = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);

		// Use Nearest for performance.
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);

		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_REPLACE);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);

		bmp.recycle();

		return true;
	}
}
