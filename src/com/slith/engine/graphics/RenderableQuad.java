package com.slith.engine.graphics;

import com.slith.engine.maths.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.slith.engine.graphics.utils.*;
import com.slith.engine.shapes.*;

// To be used with batch renderer
public class RenderableQuad {
	private static final vec2[] LocalVertices = {
			new vec2(-0.5f, 0.5f),
			new vec2(-0.5f, -0.5f),
			new vec2(0.5f, -0.5f),
			new vec2(0.5f, 0.5f)
	};
	private vec2 position;
	private vec2 size;
	private float rotationAngle;
	
	private Vertex[] finalVertices = new Vertex[4];
	private FloatBuffer verticesBuffer;
	
	private Texture texture = null;
	private int textureIndex = -1;
		
	public RenderableQuad(RectArea rect, Color color) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;
		
		// Order is counter clock-wise
		finalVertices[0] = new Vertex(LocalVertices[0], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(0.0f, 1.0f), 
				0.0f);
		finalVertices[1] = new Vertex(LocalVertices[1], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(0.0f, 0.0f), 
				0.0f);
		finalVertices[2] = new Vertex(LocalVertices[2], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(1.0f, 0.0f), 
				0.0f);
		finalVertices[3] = new Vertex(LocalVertices[3], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(1.0f, 1.0f), 
				0.0f);
		
