package com.megavil.cheleditor.renderer;

import static org.lwjgl.opengl.GL20.glUniform4fv;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.megavil.cheleditor.core.CameraPerspective;
import com.megavil.cheleditor.core.Node;
import com.megavil.cheleditor.core.Node3D;
import com.megavil.cheleditor.core.PoolMatrix4f;
import com.megavil.cheleditor.shader.Shader3D;

public class Renderer3D {
	
	private Shader3D shader;
		
	public Renderer3D(Shader3D _shader) {
		shader = _shader;
	}
	
	public void render(CameraPerspective camera , Node3D node) {	
		try (MemoryStack stack = MemoryStack.stackPush()){
			
			FloatBuffer floatBuffer = stack.mallocFloat(16);
			camera.getMatrixViewInv().get(floatBuffer);	
			glUniform4fv(shader.getU_view(), floatBuffer);
			
			camera.getMatrixPerspective().get(floatBuffer);
			glUniform4fv(shader.getU_proj(), floatBuffer);
			
			try (PoolMatrix4f matrixPool = PoolMatrix4f.instance()) {
				
				Matrix4f matrixCombined = matrixPool.push();
				renderNode3D((Node3D)node, matrixCombined , floatBuffer , matrixPool);
			}
		}
	}
	
	protected void renderNode3D(Node3D node , Matrix4f matrixCombined , FloatBuffer buffer , PoolMatrix4f matrixPool) {
		Matrix4f m = node.getMatrix();
		
		if (node.isDirty()) {
			m = node.getCalculateModelMatrix();
			node.setDirty(false);
		}
		
		matrixCombined.mul(m);
		matrixCombined.get(buffer);
		glUniform4fv(shader.getU_model(), buffer);
		
		node.render();
		
		for (Node child : node.getChilds()) {
			Node3D child3D = (Node3D)child;
			renderNode3D( child3D , matrixCombined , buffer , matrixPool);
		}
	}
	
	public void clear() {
		
	}
}
