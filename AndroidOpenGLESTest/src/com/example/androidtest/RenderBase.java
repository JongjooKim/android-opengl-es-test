package com.example.androidtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R.anim;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;
import android.view.MotionEvent;


abstract public class RenderBase implements Renderer {
	protected Context mContext;
	public RenderBase(Context context){
		mContext = context;		
	}
	
	public FloatBuffer createFloatBuffer(float data[]){
		ByteBuffer vbb = ByteBuffer.allocateDirect(data.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer outBuffer = vbb.asFloatBuffer();
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
	 * converts screen coordinate(x,y) to OpenGL world coordinate system(x', y', z')<br>
	 * <img src="https://drive.google.com/uc?export=download&id=0B-4Nl1O9x0z1amhDd3BWNEk1OFk"
	 * alt="calculation description..."/>
	 * <img src="https://drive.google.com/uc?export=download&id=0B-4Nl1O9x0z1RDRiaGxUVUQtSVU"/>
	 * @param x	screen x
	 * @param y	screen y
	 * @param width screen width
	 * @param height screen height
	 * @return	Matrix converted to opengl world coordinate system(x', y', z')
	 */
	public float[] convertScreenCS2WorldCS(float x, float y, float width, float height) {
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
}