		verticesBuffer = BufferUtils.createFloatBuffer(getTotalFloatsPerQuad());
		calculateFinalVertices();
	}
	// TODO
	public RenderableQuad(RectArea rect, Texture texture, SimpleBatchRenderer batchRenderer) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;

		this.texture = texture;
		
		// Check if the batch renderer already has the texture from other quads
		if(batchRenderer.currentTextures.containsKey(texture)) {
			textureIndex = batchRenderer.currentTextures.get(texture);
		}else {
			textureIndex = batchRenderer.numAvailableTextureIndex;
			batchRenderer.currentTextures.put(texture, textureIndex);
			
			batchRenderer.numAvailableTextureIndex++;
		}

		// Order is counter clock-wise
		finalVertices[0] = new Vertex(LocalVertices[0], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				new vec2(0.0f, 1.0f), 
				textureIndex);
		finalVertices[1] = new Vertex(LocalVertices[1], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				new vec2(0.0f, 0.0f), 
				textureIndex);
		finalVertices[2] = new Vertex(LocalVertices[2], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				new vec2(1.0f, 0.0f), 
				textureIndex);
		finalVertices[3] = new Vertex(LocalVertices[3], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				new vec2(1.0f, 1.0f), 
				textureIndex);
		
		verticesBuffer = BufferUtils.createFloatBuffer(getTotalFloatsPerQuad());
		calculateFinalVertices();
	}
	// TODO
	public RenderableQuad(RectArea rect, Texture texture, RectArea srcRect, SimpleBatchRenderer batchRenderer) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;

		this.texture = texture;
		
		// Check if the batch renderer already has the texture from other quads
		if(batchRenderer.currentTextures.containsKey(texture)) {
			textureIndex = batchRenderer.currentTextures.get(texture);
		}else {
			textureIndex = batchRenderer.numAvailableTextureIndex;
			batchRenderer.currentTextures.put(texture, textureIndex);
			
			batchRenderer.numAvailableTextureIndex++;
		}
		
		// TODO: Calculate the texture coordinates from the srcRect
		vec2[] textCoords = Texture.getTextureCoords(srcRect, texture.getWidth(), texture.getHeight());

		// Order is counter clock-wise
		finalVertices[0] = new Vertex(LocalVertices[0], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				textCoords[0], 
				textureIndex);
		finalVertices[1] = new Vertex(LocalVertices[1], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				textCoords[1], 
				textureIndex);
		finalVertices[2] = new Vertex(LocalVertices[2], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				textCoords[2], 
				textureIndex);
		finalVertices[3] = new Vertex(LocalVertices[3], 
				new vec4(1.0f, 1.0f, 1.0f, 1.0f), 
				textCoords[3], 
				textureIndex);
		
		verticesBuffer = BufferUtils.createFloatBuffer(getTotalFloatsPerQuad());
		calculateFinalVertices();
	}
	public RenderableQuad(RectArea rect, Texture texture, RectArea srcRect, SimpleBatchRenderer batchRenderer, Color color) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;

		this.texture = texture;
		
		// Check if the batch renderer already has the texture from other quads
		if(batchRenderer.currentTextures.containsKey(texture)) {
			textureIndex = batchRenderer.currentTextures.get(texture);
		}else {
			textureIndex = batchRenderer.numAvailableTextureIndex;
			batchRenderer.currentTextures.put(texture, textureIndex);
			
			batchRenderer.numAvailableTextureIndex++;
		}
		
		// TODO: Calculate the texture coordinates from the srcRect
		vec2 textureTopLeft = srcRect.getPosition();
		vec2 textureSize = srcRect.getDimension();
		float totalTextureWidth = texture.getWidth();
		float totalTextureHeight = texture.getHeight();
		
		float topLeftXNormalized = textureTopLeft.getX()/totalTextureWidth;
		float topLeftYNormalized = textureTopLeft.getY()/totalTextureHeight;
		float textureWidthNormalized = textureSize.getX()/totalTextureWidth;
		float textureHeightNormalized = textureSize.getY()/totalTextureHeight;
		
		vec2[] textCoords = new vec2[4];
		textCoords[0] = new vec2(topLeftXNormalized, (1.0f - topLeftYNormalized));
		textCoords[1] = new vec2(topLeftXNormalized, 1.0f - (topLeftYNormalized + textureHeightNormalized));
		textCoords[2] = new vec2(topLeftXNormalized + textureWidthNormalized, 1.0f - (topLeftYNormalized + textureHeightNormalized));
		textCoords[3] = new vec2(topLeftXNormalized + textureWidthNormalized, (1.0f - topLeftYNormalized));

		// Order is counter clock-wise
		finalVertices[0] = new Vertex(LocalVertices[0], 
				new vec4(color.r, color.g, color.b, color.a), 
				textCoords[0], 
				textureIndex);
		finalVertices[1] = new Vertex(LocalVertices[1], 
				new vec4(color.r, color.g, color.b, color.a), 
				textCoords[1], 
				textureIndex);
		finalVertices[2] = new Vertex(LocalVertices[2], 
				new vec4(color.r, color.g, color.b, color.a), 
				textCoords[2], 
				textureIndex);
		finalVertices[3] = new Vertex(LocalVertices[3], 
				new vec4(color.r, color.g, color.b, color.a), 
				textCoords[3], 
				textureIndex);
		
		verticesBuffer = BufferUtils.createFloatBuffer(getTotalFloatsPerQuad());
		calculateFinalVertices();
	}

	
	public void calculateFinalVertices() {
		// 1: Scale the vertices
		vec2[] finalPositions = new vec2[4];
		
		finalPositions[0] = LocalVertices[0].multiply(size);
		finalPositions[1] = LocalVertices[1].multiply(size);
		finalPositions[2] = LocalVertices[2].multiply(size);
		finalPositions[3] = LocalVertices[3].multiply(size);
		
		// 2: Rotate the vertices
		if(rotationAngle != 0.0f) {
			 
		}
		
		// 3: Local space -> World space (Origin is top left, Y-axis is inverted)
		
		// Invert the Y in the coordinate space
		vec2 invertY = new vec2(1.0f, -1.0f);
		// Translate to make (0, 0) the left-most vertex
		vec2 translation = new vec2(size.getX()/2, size.getY()/2);
		finalPositions[0] = (finalPositions[0].multiply(invertY)).add(translation);
		finalPositions[1] = (finalPositions[1].multiply(invertY)).add(translation);
		finalPositions[2] = (finalPositions[2].multiply(invertY)).add(translation);
		finalPositions[3] = (finalPositions[3].multiply(invertY)).add(translation);
		
		// Translate the vertices
		finalPositions[0] = finalPositions[0].add(position);
		finalPositions[1] = finalPositions[1].add(position);
		finalPositions[2] = finalPositions[2].add(position);
		finalPositions[3] = finalPositions[3].add(position);
		
		finalVertices[0].setPosition(finalPositions[0]);
		finalVertices[1].setPosition(finalPositions[1]);
		finalVertices[2].setPosition(finalPositions[2]);
		finalVertices[3].setPosition(finalPositions[3]);
		
		// For debugging
		//System.out.println("FINAL QUAD POSITIONS: ");
		//for(int i =0; i < finalVertices.length; i++) {
			//System.out.println(finalVertices[i].getPosition().getX() + ", " + finalVertices[i].getPosition().getY());
		//}

		putIntoLocalBuffer();
	}
	
	private void putIntoLocalBuffer() {
		verticesBuffer.clear();
		
		finalVertices[0].putIntoBuffer(verticesBuffer);
		finalVertices[1].putIntoBuffer(verticesBuffer);
		finalVertices[2].putIntoBuffer(verticesBuffer);
		finalVertices[3].putIntoBuffer(verticesBuffer);
		
		verticesBuffer.flip();
	}
	
	public void putIntoBuffer(FloatBuffer buffer) {
		buffer.put(verticesBuffer);
	}
	
	public void setPosition(vec2 position) {
		this.position = position;
	}
	public void setSize(vec2 size) {
		this.size = size;
	}
	
	public Vertex[] getQuadVertices() {
		return finalVertices;
	}
	
	public static int getQuadSize() {
		return Vertex.getVertexSize()*4;
	}
	public static int getTotalFloatsPerQuad() {
		return Vertex.getTotalFloats()*4;
	}
}
