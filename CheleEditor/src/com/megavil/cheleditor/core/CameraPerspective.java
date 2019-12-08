package com.megavil.cheleditor.core;

import org.joml.Matrix4f;

public class CameraPerspective extends Camera {
	
	protected Matrix4f mPerspective;

	public CameraPerspective(float fovy , float aspect , float near , float far) {
		super();
		mPerspective = new Matrix4f();
		mPerspective.perspective(fovy, aspect, near, far);
	}
	
	public Matrix4f getMatrixPerspective() {
		return mPerspective;
	}
	

	
}
