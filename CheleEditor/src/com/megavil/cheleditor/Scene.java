package com.megavil.cheleditor;

import java.util.ArrayList;
import com.megavil.cheleditor.core.CameraPerspective;
import com.megavil.cheleditor.core.Geometry;
import com.megavil.cheleditor.core.Model;
import com.megavil.cheleditor.core.Node3;
import com.megavil.cheleditor.material.Material;
import com.megavil.cheleditor.model.Mesh;
import com.megavil.cheleditor.renderer.Renderer3;
import com.megavil.cheleditor.shader.Shader;
import static org.lwjgl.stb.STBEasyFont.*;
import org.lwjgl.*;

public class Scene {
	
	protected Node3 stage;
	protected Renderer3 renderer3;
	protected Shader shader;
	protected Material material;
	
	protected CameraPerspective cameraPerspective;
	
	protected ArrayList<Model> models = new ArrayList<Model>();
	
	// Game Nodes
	private ArrayList<Node3> nodes = new ArrayList<Node3>();
	
	 /** time at last frame */
    long lastFrame;
     
    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;
 
	
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
		cameraPerspective = new CameraPerspective(57.0f * (float)Math.PI / 180.0f, 
				Application.WIDTH_WINDOW * 1.0f / Application.HEIGHT_WINDOW * 1.0f, 0.1f, 100.0f);
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
		models.add(mesh);
		
		for (int i = 0; i < 1000; i++) {
			Node3 node = new Node3();
			node.setModel(mesh);
			node.translate(-10.0f + (float)Math.random() * 25.0f , 0 , 0);
			node.scaling((float)Math.random(), (float)Math.random(), 1);
			nodes.add(node);
			stage.addChild(node);
		}
		
		lastFPS = getTime();
	}
	
	public void resize(int width , int height) {
		cameraPerspective.setAspectRatio((width * 1.0f) / (height * 1.0f));
	}
	
	   /** 
     * Calculate how many milliseconds have passed 
     * since last frame.
     * 
     * @return milliseconds passed since last frame 
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
      
        return delta;
    }
     
    /**
     * Get the accurate system time
     * 
     * @return The system time in milliseconds
     */
    public long getTime() {
        return System.nanoTime() / 1000000;
    }
     
    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            // Display.setTitle("FPS: " + fps);
            System.out.println("FPS " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
	
	public void update(float dt) {
		for (int i = 0; i < 1000; i++) {
			Node3 node = nodes.get(i);
			node.translate(-0.01f, 0 , 0);
			node.addEulerAngleZ(0.01f);
		}
		updateFPS();
	}
	
	public void render() {
		renderer3.render(cameraPerspective, stage);
	}
	
	public void clear() {
		shader.clear();
		stage.clear();
		renderer3.clear();
		for (var model : models) {
			model.clear();
		}
		models = null;
	}
	

}
