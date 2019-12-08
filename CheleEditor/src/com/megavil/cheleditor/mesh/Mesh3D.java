package com.megavil.cheleditor.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

import com.megavil.cheleditor.core.ComponentNode;
import com.megavil.cheleditor.core.Geometry;
import com.megavil.cheleditor.material.Material3D;

public class Mesh3D extends ComponentNode  {
	
	private int vao;
	private int vboPosition;
	private int vboColors;
	
	private Geometry geometry;
	private Material3D material;
	
	public Mesh3D(Geometry _geometry , Material3D _material) {
		geometry = _geometry;
		material = _material;
		create();
	}
	
	@Override
	public void create() {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		try (MemoryStack stack = MemoryStack.stackPush()){
			
			FloatBuffer position_buffer = stack.mallocFloat(geometry.getVertices().length);
			position_buffer.put(geometry.getVertices());
			position_buffer.flip();
			
			vboPosition = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER , vboPosition);
			glBufferData(GL_ARRAY_BUFFER , position_buffer , GL_DYNAMIC_DRAW);
			
		    glVertexAttribPointer(material.getShader3D().getIn_position(), 3, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray( material.getShader3D().getIn_position() );
			
			
			FloatBuffer color_buffer = stack.mallocFloat(geometry.getColors().length);
			color_buffer.put(geometry.getColors());
			color_buffer.flip();
			
			vboColors = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER , vboColors);
			glBufferData(GL_ARRAY_BUFFER , color_buffer , GL_DYNAMIC_DRAW);
			
		    glVertexAttribPointer(material.getShader3D().getIn_color(), 3, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray(material.getShader3D().getIn_color() );
			
		}
	}
	
	@Override 
	public void render() {
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, geometry.getVertices().length);
		if (material.isShowWireframe()) {
			glDrawArrays(GL_LINE_LOOP, 0, geometry.getVertices().length);
		}
		glBindVertexArray(0);
	}
	
	@Override
	public void clear() {
		if (vao > 0) {
			glDeleteBuffers(vboPosition);
			glDeleteBuffers(vboColors);
			glDeleteVertexArrays(vao);
			vboPosition = 0;
			vboColors = 0;
			vao = 0;
		}
	}
	

}
