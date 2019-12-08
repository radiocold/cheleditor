package com.megavil.cheleditor.core;

import org.joml.Matrix4f;

public class CameraPerspective extends Camera {
	
	protected Matrix4f mPerspective;
	private float fovy;
	private float aspect;
	private float near;
	private float far;
	
	public CameraPerspective(float _fovy , float _aspect , float _near , float _far) {
		super();
		mPerspective = new Matrix4f();
		mPerspective.perspective(_fovy, _aspect, _near, _far);
		
		fovy = _fovy;
		aspect = _aspect;
		near = _near;
		far = _far;
	}
	
	public Matrix4f getMatrixPerspective() {
		return mPerspective;
	}
	
	public void setAspectRatio(float _aspect) {
		mPerspective.identity();
		mPerspective.perspective(fovy, _aspect, near, far);
		aspect = _aspect;
	}
	
}
