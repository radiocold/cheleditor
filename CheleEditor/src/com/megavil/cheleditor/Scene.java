package com.megavil.cheleditor;

import java.util.ArrayList;
import com.megavil.cheleditor.core.CameraPerspective;
import com.megavil.cheleditor.core.Geometry;
import com.megavil.cheleditor.core.Node3;
import com.megavil.cheleditor.material.Material;
import com.megavil.cheleditor.model.Mesh;
import com.megavil.cheleditor.renderer.Renderer3;
import com.megavil.cheleditor.shader.Shader;

public class Scene {
	
	protected Node3 stage;
	protected Renderer3 renderer3;
	protected Shader shader;
	protected Material material;
	
	protected CameraPerspective cameraPerspective;
	
	// Game Nodes
	private ArrayList<Node3> nodes = new ArrayList<Node3>();
	
	public Scene() {
		OnConfigShadersMaterials();
		OnConfigRenderer();
		OnConfigNodes();
		create();
	}
	
	protected void OnConfigShadersMaterials() {
		shader = new Shader();
		
		material = new Material(shader);
		material.setAlpha(1);
		material.setShowWireframe(false);
	}
	
	
	protected void OnConfigRenderer() {
		stage = new Node3();
		renderer3 = new Renderer3(shader);
	}
	
	protected void OnConfigNodes() {
		cameraPerspective = new CameraPerspective(57.0f * (float)Math.PI / 180.0f, 1024.0f/768.0f, 0.1f, 100.0f);
		cameraPerspective.translate(0, 0, 5.0f);
	}
	
	public void create() {
		Geometry geometry = new Geometry();
		geometry.setVertices(new float[] {0    ,  0.5f , 0,
										 -0.5f , -0.5f , 0,
										  0.5f , -0.5f , 0});
		
		geometry.setColors(new float[] {1.0f,  0.0f , 0,
									    0.0f , 1.0f , 0,
									    0.0f , 0.0f , 1.0f});
		
		Mesh mesh = new Mesh(geometry, material);
		
		for (int i = 0; i < 1000; i++) {
			Node3 node = new Node3();
			node.setModel(mesh);
			node.translate(-10.0f + (float)Math.random() * 25.0f , 0 , 0);
			node.scaling((float)Math.random(), (float)Math.random(), 1);
			nodes.add(node);
			stage.addChild(node);
		}
		
	}
	
	public void resize(int width , int height) {
		cameraPerspective.setAspectRatio((width * 1.0f) / (height * 1.0f));
	}
	
	public void update(float dt) {
		for (int i = 0; i < 1000; i++) {
			Node3 node = nodes.get(i);
			node.translate(-0.01f, 0 , 0);
			node.addEulerAngleZ(0.01f);
		}
	}
	
	public void render() {
		renderer3.render(cameraPerspective, stage);
	}
	
	public void clear() {
		shader.clear();
		stage.clear();
		renderer3.clear();
	}
	

}
