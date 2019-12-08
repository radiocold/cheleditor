package com.megavil.cheleditor.core;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Node3D extends Node {

	protected Vector3f position;
	protected Vector3f scale;
	
	protected float eulerAngleX;
	protected float eulerAngleY;
	protected float eulerAngleZ;

	protected Quaternionf quaternion;
	
	protected Matrix4f matrix;
	
	protected boolean dirty = false;
	
	public Node3D() {
		position = new Vector3f();
		scale = new Vector3f(1 , 1, 1);
		matrix = new Matrix4f();
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		dirty = true;
	}
	
	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
		dirty = true;
	}
	
	public void move(float vx , float vy , float vz) {
		this.position.add(vx, vy, vz);
		dirty = true;
	}
		
	public float getEulerAngleX() {
		return eulerAngleX;
	}

	public void setEulerAngleX(float eulerAngleX) {
		this.eulerAngleX = eulerAngleX;
		updateQuaternionEuler();
	}

	public float getEulerAngleY() {
		return eulerAngleY;
	}

	public void setEulerAngleY(float eulerAngleY) {
		this.eulerAngleY = eulerAngleY;
		updateQuaternionEuler();
	}

	public float getEulerAngleZ() {
		return eulerAngleZ;
	}

	public void setEulerAngleZ(float eulerAngleZ) {
		this.eulerAngleZ = eulerAngleZ;
		updateQuaternionEuler();
	}
	
	private void updateQuaternionEuler() {
		quaternion.rotateXYZ(eulerAngleX, eulerAngleY, eulerAngleZ);
		dirty = true;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean value) {
		this.dirty = value;
	}

	public Quaternionf getQuaternion() {
		return quaternion;
	}

	public void setQuaternion(Quaternionf quaternion) {
		this.quaternion = quaternion;
	}
	
	public Matrix4f getCalculateModelMatrix() {
		matrix.translate(getPosition());
		matrix.rotate(getQuaternion());
		matrix.scale(getScale());
		return matrix;
	}
	
	public Matrix4f getMatrix() {
		return matrix;
	}
	
	 @Override
	public void render() {
		for (ComponentNode component : components) {
			component.render();
		}
	}
}
