package com.example.androidtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

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
}