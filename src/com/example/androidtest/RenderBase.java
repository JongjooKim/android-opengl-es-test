package com.example.androidtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R.anim;
import android.R.integer;
import android.R.xml;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;


abstract public class RenderBase implements Renderer {
	private String LOG_TAG = "RenderBase";
	
	protected Context mContext;
	
	public RenderBase(Context context) {
		mContext = context;
	}
	
	public FloatBuffer createFloatBuffer(float data[]){
		ByteBuffer vbb = ByteBuffer.allocateDirect(data.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer outBuffer = vbb.asFloatBuffer();
		outBuffer.put(data).position(0);
		return outBuffer;
	}
	
	public ShortBuffer createShortBuffer(short data[]) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(data.length * 2);
		vbb.order(ByteOrder.nativeOrder());
		ShortBuffer outBuffer = vbb.asShortBuffer();
		outBuffer.put(data).position(0);
		return outBuffer;
	}
	
	public Bitmap getTextureFromBitmapResource(Context context, int resourceId) {
		Bitmap bitmap = null;
		Matrix yFlipMatrix = new Matrix();
		yFlipMatrix.postScale(1, -1); // flip Y axis		
		try {
			bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
			return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), yFlipMatrix, false);
		}
		finally	{
			if (bitmap != null) {
				bitmap.recycle();
			}
		}
	}		
	
	public boolean handleTouchEvent(MotionEvent event) {
		return false;
	}
	
	public boolean handleKeyEvent(int keyCode, KeyEvent event) {
		return false;
	}	
	
	/**
	 * converts screen coordinate(x,y) to OpenGL world coordinate system(x', y', z') in 
	 * orthogonal mode.<br>
	 * <img src="https://drive.google.com/uc?export=download&id=0B-4Nl1O9x0z1amhDd3BWNEk1OFk"
	 * alt="calculation description..."/>
	 * <img src="https://drive.google.com/uc?export=download&id=0B-4Nl1O9x0z1RDRiaGxUVUQtSVU"/>
	 * @param x	screen x
	 * @param y	screen y
	 * @param width screen width
	 * @param height screen height
	 * @return	float array converted to opengl world coordinate system, 
	 * [0] = x', [1] = y', [2] = z'
	 */
	public float[] convertSSC2WSCInOrthogonal(float x, float y, float width, float height) {
		/**
		 * world coordinate converted.
		 * [0] : x, [1] : y, [2] : z
		 */
		float[] wc = new float[3];
		
		wc[0] = (2.0f / width) * x - 1.0f;
		wc[1] = 1.0f - (2.0f / height) * y;
		wc[2] = 0.f;
		
		return wc;
	}	
	
	/*
	public float[] convertSSC2WSCInPerspective1(GL10 gl, float x, float y, 
			int width, int height) {
		float[] posNear = new float[4];
		float[] posFar = new float[4];
		float[] modelViewMatrix = new float[16];
		float[] projectMatrix = new float[16];
		int[] viewport = {0, 0, width, height};
		float winX, winY;
		
		matrixGrabber.getCurrentProjection(gl);
		projectMatrix = matrixGrabber.mProjection;
		matrixGrabber.getCurrentModelView(gl);
		modelViewMatrix = matrixGrabber.mModelView;
				
		winX = x;
		winY = height - y;
		
		// for near plane
		GLU.gluUnProject(winX, winY, 0.0f, 
				modelViewMatrix, 0, projectMatrix, 0, 
				viewport, 0, posNear, 0);		
		
		// for far plane
		GLU.gluUnProject(winX, winY, 1.0f,
				modelViewMatrix, 0, projectMatrix, 0,
				viewport, 0, posFar, 0);		
		
		// turns into modelview mode
		// matrixGrabber.getCurrentModelView(gl);
		// gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		return posNear;
	}
	*/
	
	public float[] convertSSC2WSCInPerspective2(GL10 gl, float x, float y, 
			int width, int height) {		
		/**
		 * world coordinate converted.
		 * [0] : x, [1] : y, [2] : z
		 */
		float[] wc = new float[3];
		
		wc[0] = (2.0f / width) * x - 1.0f;
		wc[1] = 1.0f - (2.0f / height) * y;
		wc[2] = 1.f;
		
		return wc;
	}
	
	public class Vector3 {
		private float x;
		private float y;
		private float z;
		
		public Vector3(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public float getY() {
			return y;
		}
		public void setY(float y) {
			this.y = y;
		}
		public float getZ() {
			return z;
		}
		public void setZ(float z) {
			this.z = z;
		}		
	}
	
	public class Vector2 {
		private float x;
		private float y;
		
		public Vector2(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public float getY() {
			return y;
		}
		public void setY(float y) {
			this.y = y;
		}		
	}
}