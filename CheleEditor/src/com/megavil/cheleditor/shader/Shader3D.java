package com.megavil.cheleditor.shader;

import static org.lwjgl.opengl.GL20.*;

import com.megavil.cheleditor.core.ShaderProgram;
/**
 * Creara el programa en OpenGL con un shader basico para obtener las matrices y ubicaciones de variables principales
 * @author Martin
 *
 */
public class Shader3D {
	
	protected ShaderProgram program;
	
	protected int in_position;
	protected int in_color;
	
	protected int u_view;
	protected int u_model;
	protected int u_proj;
	
	
	private CharSequence v_shader = "#version 330\n" +
			"in vec3 in_position;\n" +
			"in vec3 in_color;\n" +
			"out vec3 o_color;\n" +
			
			"uniform mat3 u_model;\n"+
			"uniform mat3 u_view;\n"+
			"uniform mat3 u_proj;\n"+
		
			"void main () {\n" +
			"  gl_Position = u_proj * u_view * u_model *  vec4(position, 1.0);\n" +
			"  o_color = in_color;\n"+ 
			"};\n"; 
		
	private CharSequence f_shader = "#version 330\n" +
				"out vec4 frag_colour;\n" + 
				"in vec3 o_color;\n" + 
				"void main () {\n" +
				"  frag_colour = vec4 (o_color.x, o_color.y, o_color.z, 1.0);\n" + 
				"};";
	
	public Shader3D() {
		
		program = new ShaderProgram();
		program.create(v_shader.toString(), f_shader.toString());
		program.use();
		
		// Read Parameters
		in_position = glGetAttribLocation(program.getProgramID(), "in_position");
		in_color = glGetAttribLocation(program.getProgramID(), "in_color");
		
		u_view = glGetUniformLocation(program.getProgramID() , "u_view");
		u_model = glGetUniformLocation(program.getProgramID() , "u_model");
		u_proj = glGetUniformLocation(program.getProgramID() , "u_proj");
	}
	
	public void use() {
		program.use();
	}

	public ShaderProgram getProgram() {
		return program;
	}

	public int getIn_position() {
		return in_position;
	}

	public int getIn_color() {
		return in_color;
	}

	public int getU_view() {
		return u_view;
	}

	public int getU_model() {
		return u_model;
	}

	public int getU_proj() {
		return u_proj;
	}

}
