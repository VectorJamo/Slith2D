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
		attributes = new ArrayList<Attribute>();
		
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
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		// Set the layout
		int offset = 0;
		for(int i = 0; i < attributes.size(); i++) {
			// Uncomment for debugging
			//System.out.println("Attribute Index: " + i);
			//System.out.println("Count: " + attributes.get(i).floatsPerAttribute);
			//System.out.println("Stride: " + floatsPerVertex * Float.BYTES);
			//System.out.println("Offset: " + offset * Float.BYTES);
			
			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, attributes.get(i).floatsPerAttribute, GL_FLOAT, false, 
					floatsPerVertex * Float.BYTES, offset * Float.BYTES);
			
			offset += attributes.get(i).floatsPerAttribute;
		}
		
		// Un-bind everything
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void bind() {
		glBindVertexArray(vao);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
}
