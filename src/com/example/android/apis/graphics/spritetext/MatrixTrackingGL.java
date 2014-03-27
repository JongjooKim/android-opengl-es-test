/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.graphics.spritetext;

import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Allows retrieving the current matrix even if the current OpenGL ES
 * driver does not support retrieving the current matrix.
 *
 * Note: the actual matrix may differ from the retrieved matrix, due
 * to differences in the way the math is implemented by GLMatrixWrapper
 * as compared to the way the math is implemented by the OpenGL ES
 * driver.
 */
public class MatrixTrackingGL implements GL, GL10, GL10Ext, GL11, GL11Ext {
	private final String LOG_TAG = "MatrixTrackingGL";
	
	private GL10 mgl;
	private GL10Ext mgl10Ext;
	private GL11 mgl11;
	private GL11Ext mgl11Ext;
	private int mMatrixMode;
	private MatrixStack mCurrent;
	private MatrixStack mModelView;
	private MatrixStack mTexture;
	private MatrixStack mProjection;
	
	private final static boolean _check = false;
	ByteBuffer mByteBuffer;
	FloatBuffer mFloatBuffer;
	float[] mCheckA;
	float[] mCheckB;
	
	public MatrixTrackingGL(GL gl) {
		mgl = (GL10) gl;
       if (gl instanceof GL10Ext) {
    	   mgl10Ext = (GL10Ext) gl;
        }
       if (gl instanceof GL11) {
    	   mgl11 = (GL11) gl;
        }
       if (gl instanceof GL11Ext) {
    	   mgl11Ext = (GL11Ext) gl;
        }
        
       Log.d(LOG_TAG, "constructor() : " + ", mgl10Ext : " + mgl10Ext + 
    		   ", mgl11 : " + mgl11 + ", mgl11Ext : " + mgl11Ext);
        
		mModelView = new MatrixStack();
		mProjection = new MatrixStack();
		mTexture = new MatrixStack();
		mCurrent = mModelView;
		mMatrixMode = GL10.GL_MODELVIEW;
    }

    // ---------------------------------------------------------------------
    // GL10 methods:

    public void glActiveTexture(int texture) {
    	Log.d(LOG_TAG, "glActiveTexture() : texture : " + texture);
    	
    	mgl.glActiveTexture(texture);
    }

    public void glAlphaFunc(int func, float ref) {
    	Log.d(LOG_TAG, "glAlphaFunc() : func : " + func + " , ref : " + ref);
    	
        mgl.glAlphaFunc(func, ref);
    }

    public void glAlphaFuncx(int func, int ref) {
    	Log.d(LOG_TAG, "glAlphaFuncx() : func : " + func + ", ref : " + ref);
    	
        mgl.glAlphaFuncx(func, ref);
    }

    public void glBindTexture(int target, int texture) {    	
    	Log.d(LOG_TAG, "glBindTexture() : target : " + target + ", textrue : " + target);
        mgl.glBindTexture(target, texture);
    }

    public void glBlendFunc(int sfactor, int dfactor) {
    	Log.d(LOG_TAG, "glBlendFunc() : sfactor : " + sfactor + ", dfactor : " + dfactor);
        mgl.glBlendFunc(sfactor, dfactor);
    }

    public void glClear(int mask) {
    	Log.d(LOG_TAG, "glClear() : mask : " + mask);
        mgl.glClear(mask);
    }

    public void glClearColor(float red, float green, float blue, float alpha) {
    	Log.d(LOG_TAG, "glClearColor() : red : " + red + ", green : " + green +
    			", blue : " + blue + ", alpha : " + alpha);
        mgl.glClearColor(red, green, blue, alpha);
    }

    public void glClearColorx(int red, int green, int blue, int alpha) {
    	Log.d(LOG_TAG, "glClearColorx() : red : " + red + ", green : " + green +
    			", blue : " + blue + ", alpha : " + alpha);
        mgl.glClearColorx(red, green, blue, alpha);
    }

    public void glClearDepthf(float depth) {
    	Log.d(LOG_TAG, "glClearDepthf() : depth : " + depth);
        mgl.glClearDepthf(depth);
    }

    public void glClearDepthx(int depth) {
    	Log.d(LOG_TAG, "glClearDepthx() : depth : " + depth);
        mgl.glClearDepthx(depth);
    }

    public void glClearStencil(int s) {
    	Log.d(LOG_TAG, "glClearStencil() : s : " + s);
        mgl.glClearStencil(s);
    }

    public void glClientActiveTexture(int texture) {
    	Log.d(LOG_TAG, "glClientActiveTexture() : texture : " + texture);
        mgl.glClientActiveTexture(texture);
    }

    public void glColor4f(float red, float green, float blue, float alpha) {
    	Log.d(LOG_TAG, "glColor4f() : red : " + red + ", green : " + green + 
    			", blue : " + blue + ", alpha : " + alpha);
        mgl.glColor4f(red, green, blue, alpha);
    }

    public void glColor4x(int red, int green, int blue, int alpha) {
    	Log.d(LOG_TAG, "glColor4x() : red : " + red + ", green : " + green +
    			", blue : " + blue + ", alpha : " + alpha);
        mgl.glColor4x(red, green, blue, alpha);
    }

    public void glColorMask(boolean red, boolean green, boolean blue,
            boolean alpha) {
    	Log.d(LOG_TAG, "glColorMask() : red : " + red + ", green : " + green +
    			", blue : " + blue + ", alpha : " + alpha);
        mgl.glColorMask(red, green, blue, alpha);
    }

