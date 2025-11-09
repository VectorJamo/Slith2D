package com.slith;

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

import com.slith.engine.graphics.*;
import com.slith.engine.graphics.utils.*;

public class Application {
	
	public Application() {
		Window window = new Window(800, 600, "Slith Engine");
		
		float[] vertices = {
				0.0f, 0.5f,
			   -0.5f, -0.5f,
				0.5f, -0.5f
		};
		VAO vao = new VAO(vertices, 2, 1);
		vao.defineAttribute(0, 2);
		vao.createVAO();
		
		Shader shader = new Shader("res/shaders/vs.glsl", "res/shaders/fs.glsl");
		
		vao.bind();
		shader.bind();
		
		while(!window.windowShouldClose()) {
			window.pollEvents();
			
			window.clear();
			
			// Draw
			glDrawArrays(GL_TRIANGLES, 0, 3);
			
			window.show();
		}
	}

	public static void main(String[] args) {
		new Application();
	}
}
