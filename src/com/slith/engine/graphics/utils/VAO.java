package com.slith.engine.graphics.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;
import java.nio.*;
import java.util.ArrayList;

public class VAO {
	
	private int vao, vbo;
	
	private int floatsPerVertex;
	private int attributesPerVertex;
	private ArrayList<Attribute> attributes;
	
	public VAO(float[] vertices, int floatsPerVertex, int attributesPerVertex) {
		this.floatsPerVertex = floatsPerVertex;
		this.attributesPerVertex = attributesPerVertex;
		
		vao = glGenVertexArrays();
		
		createVBO(vertices);
	}
	
	private void createVBO(float[] vertices) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
		buffer.put(vertices);
		buffer.flip();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void defineAttribute(int attributeIndex, int floatsPerAttribute) {
		attributes.add(new Attribute(attributeIndex, floatsPerAttribute));
	}
	
	public void createVAO() {
		// TODO. 
		// Bind the VAO and VBO
		// Set the layout
	}
	
	public void bind() {
		glBindVertexArray(vao);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	int GetVAO() {
		return vao;
	}

}
