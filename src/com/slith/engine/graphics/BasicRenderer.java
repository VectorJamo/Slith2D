package com.slith.engine.graphics;

import com.slith.engine.maths.*;
import com.slith.engine.shapes.*;
import com.slith.engine.graphics.utils.*;

public abstract class BasicRenderer {
	
	protected VAO vao; // Global VAO
	protected Shader shader; // Global Shader
	
	public BasicRenderer() {
		float[] vertices = {
				-0.5f,  0.5f,
				-0.5f, -0.5f,
				 0.5f, -0.5f,
				 0.5f,  0.5f
		}; // 1x1 Rectangle
		
		vao = new VAO(vertices, 2, 1);
		vao.defineAttribute(0, 2);
		vao.createVAO();
		
		shader = new Shader("res/shaders/vs.glsl", "res/shaders/fs.glsl");
	}
	
	public void SetGlobalShader(Shader shader) {
		this.shader = shader;
	}

	public abstract void RenderRect(RectArea rect, Color color);
	public abstract void RenderRect(Texture texture, RectArea destination, RectArea source);
	public abstract void RenderRectRotated(RectArea rect, Color color, float angleInDegrees);
	public abstract void RenderRectRotated(Texture texture, RectArea destination, RectArea source, float angleInDegrees);
	
	public abstract void RenderPoint(vec2 point, Color color);
	public abstract void RenderLine(vec2 point1, vec2 point2, Color color);
}
