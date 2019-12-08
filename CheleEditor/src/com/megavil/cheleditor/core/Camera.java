package com.megavil.cheleditor.core;

import org.joml.Matrix4f;

public class Camera extends Node3D {
	
	protected Matrix4f mView;
	protected Matrix4f mViewInv;
	
	public Camera() {
		super();
		mViewInv = new Matrix4f();
	}
	
	public Matrix4f getMatrixViewInv() {
		return getCalculateModelMatrix().invert(mViewInv);
	}
	
}
