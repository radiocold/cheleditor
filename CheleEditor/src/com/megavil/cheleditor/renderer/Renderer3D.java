package com.megavil.cheleditor.renderer;

import static org.lwjgl.opengl.GL20.*;

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
		shader.use();
		
		try (MemoryStack stack = MemoryStack.stackPush()){
			
			FloatBuffer fbuffer = stack.mallocFloat(16);
			camera.getMatrixViewInv().get(fbuffer);	
			glUniformMatrix4fv(shader.getU_view(), false ,  fbuffer);
			
			camera.getMatrixPerspective().get(fbuffer);

			glUniformMatrix4fv(shader.getU_proj(), false ,  fbuffer);
			
			try (PoolMatrix4f pool = PoolMatrix4f.instance()) {
				
				Matrix4f matrix_parent = pool.push();
				renderNode3D((Node3D)node, matrix_parent, fbuffer , pool);
			}
		}
	}
	
	protected void renderNode3D(Node3D node , Matrix4f matrix_parent , FloatBuffer buffer , PoolMatrix4f mpool) {
		Matrix4f m = node.getMatrix();
		
		if (node.isDirty()) {
			m = node.getCalculateModelMatrix();
			node.setDirty(false);
		}
		
		Matrix4f mcombined = mpool.push();
		matrix_parent.get(mcombined);
		
		mcombined.mul(m);
		mcombined.get(buffer);

		glUniformMatrix4fv(shader.getU_model(), false, buffer);
		
		node.render();
		
		for (Node child : node.getChilds()) {
			Node3D child3D = (Node3D)child;
			renderNode3D( child3D , mcombined , buffer , mpool);
		}
	}
	
	public void clear() {
		
	}
}
