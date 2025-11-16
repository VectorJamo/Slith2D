package com.slith.engine.graphics;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;

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

import com.slith.engine.maths.*;
import com.slith.engine.graphics.utils.*;
import com.slith.engine.shapes.*;

public class SimpleBatchRenderer {

	private int vao, vbo, ibo;
	private Shader defaultShader, currentShader;
	protected mat4 projectionMatrix;
	
	private static int unitWhiteTexture;
	
	private ArrayList<RenderableQuad> quads;
	private FloatBuffer quadVertexBuffer;
	private ShortBuffer quadIndexBuffer;
	
	public static final int MAX_QUADS_PER_BATCH = 12000;
	
	// Texture to texture index map
	public HashMap<Texture, Integer> currentTextures = new HashMap<Texture, Integer>();
	public int numAvailableTextureIndex = 1;
	
	public SimpleBatchRenderer() {
		quads = new ArrayList<RenderableQuad>();
		
		quadVertexBuffer = BufferUtils.createFloatBuffer(RenderableQuad.getTotalFloatsPerQuad()*MAX_QUADS_PER_BATCH);
		quadIndexBuffer = BufferUtils.createShortBuffer(6*MAX_QUADS_PER_BATCH);
		
		// Buffers
		createOpenGLBuffers();
		
		// Shaders
		defaultShader = new Shader("res/shaders/default_shaders/batch_rendering/vs.glsl", "res/shaders/default_shaders/batch_rendering/fs.glsl");
		projectionMatrix = mat4.createOrthographic(0.0f, 800.0f, 0.0f, 600.0f);
		
		currentShader = defaultShader;
		currentShader.setUniformMat4f("u_Projection", projectionMatrix);
		currentShader.setUniformiv("u_Textures", new int[] {0, 1, 2, 3, 4, 5, 6, 7});
		
		// Textures
		unitWhiteTexture = Texture.createUnitWhiteTexture();
	}
	
	public void setShader(Shader shader) {
		currentShader = shader;

		currentShader.setUniformMat4f("u_Projection", projectionMatrix);
		currentShader.setUniformiv("u_Textures", new int[] {0, 1, 2, 3, 4, 5, 6, 7});
	}
	
	public void createOpenGLBuffers() {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, RenderableQuad.getQuadSize()*MAX_QUADS_PER_BATCH, GL_DYNAMIC_DRAW);
		
		// Set the layout
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, Vertex.getVertexSize(), Vertex.getOffsetOfPosition());		
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, Vertex.getVertexSize(), Vertex.getOffsetOfColor());		
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, Vertex.getVertexSize(), Vertex.getOffsetOfTextCoord());		
		glEnableVertexAttribArray(3);
		glVertexAttribPointer(3, 1, GL_FLOAT, false, Vertex.getVertexSize(), Vertex.getOffsetOfTextIndex());		
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, (Short.BYTES*6)*MAX_QUADS_PER_BATCH, GL_DYNAMIC_DRAW);
		
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void pushQuad(RenderableQuad quad) {
		quads.add(quad);
	}
	public void removeQuad(RenderableQuad quad) {
		quads.remove(quad);
	}
	
	public void createBatchedBuffers() {
		// Bind textures
		glActiveTexture(GL_TEXTURE0 + 0);
		glBindTexture(GL_TEXTURE_2D, unitWhiteTexture);

		createBatchedVertexBuffer();
		createBatchedIndexBuffer();
	}
	
	private void createBatchedVertexBuffer() {
		quadVertexBuffer.clear();
		
		// Loop over all the quads
		for(int i = 0; i < quads.size(); i++) {
			RenderableQuad quad = quads.get(i);			
			quad.putIntoBuffer(quadVertexBuffer);
		}
		quadVertexBuffer.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, quadVertexBuffer);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		//System.out.println("DONE FILLING UP VERTEX BUFFER");
	}
	
	private void createBatchedIndexBuffer() {
		quadIndexBuffer.clear();
		
		for(int i = 0; i < quads.size(); i++) {
			quadIndexBuffer.put((short)(0 + i*4));
			quadIndexBuffer.put((short)(1 + i*4));
			quadIndexBuffer.put((short)(2 + i*4));
			quadIndexBuffer.put((short)(2 + i*4));
			quadIndexBuffer.put((short)(3 + i*4));
			quadIndexBuffer.put((short)(0 + i*4));
		}
		quadIndexBuffer.flip();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, quadIndexBuffer);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	
		//System.out.println("DONE FILLING UP INDEX BUFFER");
	}
	
	public void bindTextures() {
		for(Texture texture: currentTextures.keySet()) {
			int textureSlot = currentTextures.get(texture);
			texture.bind(textureSlot);
		}
	}
	
	public void drawQuads() {
		createBatchedBuffers();
		
		glBindVertexArray(vao);
		currentShader.bind();
		bindTextures();
		
		glDrawElements(GL_TRIANGLES, quads.size()*6, GL_UNSIGNED_SHORT, 0);

		glBindVertexArray(0); 
	}
	
	public void clearQuads() {
		quads.clear();
	}
	
	public Shader getShaderObject() {
		return currentShader;
	}
	public Shader getDefaultShader() {
		return defaultShader;
	}
	public ArrayList<RenderableQuad> getQuadBuffer() {
		return quads;
	}
}
