package com.megavil.cheleditor;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class Main {

	// The window handle
	private long window;
	Vector3f vec3;
	
	private CharSequence vertex_shader = "#version 330\n" +
		"in vec3 position;\n" +
		"in vec3 in_color;\n" +
		"out vec3 o_color;\n" +
		"uniform mat3 model;\n"+
		
		
		"void main () {\n" +
		"  gl_Position = vec4(model * position, 1.0);\n" +
		"  o_color = in_color;\n"+ 
		"};\n"; 
	
	private CharSequence fragment_shader = "#version 330\n" +
			"out vec4 frag_colour;\n" + 
			"in vec3 o_color;\n" + 
			"void main () {\n" +
			"  frag_colour = vec4 (o_color.x, o_color.y, o_color.z, 1.0);\n" + 
			"};";
	
	private int shader_program;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		 GLFWErrorCallback.createPrint(System.err).set();
		
		

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
	
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
		glfwWindowHint( GLFW_CONTEXT_VERSION_MAJOR, 4 );
		glfwWindowHint( GLFW_CONTEXT_VERSION_MINOR, 3 );
		
		glfwWindowHint( GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE );
		glfwWindowHint( GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE );
		
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		
		// Create the window
		window = glfwCreateWindow(1024, 768, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		
		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
			IntBuffer inr2;
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
			
	
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		
		
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();


		// GLUtil.setupDebugMessageCallback(System.out);
		
		String renderer = glGetString( GL_RENDERER ); /* get renderer string */
		String version = glGetString( GL_VERSION );	 /* version as a string */
		
		System.out.println(renderer);
		System.out.println(version);

		// Set the clear color
		glClearColor(0.0f, 1.0f, 0.0f, 0.0f);

		
		int params[] = new int[1];
		
		int vert_shader = glCreateShader( GL_VERTEX_SHADER );
		glShaderSource( vert_shader, vertex_shader);
		glCompileShader( vert_shader );
		
		glGetShaderiv( vert_shader, GL_COMPILE_STATUS, params );
		if ( GL_TRUE != params[0] ) 
		{
			System.out.println("ERROR: GL shader index " + vert_shader + " did not compile\n");
			System.out.println("INFO LOG " + glGetShaderInfoLog(vert_shader));
		
		}
		
		int frag_shader = glCreateShader( GL_FRAGMENT_SHADER );
		glShaderSource( frag_shader, fragment_shader );
		glCompileShader( frag_shader );
		
		glGetShaderiv( frag_shader, GL_COMPILE_STATUS, params );
		if ( GL_TRUE != params[0] ) 
		{
			System.out.println("ERROR: GL shader index " + frag_shader + " did not compile\n");
			System.out.println("INFO LOG " + glGetShaderInfoLog(frag_shader));
		}
		
		shader_program = glCreateProgram();
		glAttachShader( shader_program, vert_shader );
		glAttachShader( shader_program, frag_shader );
		glLinkProgram( shader_program );
		
		glDeleteShader(vert_shader);
		glDeleteShader(frag_shader);

		
		glGetProgramiv( shader_program, GL_LINK_STATUS, params );
		if ( GL_TRUE != params[0] ) {
			System.out.println( "ERROR: could not link shader programme GL index shader_program \n");
			System.out.println("INFO LOG PROGRAM " + glGetProgramInfoLog(shader_program));
		}
		
		
		int position_loc = glGetAttribLocation(shader_program, "position");
		int color_loc = glGetAttribLocation(shader_program, "in_color");
		
		
		float points[] = new float[] {
				 0.0f,  0.5f, 1.0f, 
				 0.5f, -0.5f, 1.0f,
				-0.5f, -0.5f, 1.0f

				
		};
		
		
		float colors[] = new float[] {
				1.0f, 0.0f, 0.0f, 
				0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 1.0f
				
		};
		
		//0.0f, 0.5f, 0.0f, 
		//0.5f, -0.5f, 0.0f,
		//-0.5f, -0.5f, 0.0f 
		
		int vboVertices = -1;
		int vboColors = -1;
		
		try (MemoryStack stack = MemoryStack.stackPush()){
			
			FloatBuffer point_buffer = stack.mallocFloat(3 * 3);
			// FloatBuffer point_buffer = BufferUtils.createFloatBuffer(points.length);
			point_buffer.put(points);
			point_buffer.flip();
			
			//vboVertices = glGenBuffers();
			 vboVertices = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER , vboVertices);
			glBufferData(GL_ARRAY_BUFFER , point_buffer , GL_DYNAMIC_DRAW);
			
		    glVertexAttribPointer(position_loc, 3, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray( position_loc );
			
			
			FloatBuffer color_buffer = stack.mallocFloat(3 * 3);
			// FloatBuffer point_buffer = BufferUtils.createFloatBuffer(points.length);
			color_buffer.put(colors);
			color_buffer.flip();
			
			//vboColors = glGenBuffers();
			 vboColors = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER , vboColors);
			glBufferData(GL_ARRAY_BUFFER , color_buffer , GL_DYNAMIC_DRAW);
			
		    glVertexAttribPointer(color_loc, 3, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray( color_loc );
			
		}
		
		
		
		Matrix4f m4;
		
		
////		 Classic Way		
//				FloatBuffer point_buffer = BufferUtils.createFloatBuffer(points.length);
//				point_buffer.put(points);
//				point_buffer.flip();
//				
//				int buffer = glGenBuffers();
//			
//		
//				glBindBuffer(GL_ARRAY_BUFFER , buffer);
//				glBufferData(GL_ARRAY_BUFFER , points , GL_DYNAMIC_DRAW);
		

		

		
		
		//glEnable( GL_CULL_FACE ); // cull face
		
		//glCullFace( GL_BACK );		// cull back face
		//glFrontFace( GL_CW );			// GL_CCW for counter clock-wise
		//glEnable(GL_DEPTH_TEST);
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		
		float posX = 0;
		
		while ( !glfwWindowShouldClose(window) )
		{
			glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			glViewport( 0, 0, 1024, 768 );
			
			int state = glfwGetKey(window, GLFW_KEY_E);
			if (state == GLFW_PRESS) {
				System.out.println("Press E");
			}
			
			glUseProgram(shader_program);
			
			glBindBuffer(GL_ARRAY_BUFFER ,  vboVertices);
		    glVertexAttribPointer(position_loc, 3, GL_FLOAT, false, 0, 0);
		    glEnableVertexAttribArray( position_loc );
		    
		    

		    
			glBindBuffer(GL_ARRAY_BUFFER , vboColors);			
		    glVertexAttribPointer(color_loc, 3, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray( color_loc );
			
			
			
			
		    int u_model = glGetUniformLocation(shader_program, "model");
		    System.out.println(u_model);
		    posX += 0.001f;
		    FloatBuffer f_buffer = BufferUtils.createFloatBuffer(9);	
		    Matrix3f m3 = new Matrix3f(f_buffer);
		    m3.identity();
		    m3.setColumn(2, posX, 0, 1);
//		    System.out.println(m3.toString());
		    
		      
		    f_buffer = m3.get(f_buffer);
		    glUniformMatrix3fv(u_model , false , f_buffer);
		    
		    
		    
		    
			/* draw points 0-3 from the currently bound VAO with current in-use shader */
			 glDrawArrays(GL_LINE_LOOP, 0, 3);
		    // glDrawElements(GL_TRIANGLES , 6 , GL_UNSIGNED_SHORT , 0);
		     
			glfwSwapBuffers(window); // swap the color buffers


			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

}