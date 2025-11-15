package com.slith.engine.graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.slith.engine.input.*;

public class Window {
	
	private long window;

	// Window properties
	private int width, height;
	private String title;
	
	// For FPS
	private double lastTime = 0.0, deltaTime = 0.0;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		if(!glfwInit()) {
			System.out.println("Failed to initialize GLFW!");
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(width, height, title, 0, 0);
		
		// Input call-backs
		glfwSetKeyCallback(window, new KeyManager());
		MouseManager.init(window);
		
		glfwMakeContextCurrent(window);
		createCapabilities();

		glfwSwapInterval(0);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void pollEvents() {
		double currentTime = glfwGetTime();
		deltaTime = currentTime - lastTime;
		lastTime = currentTime;
		
		glfwPollEvents();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void show() {
		glfwSwapBuffers(window);
		
		KeyManager.resetKeys();
		MouseManager.resetButtons();
	}
	
	public boolean windowShouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public double getDeltaTime() {
		return deltaTime;
	}
}