    public void glColorPointer(int size, int type, int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "glColorPointer() : size : " + size + ", type : " + type +
    			", stride : " + stride + ", pointer : " + pointer);
        mgl.glColorPointer(size, type, stride, pointer);
    }

    public void glCompressedTexImage2D(int target, int level,
            int internalformat, int width, int height, int border,
            int imageSize, Buffer data) {
    	Log.d(LOG_TAG, "glCompressedTexImage2D() : target : " + target + ", level : " + level +
    			", internalformat : " + internalformat + ", width : " + width + 
    			", height : " + height + ", border : " + border +
    			", imageSize : " + imageSize + ", data : " + data);
        mgl.glCompressedTexImage2D(target, level, internalformat, width,
                height, border, imageSize, data);
    }

    public void glCompressedTexSubImage2D(int target, int level, int xoffset,
            int yoffset, int width, int height, int format, int imageSize,
            Buffer data) {
    	Log.d(LOG_TAG, "glCompressedTexSubImage2D() : target : " + target +
    			", level : " + level + ", xoffset : " + xoffset + ", yoffset : " + yoffset +
    			", width : " + width + ", height : " + height + ", format : " + format +
    			", imageSize : " + imageSize + ", data : " + data);
        mgl.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width,
                height, format, imageSize, data);
    }

    public void glCopyTexImage2D(int target, int level, int internalformat,
            int x, int y, int width, int height, int border) {
    	Log.d(LOG_TAG, "glCopyTexImage2D() : target : " + target + ", level : " + level + 
    			", internalformat : " + internalformat + ", x : " + x +
    			", y : " + y + ", width : " + width + ", height : " + height +
    			", border : " + border);
        mgl.glCopyTexImage2D(target, level, internalformat, x, y, width,
                height, border);
    }

    public void glCopyTexSubImage2D(int target, int level, int xoffset,
            int yoffset, int x, int y, int width, int height) {
    	Log.d(LOG_TAG, "glCopyTexSubImage2D() : target : " + target + 
    			", level : " + level + ", xoffset : " + xoffset + ", yoffset : " + yoffset +
    			", x : " + x + ", y : " + y +", width : " + width + ", height : " + height);
        mgl.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width,
                height);
    }

    public void glCullFace(int mode) {
    	Log.d(LOG_TAG, "glCullFace() : mode : " + mode);
        mgl.glCullFace(mode);
    }

    public void glDeleteTextures(int n, int[] textures, int offset) {
    	Log.d(LOG_TAG, "glDeleteTextures() : n : " + n + ", textures : " + textures +
    			", offset : " + offset);
        mgl.glDeleteTextures(n, textures, offset);
    }

    public void glDeleteTextures(int n, IntBuffer textures) {
    	Log.d(LOG_TAG, "glDeleteTextures() : n : " + n + ", textures : " + textures);
        mgl.glDeleteTextures(n, textures);
    }

    public void glDepthFunc(int func) {
    	Log.d(LOG_TAG, "glDepthFunc() : func : " + func);
        mgl.glDepthFunc(func);
    }

    public void glDepthMask(boolean flag) {
    	Log.d(LOG_TAG, "glDepthMask() : flag : " + flag);
        mgl.glDepthMask(flag);
    }

    public void glDepthRangef(float near, float far) {
    	Log.d(LOG_TAG, "glDepthRangef() : near : " + near + ", far : " + far);
        mgl.glDepthRangef(near, far);
    }

    public void glDepthRangex(int near, int far) {
    	Log.d(LOG_TAG, "glDepthRangex() : near : " + near + ", far : " + far);
        mgl.glDepthRangex(near, far);
    }

    public void glDisable(int cap) {
    	Log.d(LOG_TAG, "glDisable() : cap : " + cap);
        mgl.glDisable(cap);
    }

    public void glDisableClientState(int array) {
    	Log.d(LOG_TAG, "glDisableClientState() : array : " + array);
        mgl.glDisableClientState(array);
    }

    public void glDrawArrays(int mode, int first, int count) {
    	Log.d(LOG_TAG, "glDrawArrays() : mode : " + mode + ", first : " + first +
    			", count : " + count);
        mgl.glDrawArrays(mode, first, count);
    }

    public void glDrawElements(int mode, int count, int type, Buffer indices) {
    	Log.d(LOG_TAG, "glDrawElements() : mode : " + mode + ", count : " + count +
    			", type : " + type + ", indices : " + indices);
        mgl.glDrawElements(mode, count, type, indices);
    }

    public void glEnable(int cap) {
    	Log.d(LOG_TAG, "glEnable() : cap : " + cap);
        mgl.glEnable(cap);
    }

    public void glEnableClientState(int array) {
    	Log.d(LOG_TAG, "glEnableClientState() : array : " + array);
        mgl.glEnableClientState(array);
    }

    public void glFinish() {
    	Log.d(LOG_TAG, "glFinish() is called...");
        mgl.glFinish();
    }

    public void glFlush() {
    	Log.d(LOG_TAG, "glFlush() is called...");
        mgl.glFlush();
    }

    public void glFogf(int pname, float param) {
    	Log.d(LOG_TAG, "glFogf() : pname : " + pname + ", param : " + param);
        mgl.glFogf(pname, param);
    }

    public void glFogfv(int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glFogfv() : pname : " + pname + ", params : " + params +
    			", offset : " + offset);
        mgl.glFogfv(pname, params, offset);
    }

    public void glFogfv(int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glFogfv() : pname : " + pname + ", params : " + params);
        mgl.glFogfv(pname, params);
    }

    public void glFogx(int pname, int param) {
    	Log.d(LOG_TAG, "glFogx() : pname : " + pname + ", param : " + param);
        mgl.glFogx(pname, param);
    }

    public void glFogxv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glFogxv() : pname : " + pname + ", params : " + params +
    			", offset : " + offset);
        mgl.glFogxv(pname, params, offset);
    }

    public void glFogxv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glFogxv() : pname : " + pname + ", params : " + params);
        mgl.glFogxv(pname, params);
    }

    public void glFrontFace(int mode) {
    	Log.d(LOG_TAG, "glFrontFace() : mode : " + mode);
        mgl.glFrontFace(mode);
    }

    public void glFrustumf(float left, float right, float bottom, float top,
            float near, float far) {
    	Log.d(LOG_TAG, "glFrustumf() : left : " + left + ", right : " + ", bottom : " + bottom +
    			", top : " + top + " near : " + near + ", far : " + far);
        mCurrent.glFrustumf(left, right, bottom, top, near, far);
        mgl.glFrustumf(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glFrustumx(int left, int right, int bottom, int top, int near,
            int far) {
    	Log.d(LOG_TAG, "glFrustumx() : left : " + left + ", right : " + right +
    			", bottom : " + bottom + ", top : " + top + ", near : " + near +
    			", far : " + far);
        mCurrent.glFrustumx(left, right, bottom, top, near, far);
        mgl.glFrustumx(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glGenTextures(int n, int[] textures, int offset) {
    	Log.d(LOG_TAG, "glGenTextures() : n : " + n + ", textures : " + textures +
    			", offset : " + offset);
        mgl.glGenTextures(n, textures, offset);
    }

    public void glGenTextures(int n, IntBuffer textures) {
    	Log.d(LOG_TAG, "glGenTextures() : n : " + n + ", textures : " + textures);
        mgl.glGenTextures(n, textures);
    }

    public int glGetError() {
    	Log.d(LOG_TAG, "glGetError() is called...");
        int result = mgl.glGetError();
        return result;
    }

    public void glGetIntegerv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetIntegerv() : pname : " + pname + ", params : " + params +
    			", offset : " + offset);
        mgl.glGetIntegerv(pname, params, offset);
    }

    public void glGetIntegerv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetIntegerv() : pname : " + pname + 
    			", params : " + params);
        mgl.glGetIntegerv(pname, params);
    }

    public String glGetString(int name) {
    	Log.d(LOG_TAG, "glGetString() : name : " + name);
        String result = mgl.glGetString(name);
        return result;
    }

    public void glHint(int target, int mode) {
    	Log.d(LOG_TAG, "glHint() : target : " + target + ", mode : " + mode);
        mgl.glHint(target, mode);
    }

    public void glLightModelf(int pname, float param) {
    	Log.d(LOG_TAG, "glLightModelf() : pname : " + pname + ", param : " + param);
        mgl.glLightModelf(pname, param);
    }

    public void glLightModelfv(int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glLightModelfv() : pname : " + pname + ", params : " + params +
    			", offset : " + offset);
        mgl.glLightModelfv(pname, params, offset);
    }

    public void glLightModelfv(int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glLightModelfv() : pname : " + pname + ", params : " + params);
        mgl.glLightModelfv(pname, params);
    }

    public void glLightModelx(int pname, int param) {
    	Log.d(LOG_TAG, "glLightModelx() : pname : " + pname + ", param : " + param);
        mgl.glLightModelx(pname, param);
    }

    public void glLightModelxv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glLightModelxv() : pname : " + pname + ", params : " + params +
    			", offset : " + offset);
        mgl.glLightModelxv(pname, params, offset);
    }

    public void glLightModelxv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glLightModelxv() : pname : " + pname + ", params : " + params);
        mgl.glLightModelxv(pname, params);
    }

    public void glLightf(int light, int pname, float param) {
    	Log.d(LOG_TAG, "glLightf() : light : " + light + ", pname : " + pname + 
    			", param : " + param);
        mgl.glLightf(light, pname, param);
    }

    public void glLightfv(int light, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glLightfv() : light : " + light + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
        mgl.glLightfv(light, pname, params, offset);
    }

    public void glLightfv(int light, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glLightfv() : light : " + light + ", pname : " + pname +
    			", params : " + params);
        mgl.glLightfv(light, pname, params);
    }

    public void glLightx(int light, int pname, int param) {
    	Log.d(LOG_TAG, "glLightx() : light : " + light + ", pname : " + pname +
    			", param : " + param);
        mgl.glLightx(light, pname, param);
    }

    public void glLightxv(int light, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glLightxv() : light : " + light + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
        mgl.glLightxv(light, pname, params, offset);
    }

    public void glLightxv(int light, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glLightxv() : light : " + light + ", pname : " + pname +
    			", params : " + params);
        mgl.glLightxv(light, pname, params);
    }

    public void glLineWidth(float width) {
    	Log.d(LOG_TAG, "glLineWidth() : width : " + width);
        mgl.glLineWidth(width);
    }

    public void glLineWidthx(int width) {
    	Log.d(LOG_TAG, "glLineWidthx() : width : " + width);
        mgl.glLineWidthx(width);
    }

    public void glLoadIdentity() {
    	Log.d(LOG_TAG, "glLoadIdentity() is called...");
    	
        mCurrent.glLoadIdentity();
        mgl.glLoadIdentity();
        if ( _check) check();
    }

    public void glLoadMatrixf(float[] m, int offset) {
    	Log.d(LOG_TAG, "glLoadMatrixf() : m : " + m + ", offset : " + offset);
    	
        mCurrent.glLoadMatrixf(m, offset);
        mgl.glLoadMatrixf(m, offset);
        if ( _check) check();
    }

    public void glLoadMatrixf(FloatBuffer m) {
    	Log.d(LOG_TAG, "glLoadMatrixf() : m : " + m);
    	
        int position = m.position();
        mCurrent.glLoadMatrixf(m);
        m.position(position);
        mgl.glLoadMatrixf(m);
        if ( _check) check();
    }

    public void glLoadMatrixx(int[] m, int offset) {
    	Log.d(LOG_TAG, "glLoadMatrixx() : m : " + m + ", offset : " + offset);
    	
        mCurrent.glLoadMatrixx(m, offset);
        mgl.glLoadMatrixx(m, offset);
        if ( _check) check();
    }

    public void glLoadMatrixx(IntBuffer m) {
    	Log.d(LOG_TAG, "glLoadMatrixx() : m : " + m);
        int position = m.position();
        mCurrent.glLoadMatrixx(m);
        m.position(position);
        mgl.glLoadMatrixx(m);
        if ( _check) check();
    }

    public void glLogicOp(int opcode) {
    	Log.d(LOG_TAG, "glLogicOp() : opcode : " + opcode);
    	
        mgl.glLogicOp(opcode);
    }

    public void glMaterialf(int face, int pname, float param) {
    	Log.d(LOG_TAG, "glMaterialf() : face : " + face + ", pname : " + pname +
    			", param : " + param);
        mgl.glMaterialf(face, pname, param);
    }

    public void glMaterialfv(int face, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glMaterialfv() : face : " + face + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
        mgl.glMaterialfv(face, pname, params, offset);
    }

    public void glMaterialfv(int face, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glMaterialfv() : face : " + face + ", pname : " + pname +
    			", params : " + params);
        mgl.glMaterialfv(face, pname, params);
    }

    public void glMaterialx(int face, int pname, int param) {
    	Log.d(LOG_TAG, "glMaterialx() : face : " + face + ", pname : " + pname +
    			", param : " + param);
        mgl.glMaterialx(face, pname, param);
    }

    public void glMaterialxv(int face, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glMaterialxv() : face : " + face + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
        mgl.glMaterialxv(face, pname, params, offset);
    }

    public void glMaterialxv(int face, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glMaterialxv() : face : " + face + ", pname : " + pname +
    			", params : " + params);
        mgl.glMaterialxv(face, pname, params);
    }

    public void glMatrixMode(int mode) {
    	Log.d(LOG_TAG, "glMatrixMode() : mode : " + mode);
        switch (mode) {
        case GL10.GL_MODELVIEW:
            mCurrent = mModelView;
            break;
        case GL10.GL_TEXTURE:
            mCurrent = mTexture;
            break;
        case GL10.GL_PROJECTION:
            mCurrent = mProjection;
            break;
        default:
            throw new IllegalArgumentException("Unknown matrix mode: " + mode);
        }
        mgl.glMatrixMode(mode);
        mMatrixMode = mode;
        if ( _check) check();
    }

    public void glMultMatrixf(float[] m, int offset) {
    	Log.d(LOG_TAG, "glMultMatrixf() : m : " + m + ", offset : " + offset);
        mCurrent.glMultMatrixf(m, offset);
        mgl.glMultMatrixf(m, offset);
        if ( _check) check();
    }

    public void glMultMatrixf(FloatBuffer m) {
    	Log.d(LOG_TAG, "glMultMatrixf() : m : " + m);
        int position = m.position();
        mCurrent.glMultMatrixf(m);
        m.position(position);
        mgl.glMultMatrixf(m);
        if ( _check) check();
    }

    public void glMultMatrixx(int[] m, int offset) {
    	Log.d(LOG_TAG, "glMultMatrixx() : m : " + m + ", offset : " + offset);
        mCurrent.glMultMatrixx(m, offset);
        mgl.glMultMatrixx(m, offset);
        if ( _check) check();
    }

    public void glMultMatrixx(IntBuffer m) {
    	Log.d(LOG_TAG, "glMultMatrixx() : m : " + m);
        int position = m.position();
        mCurrent.glMultMatrixx(m);
        m.position(position);
        mgl.glMultMatrixx(m);
        if ( _check) check();
    }

    public void glMultiTexCoord4f(int target,
            float s, float t, float r, float q) {
    	Log.d(LOG_TAG, "glMultiTexCoord4f() : target : " + target + ", s : " + s +
    			", t : " + t + ", r : " + r + ", q : " + q);
    	
        mgl.glMultiTexCoord4f(target, s, t, r, q);
    }

    public void glMultiTexCoord4x(int target, int s, int t, int r, int q) {
    	Log.d(LOG_TAG, "glMultiTexCoord4x() : target : " + target + ", s : " + s +
    			", t : " + t + ", r : " + r + ", q : " + q);
        mgl.glMultiTexCoord4x(target, s, t, r, q);
    }

    public void glNormal3f(float nx, float ny, float nz) {
    	Log.d(LOG_TAG, "glNormal3f() : nx : " + nx + ", ny : " + ny + ", nz : " + nz);
        mgl.glNormal3f(nx, ny, nz);
    }

    public void glNormal3x(int nx, int ny, int nz) {
    	Log.d(LOG_TAG, "glNormal3x() : nx : " + nx + ", ny : " + ny +
    			", nz : " + nz);
        mgl.glNormal3x(nx, ny, nz);
    }

    public void glNormalPointer(int type, int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "glNormalPointer() : type : " + type + ", stride : " + stride +
    			", pointer : " + pointer);
        mgl.glNormalPointer(type, stride, pointer);
    }

    public void glOrthof(float left, float right, float bottom, float top,
            float near, float far) {
    	Log.d(LOG_TAG, "glOrthof() : left : " + left + ", right : " + right + 
    			", bottom : " + bottom + ", top : " + top + ", near : " + near +
    			", far : " + far);
        mCurrent.glOrthof(left, right, bottom, top, near, far);
        mgl.glOrthof(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glOrthox(int left, int right, int bottom, int top, int near,
            int far) {
    	Log.d(LOG_TAG, "glOrthox() : left : " + left + ", right : " + right +
    			", bottom : " + bottom + ", top : " + top + ", near : " + near +
    			", far : " + far);
        mCurrent.glOrthox(left, right, bottom, top, near, far);
        mgl.glOrthox(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glPixelStorei(int pname, int param) {
    	Log.d(LOG_TAG, "glPixelStorei() : pname : " + pname + 
    			", param : " + param);
        mgl.glPixelStorei(pname, param);
    }

    public void glPointSize(float size) {
    	Log.d(LOG_TAG, "glPointSize() : size : " + size);
        mgl.glPointSize(size);
    }

    public void glPointSizex(int size) {
    	Log.d(LOG_TAG, "glPointSizex() : size : " + size);
        mgl.glPointSizex(size);
    }

    public void glPolygonOffset(float factor, float units) {
    	Log.d(LOG_TAG, "glPolygonOffset() : factor : " + factor + ", units : " + units);
        mgl.glPolygonOffset(factor, units);
    }

    public void glPolygonOffsetx(int factor, int units) {
    	Log.d(LOG_TAG, "glPolygonOffsetx() : factor : " + factor +
    			", units : " + units);
        mgl.glPolygonOffsetx(factor, units);
    }

    public void glPopMatrix() {
    	Log.d(LOG_TAG, "glPopMatrix() is called...");
    	
    	mCurrent.glPopMatrix();
    	mgl.glPopMatrix();
    	if( _check) check();
    }

    public void glPushMatrix() {
    	Log.d(LOG_TAG, "glPushMatrix() is called...");
    	
    	mCurrent.glPushMatrix();
    	mgl.glPushMatrix();
    	if ( _check) check();
    }

    public void glReadPixels(int x, int y, int width, int height, int format,
            int type, Buffer pixels) {
    	Log.d(LOG_TAG, "glReadPixels() : x : " + x + ", y : " + y + 
    			", width : " + width + ", height : " + ", format : " + format +
    			", type : " + type + ", pixels : " + pixels);
        mgl.glReadPixels(x, y, width, height, format, type, pixels);
    }

    public void glRotatef(float angle, float x, float y, float z) {
    	Log.d(LOG_TAG, "glRotatef() : angle : " + angle + ", x : " + x +
    			", y : " + y + ", z : " + z);
        mCurrent.glRotatef(angle, x, y, z);
        mgl.glRotatef(angle, x, y, z);
        if ( _check) check();
    }

    public void glRotatex(int angle, int x, int y, int z) {
    	Log.d(LOG_TAG, "glRotatex() : angle : " + angle + ", x : " + x +
    			", y : " + y + ", z : " + z);
        mCurrent.glRotatex(angle, x, y, z);
        mgl.glRotatex(angle, x, y, z);
        if ( _check) check();
    }

    public void glSampleCoverage(float value, boolean invert) {
    	Log.d(LOG_TAG, "glSampleCoverage() : value : " + value + 
    			", invert : " + invert);
        mgl.glSampleCoverage(value, invert);
    }

    public void glSampleCoveragex(int value, boolean invert) {
    	Log.d(LOG_TAG, "glSampleCoveragex() : value : " + value +
    			", invert : " + invert);
        mgl.glSampleCoveragex(value, invert);
    }

    public void glScalef(float x, float y, float z) {
    	Log.d(LOG_TAG, "glScalef() : x : " + x + ", y : " + y + ", z : " + z);
        mCurrent.glScalef(x, y, z);
        mgl.glScalef(x, y, z);
        if ( _check) check();
    }

    public void glScalex(int x, int y, int z) {
    	Log.d(LOG_TAG, "glScalex() : x : " + x + ", y : " + y + ", z : " + z);
        mCurrent.glScalex(x, y, z);
        mgl.glScalex(x, y, z);
        if ( _check) check();
    }

    public void glScissor(int x, int y, int width, int height) {
    	Log.d(LOG_TAG, "glScissor() : x : " + x + ", y : " + y + 
    			", width : " + width + ", height : " + height);
        mgl.glScissor(x, y, width, height);
    }

    public void glShadeModel(int mode) {
    	Log.d(LOG_TAG, "glShadeModel() : mode : " + mode);
        mgl.glShadeModel(mode);
    }

    public void glStencilFunc(int func, int ref, int mask) {
    	Log.d(LOG_TAG, "glStencilFunc() : func : " + func + ", ref : " + ref +
    			", mask : " + mask);
        mgl.glStencilFunc(func, ref, mask);
    }

    public void glStencilMask(int mask) {
    	Log.d(LOG_TAG, "glStencilMask() : mask : " + mask);
        mgl.glStencilMask(mask);
    }

    public void glStencilOp(int fail, int zfail, int zpass) {
    	Log.d(LOG_TAG, "glStencilOp() : fail : " + fail + ", zfail : " + zfail +
    			", zpass : " + zpass);
        mgl.glStencilOp(fail, zfail, zpass);
    }

    public void glTexCoordPointer(int size, int type,
            int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "glTexCoordPointer() : size : " + size + ", type : " + type +
    			", stride : " + stride + ", pointer : " + pointer);
        mgl.glTexCoordPointer(size, type, stride, pointer);
    }

    public void glTexEnvf(int target, int pname, float param) {
    	Log.d(LOG_TAG, "glTexEnvf() : target : " + target + ", pname : " + pname +
    			", param : " + param);
        mgl.glTexEnvf(target, pname, param);
    }

    public void glTexEnvfv(int target, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glTexEnvfv() : target : " + target + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
        mgl.glTexEnvfv(target, pname, params, offset);
    }

    public void glTexEnvfv(int target, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glTexEnvfv() : target : " + target + ", pname : " + pname +
    			", params : " + params);
        mgl.glTexEnvfv(target, pname, params);
    }

    public void glTexEnvx(int target, int pname, int param) {
    	Log.d(LOG_TAG, "glTexEnvx() : target : " + target + ", pname : " + pname +
    			", param : " + param);
        mgl.glTexEnvx(target, pname, param);
    }

    public void glTexEnvxv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glTexEnvxv() : target : " + target + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
        mgl.glTexEnvxv(target, pname, params, offset);
    }

    public void glTexEnvxv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glTexEnvxv() : target : " + target + ", pname : " +
    			pname + ", params : " + params);
        mgl.glTexEnvxv(target, pname, params);
    }

    public void glTexImage2D(int target, int level, int internalformat,
            int width, int height, int border, int format, int type,
            Buffer pixels) {
    	Log.d(LOG_TAG, "glTexImage2D() : target : " + target + ", level : " + level +
    			", internalformat : " + internalformat + ", width : " + width +
    			", height : " + height + ", border : " + border + ", format : " + format +
    			", type : " + type +", pixels");
        mgl.glTexImage2D(target, level, internalformat, width, height, border,
                format, type, pixels);
    }

    public void glTexParameterf(int target, int pname, float param) {
    	Log.d(LOG_TAG, "glTexParameterf() : target : " + target + ", pname : " + pname +
    			", param : " + param);
        mgl.glTexParameterf(target, pname, param);
    }

    public void glTexParameterx(int target, int pname, int param) {
    	Log.d(LOG_TAG, "glTexParameterx() : target : " + target + ", pname : " + pname +
    			", param : " + param);
        mgl.glTexParameterx(target, pname, param);
    }

    public void glTexParameteriv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glTexParameteriv() : target : " + target + ", params : " + params +
    			", offset : " + offset);
        mgl11.glTexParameteriv(target, pname, params, offset);
    }

    public void glTexParameteriv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glTexParameteriv() : target : " + target + ", pname : " + pname +
    			", params : " + params);
        mgl11.glTexParameteriv(target, pname, params);
    }

    public void glTexSubImage2D(int target, int level, int xoffset,
            int yoffset, int width, int height, int format, int type,
            Buffer pixels) {
    	Log.d(LOG_TAG, "glTexSubImage2D() : target : " + target + ", level : " + level +
    			", xoffset : " + xoffset + ", yoffset : " + yoffset + ", width : " + width +
    			", height : " + height + ", format : " + format + ", type : " + type + 
    			", pixels : " + pixels);
        mgl.glTexSubImage2D(target, level, xoffset, yoffset, width, height,
                format, type, pixels);
    }

    public void glTranslatef(float x, float y, float z) {
    	Log.d(LOG_TAG, "glTranslatef() : x : " + x + ", y : " + y + ", z : " + z);
        mCurrent.glTranslatef(x, y, z);
        mgl.glTranslatef(x, y, z);
        if ( _check) check();
    }

    public void glTranslatex(int x, int y, int z) {
    	Log.d(LOG_TAG, "glTranslatex() : x : " + x + ", y : " + y + ", z : " + z);
        mCurrent.glTranslatex(x, y, z);
        mgl.glTranslatex(x, y, z);
        if ( _check) check();
    }

    public void glVertexPointer(int size, int type,
            int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "glVertexPointer() : size : " + size + ", type : " + type +
    			", stride : " + stride +", pointer : " + pointer);
        mgl.glVertexPointer(size, type, stride, pointer);
    }

    public void glViewport(int x, int y, int width, int height) {
    	Log.d(LOG_TAG, "glViewport() : x : " + x + ", y : " + y + ", width : " + width +
    			", height : " + height);
        mgl.glViewport(x, y, width, height);
    }

    public void glClipPlanef(int plane, float[] equation, int offset) {
    	Log.d(LOG_TAG, "glClipPlanef() : plane : " + plane + ", equation : " + equation +
    			", offset : " + offset);
        mgl11.glClipPlanef(plane, equation, offset);
    }

    public void glClipPlanef(int plane, FloatBuffer equation) {
    	Log.d(LOG_TAG, "glClipPlanef() : plane : " + plane + ", equation : " + equation);
        mgl11.glClipPlanef(plane, equation);
    }

    public void glClipPlanex(int plane, int[] equation, int offset) {
    	Log.d(LOG_TAG, "glClipPlanex() : plane : " + plane + ", equation : " + equation +
    			", offset : " + offset);
        mgl11.glClipPlanex(plane, equation, offset);
    }

    public void glClipPlanex(int plane, IntBuffer equation) {
    	Log.d(LOG_TAG, "glClipPlanex() : plane : " + plane + ", equation : " + equation);
        mgl11.glClipPlanex(plane, equation);
    }

    // Draw Texture Extension

    public void glDrawTexfOES(float x, float y, float z,
        float width, float height) {
    	Log.d(LOG_TAG, "glDrawTexfOES() : x : " + x + ", y : " + y + ", z : " + z +
    			", width : " + width + ", height : " + height);    	
        mgl11Ext.glDrawTexfOES(x, y, z, width, height);
    }

    public void glDrawTexfvOES(float[] coords, int offset) {
    	Log.d(LOG_TAG, "glDrawTexfvOES() : coords : " + coords + ", offset : " + offset);
        mgl11Ext.glDrawTexfvOES(coords, offset);
    }

    public void glDrawTexfvOES(FloatBuffer coords) {
    	Log.d(LOG_TAG, "glDrawTexfvOES() : coords : " + coords);
        mgl11Ext.glDrawTexfvOES(coords);
    }

    public void glDrawTexiOES(int x, int y, int z, int width, int height) {
    	Log.d(LOG_TAG, "glDrawTexiOES() : x : " + x + ", y : " + y + 
    			", z : " + z + ", width : " + width + ", height : " + height);
        mgl11Ext.glDrawTexiOES(x, y, z, width, height);
    }

    public void glDrawTexivOES(int[] coords, int offset) {
    	Log.d(LOG_TAG, "glDrawTexivOES() : coords : " + coords + 
    			", offset : " + offset);
        mgl11Ext.glDrawTexivOES(coords, offset);
    }

    public void glDrawTexivOES(IntBuffer coords) {
    	Log.d(LOG_TAG, "glDrawTexivOES() : coords : " + coords);
        mgl11Ext.glDrawTexivOES(coords);
    }

    public void glDrawTexsOES(short x, short y, short z,
        short width, short height) {
    	Log.d(LOG_TAG, "glDrawTexsOES() : x : " + x + ", y : " + y +
    			", z : " + z + ", width : " + width + ", height : " + height);
        mgl11Ext.glDrawTexsOES(x, y, z, width, height);
    }

    public void glDrawTexsvOES(short[] coords, int offset) {
    	Log.d(LOG_TAG, "glDrawTexsvOES() : coords : " + coords + ", offset : " + offset);
        mgl11Ext.glDrawTexsvOES(coords, offset);
    }

    public void glDrawTexsvOES(ShortBuffer coords) {
    	Log.d(LOG_TAG, "glDrawTexsvOES() : coords : " + coords);
        mgl11Ext.glDrawTexsvOES(coords);
    }

    public void glDrawTexxOES(int x, int y, int z, int width, int height) {
    	Log.d(LOG_TAG, "glDrawTexxOES() : x : " + x + ", y : " + y + 
    			", z : " + z + ", width : " + width + ", height : " + height);
        mgl11Ext.glDrawTexxOES(x, y, z, width, height);
    }

    public void glDrawTexxvOES(int[] coords, int offset) {
    	Log.d(LOG_TAG, "glDrawTexxvOES() : coords : " + coords + 
    			", offset : " + offset);
        mgl11Ext.glDrawTexxvOES(coords, offset);
    }

    public void glDrawTexxvOES(IntBuffer coords) {
    	Log.d(LOG_TAG, "glDrawTexxvOES() : coords : " + coords);
        mgl11Ext.glDrawTexxvOES(coords);
    }

    public int glQueryMatrixxOES(int[] mantissa, int mantissaOffset,
        int[] exponent, int exponentOffset) {
    	Log.d(LOG_TAG, "glQueryMatrixxOES() : mantissa : " + mantissa + 
    			", mantissaOffset : " + mantissaOffset + ", exponent : " + exponent +
    			", exponentOffset : " + exponentOffset);
        return mgl10Ext.glQueryMatrixxOES(mantissa, mantissaOffset,
            exponent, exponentOffset);
    }

    public int glQueryMatrixxOES(IntBuffer mantissa, IntBuffer exponent) {
    	Log.d(LOG_TAG, "glQueryMatrixxOES() : mantissa : " + mantissa +
    			", exponent : " + exponent);
        return mgl10Ext.glQueryMatrixxOES(mantissa, exponent);
    }

    // Unsupported GL11 methods

    public void glBindBuffer(int target, int buffer) {
    	Log.d(LOG_TAG, "glBindBuffer() : target : " + target + ", buffer : " +
    			buffer);
       throw new UnsupportedOperationException();
    }

    public void glBufferData(int target, int size, Buffer data, int usage) {
    	Log.d(LOG_TAG, "glBufferData() : target : " + target + ", size : " + size +
    			", data : " + data + ", usage : " + usage);
       throw new UnsupportedOperationException();
    }

    public void glBufferSubData(int target, int offset, int size, Buffer data) {
    	Log.d(LOG_TAG, "glBufferSubData() : target : " + target + ", offset : " + offset +
    			", size : " + size + ", data : " + data);
       throw new UnsupportedOperationException();
    }

    public void glColor4ub(byte red, byte green, byte blue, byte alpha) {
    	Log.d(LOG_TAG, "glColor4ub() : red : " + red + ", green : " + green +
    			", blue : " + blue + ", alpha : " + alpha);
       throw new UnsupportedOperationException();
    }

    public void glDeleteBuffers(int n, int[] buffers, int offset) {
    	Log.d(LOG_TAG, "glDeleteBuffers() : n : " + n + ", buffers : " + buffers +
    			", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glDeleteBuffers(int n, IntBuffer buffers) {
    	Log.d(LOG_TAG, "glDeleteBuffers() : n : " + n + ", buffers : " + buffers);
       throw new UnsupportedOperationException();
    }

    public void glGenBuffers(int n, int[] buffers, int offset) {
    	Log.d(LOG_TAG, "glGenBuffers() : n : " + n + ", buffers : " + buffers +
    			", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGenBuffers(int n, IntBuffer buffers) {
    	Log.d(LOG_TAG, "glGenBuffers() : n : " + n + ", buffers : " + buffers);
       throw new UnsupportedOperationException();
    }

    public void glGetBooleanv(int pname, boolean[] params, int offset) {
    	Log.d(LOG_TAG, "glGetBooleanv() : pname : " + pname + ", params : " + params +
    			", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetBooleanv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetBooleanv() : pname : " + pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetBufferParameteriv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetBufferParameteriv() : target : " + target + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetBufferParameteriv() : target : " + target + ", pname : " +
    			pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetClipPlanef(int pname, float[] eqn, int offset) {
    	Log.d(LOG_TAG, "glGetClipPlanef() : pname : " + pname + ", eqn : " + eqn +
    			", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetClipPlanef(int pname, FloatBuffer eqn) {
    	Log.d(LOG_TAG, "glGetClipPlanef() : pname : " + pname + ", eqn : " + eqn);
       throw new UnsupportedOperationException();
    }

    public void glGetClipPlanex(int pname, int[] eqn, int offset) {
    	Log.d(LOG_TAG, "glGetClipPlanex() : pname : " + pname + 
    			", eqn : " + eqn + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetClipPlanex(int pname, IntBuffer eqn) {
    	Log.d(LOG_TAG, "glGetClipPlanex() : pname : " + pname + 
    			", eqn : " + eqn);
       throw new UnsupportedOperationException();
    }

    public void glGetFixedv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetFixedv() : pname : " + pname + ", params : " +
    			params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetFixedv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetFixedv() : pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetFloatv(int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glGetFloatv() : pname : " + pname + ", params : " +
    			params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetFloatv(int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glGetFloatv() is called...");
       throw new UnsupportedOperationException();
    }

    public void glGetLightfv(int light, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glGetLightfv() : light : " + light + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetLightfv(int light, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glGetLightfv() : light : " + light + 
    			", pname : " + pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetLightxv(int light, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetLightxv() : light : " + light + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetLightxv(int light, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetLightxv() : light : " + light + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetMaterialfv(int face, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glGetMaterialfv() : face : " + face + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetMaterialfv(int face, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glGetMaterialfv() : face : " + face + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetMaterialxv(int face, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetMaterialxv() : face : " + face + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
    	
       throw new UnsupportedOperationException();
    }

    public void glGetMaterialxv(int face, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetMaterialxv() : face : " + face + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetTexEnviv(int env, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetTexEnviv() : env : " + env + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetTexEnviv(int env, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetTexEnviv() : env : " + env + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetTexEnvxv(int env, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetTexEnvxv() : env : " + env + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetTexEnvxv(int env, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetTexEnvxv() : env : " + env + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetTexParameterfv(int target, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glGetTexParameterfv() : target : " + target +
    			", pname : " + pname + ", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glGetTexParameterfv() : target : " + target + ", pname : " +
    			pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetTexParameteriv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetTexParameteriv() : target : " + target +
    			", pname : " + pname + ", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetTexParameteriv() : target : " + target + ", pname : " +
    			pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glGetTexParameterxv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glGetTexParameterxv() : target : " + target +
    			", pname : " + pname + ", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetTexParameterxv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glGetTexParameterxv() : target : " + target +
    			", pname : " + pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public boolean glIsBuffer(int buffer) {
    	Log.d(LOG_TAG, "glIsBuffer() : buffer : " + buffer);
       throw new UnsupportedOperationException();
    }

    public boolean glIsEnabled(int cap) {
    	Log.d(LOG_TAG, "glIsEnabled() : cap : " + cap);
       throw new UnsupportedOperationException();
    }

    public boolean glIsTexture(int texture) {
    	Log.d(LOG_TAG, "glIsTexture() : texture : " + texture);
       throw new UnsupportedOperationException();
    }

    public void glPointParameterf(int pname, float param) {
    	Log.d(LOG_TAG, "glPointParameterf() : pname : " + pname +
    			", param : " + param);
    	
       throw new UnsupportedOperationException();
    }

    public void glPointParameterfv(int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glPointParameterfv() : pname : " + pname + 
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glPointParameterfv(int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glPointParameterfv() : pname : " + pname + 
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glPointParameterx(int pname, int param) {
    	Log.d(LOG_TAG, "glPointParameterx() : pname : " + pname + 
    			", param : " + param);
        throw new UnsupportedOperationException();
    }

    public void glPointParameterxv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glPointParameterxv() : pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glPointParameterxv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glPointParameterxv() : pname : " + pname +
    			", params : " + params);
    	
       throw new UnsupportedOperationException();
    }

    public void glPointSizePointerOES(int type, int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "glPointSizePointerOES() : type : " + type + ", stride : " + stride +
    			", pointer : " + pointer);
    	
       throw new UnsupportedOperationException();
    }

    public void glTexEnvi(int target, int pname, int param) {
    	Log.d(LOG_TAG, "glTexEnvi() : target : " + target + ", pname : " + pname + 
    			", param : " + param);
       throw new UnsupportedOperationException();
    }

    public void glTexEnviv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glTexEnviv() : target : " + target + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glTexEnviv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glTexEnviv() : target : " + target + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glTexParameterfv(int target, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "glTexParameterfv() : target : " + target + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "glTexParameterfv() : target : " + target + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glTexParameteri(int target, int pname, int param) {
    	Log.d(LOG_TAG, "glTexParameteri() : target : " + target + ", pname : " +
    			pname + ", param : " + param);
       throw new UnsupportedOperationException();
    }

    public void glTexParameterxv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "glTexParameterxv() : target : " + target + ", pname : " + pname +
    			", params : " + params + ", offset : " + offset);
    	
       throw new UnsupportedOperationException();
    }

    public void glTexParameterxv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "glTexParameterxv() : target : " + target + ", pname : " + pname +
    			", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glColorPointer(int size, int type, int stride, int offset) {
    	Log.d(LOG_TAG, "glColorPointer() : size : " + size + ", type : " + type + 
    			", stride : " + stride + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glDrawElements(int mode, int count, int type, int offset) {
    	Log.d(LOG_TAG, "glDrawElements() : mode : " + mode + ", count : " + count +
    			", type : " + type + ", offset : " + offset);
       throw new UnsupportedOperationException();
    }

    public void glGetPointerv(int pname, Buffer[] params) {
    	Log.d(LOG_TAG, "glGetPointerv() : pname : " + pname + ", params : " + params);
       throw new UnsupportedOperationException();
    }

    public void glNormalPointer(int type, int stride, int offset) {
    	Log.d(LOG_TAG, "glNormalPointer() : type : " + type + ", stride : " + stride +
    			", offset : " + offset);
    	
    	throw new UnsupportedOperationException();
    }

    public void glTexCoordPointer(int size, int type, int stride, int offset) {
    	Log.d(LOG_TAG, "glTexCoordPointer() : size : " + size + ", type : " + type +
    			", stride : " + stride + ", offset : " + offset);
    	
       throw new UnsupportedOperationException();
    }

    public void glVertexPointer(int size, int type, int stride, int offset) {
    	Log.d(LOG_TAG, "glVertexPointer() : size : " + size + ", type : " + type +
    			", stride : " + stride + ", offset : " + offset);
    	
       throw new UnsupportedOperationException();
    }

    public void glCurrentPaletteMatrixOES(int matrixpaletteindex) {
    	Log.d(LOG_TAG, "glCurrentPaletteMatrixOES() : matrixpaletteindex : " + matrixpaletteindex);
    	
       throw new UnsupportedOperationException();
    }

    public void glLoadPaletteFromModelViewMatrixOES() {
    	Log.d(LOG_TAG, "glLoadPaletteFromModelViewMatrixOES() is called...");
       throw new UnsupportedOperationException();
    }

    public void glMatrixIndexPointerOES(int size, int type, int stride,
            Buffer pointer) {
    	Log.d(LOG_TAG, "glMatrixIndexPointerOES() : size : " + size + ", type : " +
            type + ", stride : " + stride + ", pointer : " + pointer);
    	
       throw new UnsupportedOperationException();
    }

    public void glMatrixIndexPointerOES(int size, int type, int stride,
            int offset) {
    	Log.d(LOG_TAG, "glMatrixIndexPointerOES() : size : " + size + ", type : " +
            type + ", stride : " + stride + ", offset : " + offset);
    	
    	throw new UnsupportedOperationException();
    }

    public void glWeightPointerOES(int size, int type, int stride,
            Buffer pointer) {
    	Log.d(LOG_TAG, "glWeightPointerOES() : size : " + size + ", type : " + type +
    			", stride : " + stride + ", pointer : " + pointer);
    	
		throw new UnsupportedOperationException();
    }

    public void glWeightPointerOES(int size, int type, int stride, int offset) {
    	Log.d(LOG_TAG, "glWeightPointerOES() : size : " + size + ", type : " + type +
    			", stride : " + stride + ", offset : " + offset);
		throw new UnsupportedOperationException();
    }

    /**
     * Get the current matrix
     */

    public void getMatrix(float[] m, int offset) {
    	Log.d(LOG_TAG, "getMatrix() : m : " + m + ", offset : " + offset);
		mCurrent.getMatrix(m, offset);
    }

    /**
     * Get the current matrix mode
     */

    public int getMatrixMode() {
    	Log.d(LOG_TAG, "getMatrixMode() is called...");
        return mMatrixMode;
    }

    private void check() {
    	Log.d(LOG_TAG, "check() is called...");
    	
		int oesMode;
		switch (mMatrixMode) {
			case GL_MODELVIEW:
			    oesMode = GL11.GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES;
			    break;
			case GL_PROJECTION:
			    oesMode = GL11.GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES;
			    break;
			case GL_TEXTURE:
			    oesMode = GL11.GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES;
			    break;
			default:
			    throw new IllegalArgumentException("Unknown matrix mode");
		}
		
		if(mByteBuffer == null) {
		    mCheckA = new float[16];
		    mCheckB = new float[16];
		    mByteBuffer = ByteBuffer.allocateDirect(64);
		    mByteBuffer.order(ByteOrder.nativeOrder());
		    mFloatBuffer = mByteBuffer.asFloatBuffer();
		}
		mgl.glGetIntegerv(oesMode, mByteBuffer.asIntBuffer());
		for(int i = 0; i < 16; i++) {
		    mCheckB[i] = mFloatBuffer.get(i);
		}
		mCurrent.getMatrix(mCheckA, 0);
		
		boolean fail = false;
		for(int i = 0; i < 16; i++) {
		    if (mCheckA[i] != mCheckB[i]) {
		        Log.d(LOG_TAG, "check() : i : " + i + ", a : " + mCheckA[i] + 
		        		", b : " + mCheckB[i]);
		        fail = true;
		    }
		}
		if(fail) {
		    throw new IllegalArgumentException("Matrix math difference.");
		}
    }
}
