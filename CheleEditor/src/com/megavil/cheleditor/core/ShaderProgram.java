package com.megavil.cheleditor.core;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	
	private int m_programID = -1;
	
	public int getProgramID() {
		return m_programID;
	}
	
	public ShaderProgram() {
	}
	
	public ShaderProgram(String v_shader , String f_shader) {
		create(v_shader , f_shader);
	}
	
	public void create(String v_shader , String f_shader) {
		
		int params[] = new int[1];
		int vert_shader = glCreateShader( GL_VERTEX_SHADER );
		
		glShaderSource( vert_shader, v_shader);
		glCompileShader( vert_shader );
		
		glGetShaderiv( vert_shader, GL_COMPILE_STATUS, params );
		if (GL_TRUE != params[0]) {
			System.out.println("ERROR: GL shader index " + vert_shader + " did not compile\n");
			System.out.println("INFO LOG " + glGetShaderInfoLog(vert_shader));
		}
		
		int frag_shader = glCreateShader( GL_FRAGMENT_SHADER );
		glShaderSource( frag_shader, f_shader );
		glCompileShader( frag_shader );
		
		glGetShaderiv( frag_shader, GL_COMPILE_STATUS, params );
		if (GL_TRUE != params[0]) {
			System.out.println("ERROR: GL shader index " + frag_shader + " did not compile\n");
			System.out.println("INFO LOG " + glGetShaderInfoLog(frag_shader));
		}
		
		m_programID = glCreateProgram();
		glAttachShader( m_programID, vert_shader );
		glAttachShader( m_programID, frag_shader );
		glLinkProgram( m_programID );
		
		glDeleteShader(vert_shader);
		glDeleteShader(frag_shader);

		glGetProgramiv( m_programID, GL_LINK_STATUS, params );
		if (GL_TRUE != params[0]) {
			System.out.println( "ERROR: could not link shader programme GL index shader_program \n");
			System.out.println("INFO LOG PROGRAM " + glGetProgramInfoLog(m_programID));
		}
	}
	
	public void use() {
		glUseProgram(m_programID);
	}
	
	public void clear() {
		glDeleteProgram(m_programID);
		m_programID = -1;
	}

}
