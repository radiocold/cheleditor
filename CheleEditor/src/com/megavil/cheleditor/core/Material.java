package com.megavil.cheleditor.core;

import com.megavil.cheleditor.shader.Shader3D;

public class Material {
	
	private float alpha;
	private boolean visible;
	private boolean showWireframe;	
	
	private Shader3D shader3D;
	
	public Material() {
	}
	
	public Material(Shader3D _shader) {
		shader3D = _shader;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isShowWireframe() {
		return showWireframe;
	}

	public void setShowWireframe(boolean showWireframe) {
		this.showWireframe = showWireframe;
	}

	public Shader3D getShader3D() {
		return shader3D;
	}
	

}
