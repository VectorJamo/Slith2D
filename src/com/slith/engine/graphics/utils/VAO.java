package com.slith.engine.graphics.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;
import java.nio.*;
import java.util.ArrayList;

public class VAO {
	
	private int vao, vbo, ibo;
	
	private int floatsPerVertex;
	private int attributesPerVertex;
	private ArrayList<Attribute> attributes;
	
	public VAO(float[] vertices, byte[] indices, int floatsPerVertex, int attributesPerVertex) {
		this.floatsPerVertex = floatsPerVertex;
		this.attributesPerVertex = attributesPerVertex;
		attributes = new ArrayList<Attribute>();
		
		vao = glGenVertexArrays();
		
		createGLBuffers(vertices, indices);
	}
	
	private void createGLBuffers(float[] vertices, byte[] indices) {
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices);
		vertexBuffer.flip();

		ByteBuffer indexBuffer = BufferUtils.createByteBuffer(indices.length);
		indexBuffer.put(indices);
		indexBuffer.flip();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void defineAttribute(int attributeIndex, int floatsPerAttribute) {
		attributes.add(new Attribute(attributeIndex, floatsPerAttribute));
	}
	
	public void createVAO() {
		// Bind the VAO, VBO and IBO
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
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
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void bind() {
		glBindVertexArray(vao);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
}
