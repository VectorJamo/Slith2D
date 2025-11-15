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
	public void renderRect(RectArea rect, Color color, float rotationAngle) {
		// Bind the global VAO
		vao.bind();

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, unitWhiteTexture);
		
		// Set the matrices. Computation wise, these are very cheap.
		mat4.editScaleMatrix(scaleMatrix, rect.getDimension().getX(), rect.getDimension().getY());
		mat4.editTranslationMatrix(translateMatrix, rect.getPosition().getX(), rect.getPosition().getY());
		mat4.editRotationMatrix(rotateMatrix, rotationAngle);
		
		// Set the uniforms. These can be expensive
		defaultShader.setUniformMat4f("u_Scale", scaleMatrix);
		defaultShader.setUniformMat4f("u_Translate", translateMatrix);
		defaultShader.setUniformMat4f("u_Rotate", rotateMatrix);
		defaultShader.setUniformVec4f("u_Color", new float[] {color.r, color.g, color.b, color.a});

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		
		vao.unbind();
	}

	@Override
	public void renderRect(Texture texture, RectArea rect, float rotationAngle) {
		vao.bind();
		texture.bind(0);

		// Set the matrices. Computation wise, these are very cheap.
		mat4.editScaleMatrix(scaleMatrix, rect.getDimension().getX(), rect.getDimension().getY());
		mat4.editTranslationMatrix(translateMatrix, rect.getPosition().getX(), rect.getPosition().getY());
		mat4.editRotationMatrix(rotateMatrix, rotationAngle);
		
		// Set the uniforms. These can be expensive
		defaultShader.setUniformMat4f("u_Scale", scaleMatrix);
		defaultShader.setUniformMat4f("u_Translate", translateMatrix);
		defaultShader.setUniformMat4f("u_Rotate", rotateMatrix);
		defaultShader.setUniformVec4f("u_Color", new float[] {1.0f, 1.0f, 1.0f, 1.0f});

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		vao.unbind();
	}
	
	@Override
	public void renderRect(Texture texture, RectArea source, RectArea destination, float rotationAngle) {
		
	}



	@Override
	public void renderPoint(vec2 point, Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderLine(vec2 point1, vec2 point2, Color color) {
		// TODO Auto-generated method stub
		
	}


	
}
