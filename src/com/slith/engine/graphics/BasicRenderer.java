package com.slith.engine.graphics;

import com.slith.engine.maths.*;
import com.slith.engine.shapes.*;
import com.slith.engine.graphics.utils.*;

public abstract class BasicRenderer {
	
	protected VAO vao; // Global VAO
	protected Shader defaultShader; // Global Shader
	
	protected mat4 scaleMatrix;
	protected mat4 rotateMatrix;
	protected mat4 translateMatrix;
	protected mat4 projectionMatrix;
	
	protected int unitWhiteTexture;
	
	public BasicRenderer() {
		float[] vertices = {
			-0.5f,  0.5f, 0.0f, 1.0f, 
			-0.5f, -0.5f, 0.0f, 0.0f,
	     	 0.5f, -0.5f, 1.0f, 0.0f,
			 0.5f,  0.5f, 1.0f, 1.0f,
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
		defaultShader.setUniformMat4f("u_Projection", projectionMatrix);
		defaultShader.setUniform1i("u_TextureUnit", 0);
		
		unitWhiteTexture = Texture.createUnitWhiteTexture();
	}
	
	public abstract void renderRect(RectArea rect, Color color, float rotationAngle);
	public abstract void renderRect(Texture texture, RectArea rect, float rotationAngle);
	public abstract void renderRect(Texture texture, RectArea source, RectArea destination, float rotationAngle);
	
	public abstract void renderPoint(vec2 point, Color color);
	public abstract void renderLine(vec2 point1, vec2 point2, Color color);
}
