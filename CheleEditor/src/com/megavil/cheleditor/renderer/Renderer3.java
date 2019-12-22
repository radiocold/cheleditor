package com.megavil.cheleditor.renderer;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.*;
import com.megavil.cheleditor.core.CameraPerspective;
import com.megavil.cheleditor.core.Node;
import com.megavil.cheleditor.core.Node3;
import com.megavil.cheleditor.core.PoolMatrix4f;
import com.megavil.cheleditor.shader.Shader;

public class Renderer3 {
	
	private Shader shader;
	// MemoryStack stack;
	FloatBuffer fbuffer;
	
	public Renderer3(Shader _shader) {
		shader = _shader;
		
		try (MemoryStack stack = MemoryStack.stackPush()){
			fbuffer = stack.mallocFloat(16);
		}
	}
	
	public void render(CameraPerspective camera , Node3 node) {	
		shader.use();

		try (PoolMatrix4f pool = PoolMatrix4f.instance()) {
			
			Matrix4f m = camera.getMatrix();
			if (camera.isDirty()) {
				m = camera.getCalculateModelMatrix();
				camera.setDirty(false);
			}
			
			Matrix4f m_inv = pool.push();
			m.invert(m_inv);
			m_inv.get(fbuffer);
			glUniformMatrix4fv(shader.getU_view(), false ,  fbuffer);

			camera.getMatrixPerspective().get(fbuffer);
			glUniformMatrix4fv(shader.getU_proj(), false ,  fbuffer);

			Matrix4f matrix_parent = pool.push();
			renderNode3((Node3)node, matrix_parent, fbuffer , pool);
		}
	}
	
	protected void renderNode3(Node3 node , Matrix4f matrix_parent , FloatBuffer buffer , PoolMatrix4f mpool) {
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
		
		//Remove the last matrix4f used
		mpool.pop();
		
		node.render();
		
		for (Node child : node.getChilds()) {
			Node3 child3D = (Node3)child;
			renderNode3( child3D , mcombined , buffer , mpool);
		}
	}
	
	public void clear() {
		shader = null;
	}
}
