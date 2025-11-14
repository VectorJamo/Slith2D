package com.slith.engine.graphics;

import com.slith.engine.maths.*;
import com.slith.engine.shapes.*;
import com.slith.engine.graphics.utils.*;

public abstract class BasicRenderer {
	
	protected VAO vao; // Global VAO
	protected Shader defaultShader, defaultTexturedShader; // Global Shader
	
	protected mat4 scaleMatrix;
	protected mat4 rotateMatrix;
	protected mat4 translateMatrix;
	protected mat4 projectionMatrix;
	
	public BasicRenderer() {
		float[] vertices = { // Y-axis is flipped from the original opengl's NDC
				0.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				1.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 1.0f, 1.0f,
		}; // 1x1 Rectangle
		byte[] indices = {
				0, 1, 2, 2, 3, 0
		};
		
		vao = new VAO(vertices, indices, 4, 2);
		vao.defineAttribute(0, 2);
		vao.defineAttribute(1, 2);
		vao.createVAO();
		
		// Matrices
		scaleMatrix = mat4.createScale(1.0f, 1.0f);
		rotateMatrix = mat4.createRotate(0.0f);
		translateMatrix = mat4.createTranslate(0.0f, 0.0f);
		projectionMatrix = mat4.createOrthographic(0.0f, 800.0f, 0.0f, 600.0f);
		
		defaultShader = new Shader("res/shaders/default_shaders/vs.glsl", "res/shaders/default_shaders/fs.glsl");
		defaultTexturedShader = new Shader("res/shaders/default_shaders/vs_textured.glsl", "res/shaders/default_shaders/fs_textured.glsl");
		
		defaultShader.setUniformMat4f("u_Projection", projectionMatrix);
		defaultTexturedShader.setUniformMat4f("u_Projection", projectionMatrix);
		
		// Bind the global VAO
		vao.bind();
	}
	
	public abstract void renderRect(RectArea rect, Color color);
	public abstract void renderRect(Texture texture, RectArea rect);
	public abstract void renderRect(Texture texture, RectArea source, RectArea destination);
	public abstract void renderRectRotated(RectArea rect, Color color, float angleInDegrees);
	public abstract void renderRectRotated(Texture texture, RectArea destination, RectArea source, float angleInDegrees);
	
	public abstract void renderPoint(vec2 point, Color color);
	public abstract void renderLine(vec2 point1, vec2 point2, Color color);
	
	// For custom shaders
	public abstract void renderRect(RectArea rect, Color color, Shader shader);
	public abstract void renderRect(Texture texture, RectArea rect, Shader shader);
	public abstract void renderRect(Texture texture, RectArea source, RectArea destination, Shader shader);
	public abstract void renderRectRotated(RectArea rect, Color color, float angleInDegrees, Shader shader);
	public abstract void renderRectRotated(Texture texture, RectArea destination, RectArea source, float angleInDegrees, Shader shader);

}
