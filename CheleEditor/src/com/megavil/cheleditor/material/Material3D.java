package com.megavil.cheleditor.material;

import org.joml.Vector3f;

import com.megavil.cheleditor.shader.Shader3D;

public class Material3D {
	
	private float alpha;
	private boolean visible;
	private boolean showWireframe;	
	private Vector3f colorWireframe;
	
	private Shader3D shader3D;
	
	public Material3D() {
	}
	
	public Material3D(Shader3D _shader) {
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

	public Vector3f getColorWireframe() {
		return colorWireframe;
	}

	public void setColorWireframe(Vector3f colorWireframe) {
		this.colorWireframe = colorWireframe;
	}
	

}
