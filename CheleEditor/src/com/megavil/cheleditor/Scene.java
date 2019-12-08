package com.megavil.cheleditor;

import org.joml.Math;

import com.megavil.cheleditor.core.CameraPerspective;
import com.megavil.cheleditor.core.Geometry;
import com.megavil.cheleditor.core.Node3D;
import com.megavil.cheleditor.material.Material3D;
import com.megavil.cheleditor.mesh.Mesh3D;
import com.megavil.cheleditor.renderer.Renderer3D;
import com.megavil.cheleditor.shader.Shader3D;

public class Scene {
	
	protected Node3D stage;
	protected Renderer3D renderer3D;
	protected Shader3D shader3D;
	protected Material3D material3D;
	
	protected CameraPerspective camera3D;
	
	
	public Scene() {
		OnConfigShadersMaterials();
		OnConfigRenderer();
		OnConfigNodes();
		create();
	}
	
	protected void OnConfigShadersMaterials() {
		shader3D = new Shader3D();
		
		material3D = new Material3D(shader3D);
		material3D.setAlpha(1);
		material3D.setShowWireframe(false);
	}
	
	
	protected void OnConfigRenderer() {
		stage = new Node3D();
		renderer3D = new Renderer3D(shader3D);
		
	}
	
	protected void OnConfigNodes() {
		camera3D = new CameraPerspective(57.0f * (float)Math.PI / 180.0f, 1024.0f/640.0f, 0.1f, 100.0f);
	}
	
	
	public void create() {
		
		Node3D nodeTriangle = new Node3D();
		
		Geometry geometry = new Geometry();
		geometry.setVertices(new float[] {0    ,  0.5f , 0,
										 -0.5f , -0.5f , 0,
										  0.5f , -0.5f , 0});
		
		geometry.setColors(new float[] {1.0f,  0.0f , 0,
									    0.0f , 1.0f , 0,
									    0.0f , 0.0f , 1.0f});
		
		Mesh3D mesh = new Mesh3D(geometry, material3D);
		
		nodeTriangle.addComponent(mesh);
		stage.addChild(nodeTriangle);
		
	}
	
	public void update(float dt) {
		
	}
	
	public void render() {
		renderer3D.render(camera3D, stage);
	}
	
	public void clear() {
		
		stage.clear();
		renderer3D.clear();
	}
	

}
