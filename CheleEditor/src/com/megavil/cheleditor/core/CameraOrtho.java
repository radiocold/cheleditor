package com.megavil.cheleditor.core;

import org.joml.Matrix4f;

public class CameraOrtho extends Camera {
	
	protected Matrix4f mPerspective;
	
	public CameraOrtho(float _left , float _right , float _bottom , float _top , float _zNear , float _zFar) {
		super();
		mPerspective = new Matrix4f();
		mPerspective.ortho(_left, _right, _bottom, _top, _zNear, _zFar);
	}
	
	public Matrix4f getMatrixPerspective() {
		return mPerspective;
	}
}
	
