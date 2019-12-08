package com.megavil.cheleditor.material;

import org.joml.Vector3f;

import com.megavil.cheleditor.shader.Shader;

public class Material {
	
	private float alpha;
	private boolean visible;
	private boolean showWireframe;	
	private Vector3f colorWireframe;
	private Shader shader;
	
	public Material() {
	}
	
	public Material(Shader _shader) {
		shader = _shader;
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

	public Shader getShader() {
		return shader;
	}

	public Vector3f getColorWireframe() {
		return colorWireframe;
	}

	public void setColorWireframe(Vector3f colorWireframe) {
		this.colorWireframe = colorWireframe;
	}
	

}
