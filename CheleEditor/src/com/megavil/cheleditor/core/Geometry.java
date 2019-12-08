package com.megavil.cheleditor.core;

public class Geometry {
	
	protected float[] vertices;
	protected float[] colors;
	protected float[] indices;
	protected float[] uvs;
	
	public Geometry(float[] _vertices , float[] _colors ,  float[] _indices , float[] _uvs) {
		vertices = _vertices;
		colors = _colors;
		indices = _indices;
		uvs = _uvs;
	}

	public boolean hasIndices() {
		return (indices.length > 0);
	}
	
	public boolean hasColors() {
		return (colors.length > 0);
	}
	
	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getColors() {
		return colors;
	}

	public void setColors(float[] colors) {
		this.colors = colors;
	}

	public float[] getIndices() {
		return indices;
	}

	public void setIndices(float[] indices) {
		this.indices = indices;
	}

	public float[] getUvs() {
		return uvs;
	}

	public void setUvs(float[] uvs) {
		this.uvs = uvs;
	}

}
