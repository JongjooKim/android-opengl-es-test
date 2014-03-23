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
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
       mgl.glActiveTexture(texture);
    }

    public void glAlphaFunc(int func, float ref) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        mgl.glAlphaFunc(func, ref);
    }

    public void glAlphaFuncx(int func, int ref) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        mgl.glAlphaFuncx(func, ref);
    }

    public void glBindTexture(int target, int texture) {    	
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glBindTexture(target, texture);
    }

    public void glBlendFunc(int sfactor, int dfactor) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glBlendFunc(sfactor, dfactor);
    }

    public void glClear(int mask) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClear(mask);
    }

    public void glClearColor(float red, float green, float blue, float alpha) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClearColor(red, green, blue, alpha);
    }

    public void glClearColorx(int red, int green, int blue, int alpha) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClearColorx(red, green, blue, alpha);
    }

    public void glClearDepthf(float depth) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClearDepthf(depth);
    }

    public void glClearDepthx(int depth) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClearDepthx(depth);
    }

    public void glClearStencil(int s) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClearStencil(s);
    }

    public void glClientActiveTexture(int texture) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glClientActiveTexture(texture);
    }

    public void glColor4f(float red, float green, float blue, float alpha) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glColor4f(red, green, blue, alpha);
    }

    public void glColor4x(int red, int green, int blue, int alpha) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glColor4x(red, green, blue, alpha);
    }

    public void glColorMask(boolean red, boolean green, boolean blue,
            boolean alpha) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glColorMask(red, green, blue, alpha);
    }

    public void glColorPointer(int size, int type, int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glColorPointer(size, type, stride, pointer);
    }

    public void glCompressedTexImage2D(int target, int level,
            int internalformat, int width, int height, int border,
            int imageSize, Buffer data) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glCompressedTexImage2D(target, level, internalformat, width,
                height, border, imageSize, data);
    }

    public void glCompressedTexSubImage2D(int target, int level, int xoffset,
            int yoffset, int width, int height, int format, int imageSize,
            Buffer data) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width,
                height, format, imageSize, data);
    }

    public void glCopyTexImage2D(int target, int level, int internalformat,
            int x, int y, int width, int height, int border) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glCopyTexImage2D(target, level, internalformat, x, y, width,
                height, border);
    }

    public void glCopyTexSubImage2D(int target, int level, int xoffset,
            int yoffset, int x, int y, int width, int height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width,
                height);
    }

    public void glCullFace(int mode) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glCullFace(mode);
    }

    public void glDeleteTextures(int n, int[] textures, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDeleteTextures(n, textures, offset);
    }

    public void glDeleteTextures(int n, IntBuffer textures) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDeleteTextures(n, textures);
    }

    public void glDepthFunc(int func) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDepthFunc(func);
    }

    public void glDepthMask(boolean flag) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDepthMask(flag);
    }

    public void glDepthRangef(float near, float far) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDepthRangef(near, far);
    }

    public void glDepthRangex(int near, int far) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDepthRangex(near, far);
    }

    public void glDisable(int cap) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDisable(cap);
    }

    public void glDisableClientState(int array) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDisableClientState(array);
    }

    public void glDrawArrays(int mode, int first, int count) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDrawArrays(mode, first, count);
    }

    public void glDrawElements(int mode, int count, int type, Buffer indices) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glDrawElements(mode, count, type, indices);
    }

    public void glEnable(int cap) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glEnable(cap);
    }

    public void glEnableClientState(int array) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glEnableClientState(array);
    }

    public void glFinish() {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFinish();
    }

    public void glFlush() {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFlush();
    }

    public void glFogf(int pname, float param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFogf(pname, param);
    }

    public void glFogfv(int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFogfv(pname, params, offset);
    }

    public void glFogfv(int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFogfv(pname, params);
    }

    public void glFogx(int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFogx(pname, param);
    }

    public void glFogxv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFogxv(pname, params, offset);
    }

    public void glFogxv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFogxv(pname, params);
    }

    public void glFrontFace(int mode) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glFrontFace(mode);
    }

    public void glFrustumf(float left, float right, float bottom, float top,
            float near, float far) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glFrustumf(left, right, bottom, top, near, far);
        mgl.glFrustumf(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glFrustumx(int left, int right, int bottom, int top, int near,
            int far) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glFrustumx(left, right, bottom, top, near, far);
        mgl.glFrustumx(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glGenTextures(int n, int[] textures, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glGenTextures(n, textures, offset);
    }

    public void glGenTextures(int n, IntBuffer textures) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glGenTextures(n, textures);
    }

    public int glGetError() {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        int result = mgl.glGetError();
        return result;
    }

    public void glGetIntegerv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glGetIntegerv(pname, params, offset);
    }

    public void glGetIntegerv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glGetIntegerv(pname, params);
    }

    public String glGetString(int name) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        String result = mgl.glGetString(name);
        return result;
    }

    public void glHint(int target, int mode) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glHint(target, mode);
    }

    public void glLightModelf(int pname, float param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightModelf(pname, param);
    }

    public void glLightModelfv(int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightModelfv(pname, params, offset);
    }

    public void glLightModelfv(int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightModelfv(pname, params);
    }

    public void glLightModelx(int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightModelx(pname, param);
    }

    public void glLightModelxv(int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightModelxv(pname, params, offset);
    }

    public void glLightModelxv(int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightModelxv(pname, params);
    }

    public void glLightf(int light, int pname, float param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightf(light, pname, param);
    }

    public void glLightfv(int light, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightfv(light, pname, params, offset);
    }

    public void glLightfv(int light, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightfv(light, pname, params);
    }

    public void glLightx(int light, int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightx(light, pname, param);
    }

    public void glLightxv(int light, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightxv(light, pname, params, offset);
    }

    public void glLightxv(int light, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLightxv(light, pname, params);
    }

    public void glLineWidth(float width) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLineWidth(width);
    }

    public void glLineWidthx(int width) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glLineWidthx(width);
    }

    public void glLoadIdentity() {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glLoadIdentity();
        mgl.glLoadIdentity();
        if ( _check) check();
    }

    public void glLoadMatrixf(float[] m, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        mCurrent.glLoadMatrixf(m, offset);
        mgl.glLoadMatrixf(m, offset);
        if ( _check) check();
    }

    public void glLoadMatrixf(FloatBuffer m) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        int position = m.position();
        mCurrent.glLoadMatrixf(m);
        m.position(position);
        mgl.glLoadMatrixf(m);
        if ( _check) check();
    }

    public void glLoadMatrixx(int[] m, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        mCurrent.glLoadMatrixx(m, offset);
        mgl.glLoadMatrixx(m, offset);
        if ( _check) check();
    }

    public void glLoadMatrixx(IntBuffer m) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        int position = m.position();
        mCurrent.glLoadMatrixx(m);
        m.position(position);
        mgl.glLoadMatrixx(m);
        if ( _check) check();
    }

    public void glLogicOp(int opcode) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        mgl.glLogicOp(opcode);
    }

    public void glMaterialf(int face, int pname, float param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMaterialf(face, pname, param);
    }

    public void glMaterialfv(int face, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMaterialfv(face, pname, params, offset);
    }

    public void glMaterialfv(int face, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMaterialfv(face, pname, params);
    }

    public void glMaterialx(int face, int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMaterialx(face, pname, param);
    }

    public void glMaterialxv(int face, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMaterialxv(face, pname, params, offset);
    }

    public void glMaterialxv(int face, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMaterialxv(face, pname, params);
    }

    public void glMatrixMode(int mode) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
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
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glMultMatrixf(m, offset);
        mgl.glMultMatrixf(m, offset);
        if ( _check) check();
    }

    public void glMultMatrixf(FloatBuffer m) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        int position = m.position();
        mCurrent.glMultMatrixf(m);
        m.position(position);
        mgl.glMultMatrixf(m);
        if ( _check) check();
    }

    public void glMultMatrixx(int[] m, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glMultMatrixx(m, offset);
        mgl.glMultMatrixx(m, offset);
        if ( _check) check();
    }

    public void glMultMatrixx(IntBuffer m) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        int position = m.position();
        mCurrent.glMultMatrixx(m);
        m.position(position);
        mgl.glMultMatrixx(m);
        if ( _check) check();
    }

    public void glMultiTexCoord4f(int target,
            float s, float t, float r, float q) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
    	
        mgl.glMultiTexCoord4f(target, s, t, r, q);
    }

    public void glMultiTexCoord4x(int target, int s, int t, int r, int q) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glMultiTexCoord4x(target, s, t, r, q);
    }

    public void glNormal3f(float nx, float ny, float nz) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glNormal3f(nx, ny, nz);
    }

    public void glNormal3x(int nx, int ny, int nz) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glNormal3x(nx, ny, nz);
    }

    public void glNormalPointer(int type, int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glNormalPointer(type, stride, pointer);
    }

    public void glOrthof(float left, float right, float bottom, float top,
            float near, float far) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glOrthof(left, right, bottom, top, near, far);
        mgl.glOrthof(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glOrthox(int left, int right, int bottom, int top, int near,
            int far) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glOrthox(left, right, bottom, top, near, far);
        mgl.glOrthox(left, right, bottom, top, near, far);
        if ( _check) check();
    }

    public void glPixelStorei(int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glPixelStorei(pname, param);
    }

    public void glPointSize(float size) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glPointSize(size);
    }

    public void glPointSizex(int size) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glPointSizex(size);
    }

    public void glPolygonOffset(float factor, float units) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glPolygonOffset(factor, units);
    }

    public void glPolygonOffsetx(int factor, int units) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
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
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glReadPixels(x, y, width, height, format, type, pixels);
    }

    public void glRotatef(float angle, float x, float y, float z) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glRotatef(angle, x, y, z);
        mgl.glRotatef(angle, x, y, z);
        if ( _check) check();
    }

    public void glRotatex(int angle, int x, int y, int z) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glRotatex(angle, x, y, z);
        mgl.glRotatex(angle, x, y, z);
        if ( _check) check();
    }

    public void glSampleCoverage(float value, boolean invert) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glSampleCoverage(value, invert);
    }

    public void glSampleCoveragex(int value, boolean invert) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glSampleCoveragex(value, invert);
    }

    public void glScalef(float x, float y, float z) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glScalef(x, y, z);
        mgl.glScalef(x, y, z);
        if ( _check) check();
    }

    public void glScalex(int x, int y, int z) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glScalex(x, y, z);
        mgl.glScalex(x, y, z);
        if ( _check) check();
    }

    public void glScissor(int x, int y, int width, int height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glScissor(x, y, width, height);
    }

    public void glShadeModel(int mode) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glShadeModel(mode);
    }

    public void glStencilFunc(int func, int ref, int mask) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glStencilFunc(func, ref, mask);
    }

    public void glStencilMask(int mask) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glStencilMask(mask);
    }

    public void glStencilOp(int fail, int zfail, int zpass) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glStencilOp(fail, zfail, zpass);
    }

    public void glTexCoordPointer(int size, int type,
            int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexCoordPointer(size, type, stride, pointer);
    }

    public void glTexEnvf(int target, int pname, float param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexEnvf(target, pname, param);
    }

    public void glTexEnvfv(int target, int pname, float[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexEnvfv(target, pname, params, offset);
    }

    public void glTexEnvfv(int target, int pname, FloatBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexEnvfv(target, pname, params);
    }

    public void glTexEnvx(int target, int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexEnvx(target, pname, param);
    }

    public void glTexEnvxv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexEnvxv(target, pname, params, offset);
    }

    public void glTexEnvxv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexEnvxv(target, pname, params);
    }

    public void glTexImage2D(int target, int level, int internalformat,
            int width, int height, int border, int format, int type,
            Buffer pixels) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexImage2D(target, level, internalformat, width, height, border,
                format, type, pixels);
    }

    public void glTexParameterf(int target, int pname, float param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexParameterf(target, pname, param);
    }

    public void glTexParameterx(int target, int pname, int param) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexParameterx(target, pname, param);
    }

    public void glTexParameteriv(int target, int pname, int[] params, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11.glTexParameteriv(target, pname, params, offset);
    }

    public void glTexParameteriv(int target, int pname, IntBuffer params) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11.glTexParameteriv(target, pname, params);
    }

    public void glTexSubImage2D(int target, int level, int xoffset,
            int yoffset, int width, int height, int format, int type,
            Buffer pixels) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glTexSubImage2D(target, level, xoffset, yoffset, width, height,
                format, type, pixels);
    }

    public void glTranslatef(float x, float y, float z) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glTranslatef(x, y, z);
        mgl.glTranslatef(x, y, z);
        if ( _check) check();
    }

    public void glTranslatex(int x, int y, int z) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mCurrent.glTranslatex(x, y, z);
        mgl.glTranslatex(x, y, z);
        if ( _check) check();
    }

    public void glVertexPointer(int size, int type,
            int stride, Buffer pointer) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glVertexPointer(size, type, stride, pointer);
    }

    public void glViewport(int x, int y, int width, int height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl.glViewport(x, y, width, height);
    }

    public void glClipPlanef(int plane, float[] equation, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11.glClipPlanef(plane, equation, offset);
    }

    public void glClipPlanef(int plane, FloatBuffer equation) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11.glClipPlanef(plane, equation);
    }

    public void glClipPlanex(int plane, int[] equation, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11.glClipPlanex(plane, equation, offset);
    }

    public void glClipPlanex(int plane, IntBuffer equation) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11.glClipPlanex(plane, equation);
    }

    // Draw Texture Extension

    public void glDrawTexfOES(float x, float y, float z,
        float width, float height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexfOES(x, y, z, width, height);
    }

    public void glDrawTexfvOES(float[] coords, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexfvOES(coords, offset);
    }

    public void glDrawTexfvOES(FloatBuffer coords) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexfvOES(coords);
    }

    public void glDrawTexiOES(int x, int y, int z, int width, int height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexiOES(x, y, z, width, height);
    }

    public void glDrawTexivOES(int[] coords, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexivOES(coords, offset);
    }

    public void glDrawTexivOES(IntBuffer coords) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexivOES(coords);
    }

    public void glDrawTexsOES(short x, short y, short z,
        short width, short height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexsOES(x, y, z, width, height);
    }

    public void glDrawTexsvOES(short[] coords, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexsvOES(coords, offset);
    }

    public void glDrawTexsvOES(ShortBuffer coords) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexsvOES(coords);
    }

    public void glDrawTexxOES(int x, int y, int z, int width, int height) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexxOES(x, y, z, width, height);
    }

    public void glDrawTexxvOES(int[] coords, int offset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexxvOES(coords, offset);
    }

    public void glDrawTexxvOES(IntBuffer coords) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        mgl11Ext.glDrawTexxvOES(coords);
    }

    public int glQueryMatrixxOES(int[] mantissa, int mantissaOffset,
        int[] exponent, int exponentOffset) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
        return mgl10Ext.glQueryMatrixxOES(mantissa, mantissaOffset,
            exponent, exponentOffset);
    }

    public int glQueryMatrixxOES(IntBuffer mantissa, IntBuffer exponent) {
    	Log.d(LOG_TAG, "MatrixStack() is called...");
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
    	Log.d(LOG_TAG, "MatrixStack() : env : " + env + ", pname : " + pname +
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
		
		if ( mByteBuffer == null) {
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
		        Log.d("GLMatWrap", "i:" + i + " a:" + mCheckA[i]
		        + " a:" + mCheckB[i]);
		        fail = true;
		    }
		}
		if (fail) {
		    throw new IllegalArgumentException("Matrix math difference.");
		}
    }
}
