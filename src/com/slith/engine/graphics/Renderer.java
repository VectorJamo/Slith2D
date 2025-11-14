package com.slith.engine.graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.slith.engine.graphics.utils.Shader;
import com.slith.engine.maths.*;
import com.slith.engine.shapes.Color;
import com.slith.engine.shapes.RectArea;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer extends BasicRenderer {

	public Renderer() {
		super();
	}
	
	@Override
	public void renderRect(RectArea rect, Color color) {
		// Set the matrices. Computation wise, these are very cheap.
		mat4.editScaleMatrix(scaleMatrix, rect.getDimension().getX(), rect.getDimension().getY());
		mat4.editTranslationMatrix(translateMatrix, rect.getPosition().getX(), rect.getPosition().getY());
		
		// Set the uniforms. These can be expensive
		defaultShader.setUniformMat4f("u_Scale", scaleMatrix);
		defaultShader.setUniformMat4f("u_Translate", translateMatrix);
		defaultShader.setUniformVec4f("u_Color", new float[] {color.r, color.g, color.b, color.a});

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
	}

	@Override
	public void renderRect(Texture texture, RectArea rect) {
		texture.bind(0);

		// Set the matrices. Computation wise, these are very cheap.
		mat4.editScaleMatrix(scaleMatrix, rect.getDimension().getX(), rect.getDimension().getY());
		mat4.editTranslationMatrix(translateMatrix, rect.getPosition().getX(), rect.getPosition().getY());
		
		// Set the uniforms. These can be expensive
		defaultTexturedShader.setUniformMat4f("u_Scale", scaleMatrix);
		defaultTexturedShader.setUniformMat4f("u_Translate", translateMatrix);
		defaultTexturedShader.setUniform1i("u_Texture", 0);
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
	}

	@Override
	public void renderRect(Texture texture, RectArea source, RectArea destination) {
		
	}

	@Override
	public void renderRectRotated(RectArea rect, Color color, float angleInDegrees) {
		
	}

	@Override
	public void renderRectRotated(Texture texture, RectArea destination, RectArea source, float angleInDegrees) {
		
	}

	@Override
	public void renderPoint(vec2 point, Color color) {
		
	}

	@Override
	public void renderLine(vec2 point1, vec2 point2, Color color) {
		
	}

	// Custom shaders
	@Override
	public void renderRect(RectArea rect, Color color, Shader shader) {
		// Set the matrices. Computation wise, these are very cheap.
		mat4.editScaleMatrix(scaleMatrix, rect.getDimension().getX(), rect.getDimension().getY());
		mat4.editTranslationMatrix(translateMatrix, rect.getPosition().getX(), rect.getPosition().getY());
		
		// Set the uniforms. These can be expensive
		shader.setUniformMat4f("u_Scale", scaleMatrix);
		shader.setUniformMat4f("u_Translate", translateMatrix);

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
	}

	@Override
	public void renderRect(Texture texture, RectArea rect, Shader shader) {
		// Set the matrices. Computation wise, these are very cheap.
		mat4.editScaleMatrix(scaleMatrix, rect.getDimension().getX(), rect.getDimension().getY());
		mat4.editTranslationMatrix(translateMatrix, rect.getPosition().getX(), rect.getPosition().getY());
		
		// Set the uniforms. These can be expensive
		shader.setUniformMat4f("u_Scale", scaleMatrix);
		shader.setUniformMat4f("u_Translate", translateMatrix);
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
	}

	@Override
	public void renderRect(Texture texture, RectArea source, RectArea destination, Shader shader) {
		
	}

	@Override
	public void renderRectRotated(RectArea rect, Color color, float angleInDegrees, Shader shader) {
		
	}

	@Override
	public void renderRectRotated(Texture texture, RectArea destination, RectArea source, float angleInDegrees,
			Shader shader) {
		
	}

	
}
