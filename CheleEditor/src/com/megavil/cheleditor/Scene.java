package com.megavil.cheleditor;

import java.util.ArrayList;

import org.lwjgl.system.MathUtil;

import com.megavil.cheleditor.component.MeshRender3D;
import com.megavil.cheleditor.core.CameraPerspective;
import com.megavil.cheleditor.core.Geometry;
import com.megavil.cheleditor.core.Node3D;
import com.megavil.cheleditor.material.Material3D;
import com.megavil.cheleditor.renderer.Renderer3D;
import com.megavil.cheleditor.shader.Shader3D;

public class Scene {
	
	protected Node3D stage;
	protected Renderer3D renderer3D;
	protected Shader3D shader3D;
	protected Material3D material3D;
	
	protected CameraPerspective camera3D;
	
	// Game Nodes
	private ArrayList<Node3D> nodes = new ArrayList<Node3D>();
	
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
		camera3D = new CameraPerspective(57.0f * (float)Math.PI / 180.0f, 1024.0f/768.0f, 0.1f, 100.0f);
		camera3D.translate(0, 0, 5.0f);
	}
	
	public void create() {
		Geometry geometry = new Geometry();
		geometry.setVertices(new float[] {0    ,  0.5f , 0,
										 -0.5f , -0.5f , 0,
										  0.5f , -0.5f , 0});
		
		geometry.setColors(new float[] {1.0f,  0.0f , 0,
									    0.0f , 1.0f , 0,
									    0.0f , 0.0f , 1.0f});
		
		MeshRender3D mesh = new MeshRender3D(geometry, material3D);
		
		for (int i = 0; i < 1000; i++) {
			Node3D node = new Node3D();
			node.addComponent(mesh);
			node.translate(-10.0f + (float)Math.random() * 25.0f , 0 , 0);
			node.scaling((float)Math.random(), (float)Math.random(), 1);
			nodes.add(node);
			stage.addChild(node);
		}
		
	}
	
	public void resize(int width , int height) {
		camera3D.setAspectRatio((width * 1.0f) / (height * 1.0f));
	}
	
	public void update(float dt) {
		for (int i = 0; i < 1000; i++) {
			Node3D node = nodes.get(i);
			node.translate(-0.01f, 0 , 0);
			node.addEulerAngleZ(0.01f);
		}
	}
	
	public void render() {
		renderer3D.render(camera3D, stage);
	}
	
	public void clear() {
		shader3D.clear();
		stage.clear();
		renderer3D.clear();
	}
	

}
