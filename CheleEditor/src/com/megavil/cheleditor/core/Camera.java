package com.megavil.cheleditor.core;

import org.joml.Matrix4f;

public class Camera extends Node3D {
	
	protected Matrix4f mView;
	protected Matrix4f mViewInv;
	
	public Camera() {
		mView = new Matrix4f();
		mViewInv = new Matrix4f();
	}
	
	public Matrix4f getMatrixView() {
		mView.translate(position);
		mView.rotate(quaternion);
		mView.scale(scale);
		return mView;
	}
	
	
	public Matrix4f getMatrixViewInv() {
		return mView.invert(mViewInv);
	}
	
}
