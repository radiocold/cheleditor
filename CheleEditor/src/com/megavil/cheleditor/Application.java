package com.megavil.cheleditor;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class Application {

	// The window handle
	private long window;
	
	private Scene scene;
	
	private int wWidth;
	private int wHeight;
	public static final int WIDTH_WINDOW  = 1024;
	public static final int HEIGHT_WINDOW = 768;
	
	GLFWVidMode vidmode;
	private long monitor;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		
		wWidth  = WIDTH_WINDOW;
		wHeight = HEIGHT_WINDOW;
		
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
		
		glfwWindowHint(GLFW_DECORATED, 1);
		
		// Get the resolution of the primary monitor
		monitor = glfwGetPrimaryMonitor();
		vidmode = glfwGetVideoMode(monitor);
		
		// Create the window
		window = glfwCreateWindow(WIDTH_WINDOW, HEIGHT_WINDOW, "Cheleditor", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");


		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			
			if ( key == GLFW_KEY_F1 && action == GLFW_RELEASE )
				OnFullScreen();
			
			if (key == GLFW_KEY_F2 && action == GLFW_RELEASE)
				OnRestoreWindow();
		});
		
		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

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
		scene = new Scene();
		
		
		var  windowSizeCallback = new GLFWWindowSizeCallback() {
		    @Override
		    public void invoke(long argWindow, int argWidth, int argHeight) {
		    	wWidth  = argWidth;
		    	wHeight = argHeight;
		    	scene.resize(argWidth, argHeight);
		    }
		};
			  
		glfwSetWindowSizeCallback(window, windowSizeCallback);
		glViewport( 0, 0, wWidth, wHeight );

		while ( !glfwWindowShouldClose(window) )
		{
		 	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			scene.update(0.016f);
			scene.render();
			
			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
		
		scene.clear();
	}
	
	private void OnFullScreen() {
		glfwWindowHint(GLFW_DECORATED, 0);
		wWidth = vidmode.width();
		wHeight = vidmode.height();
		
		glfwSetWindowSize(window, wWidth, wHeight);
		glfwSetWindowPos(window, 0 , 0);
		glfwSetWindowMonitor(window, monitor, 0, 0, wWidth, wHeight, vidmode.refreshRate());
		glfwSwapInterval(1);
		glViewport( 0, 0, wWidth, wHeight );
	}
	
	private void OnRestoreWindow() {
		glfwWindowHint(GLFW_DECORATED, 1);
		wWidth = WIDTH_WINDOW; 
		wHeight = HEIGHT_WINDOW;
		glfwSetWindowMonitor(window, 0,
				(vidmode.width()  - wWidth) / 2, 
				(vidmode.height() - wHeight) / 2, 
			wWidth, wHeight, vidmode.refreshRate());
		glViewport( 0, 0, wWidth, wHeight );
	}
	

	public static void main(String[] args) {
		new Application().run();
	}

}