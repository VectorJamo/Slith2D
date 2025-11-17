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

	private vec2 invertY = new vec2(1.0f, -1.0f);
	private vec2 translation =  new vec2(0.0f, 0.0f);
	private vec2[] finalPositions = new vec2[4];
	
	private Vertex[] finalVertices = new Vertex[4];
	private FloatBuffer verticesBuffer;
	
	private Texture texture = null;
	private int textureIndex = 0;
		
	public RenderableQuad(RectArea rect, Color color) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;
		
		// Order is counter clock-wise
		finalVertices[0] = new Vertex(LocalVertices[0], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(0.0f, 1.0f), 
				textureIndex);
		finalVertices[1] = new Vertex(LocalVertices[1], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(0.0f, 0.0f), 
				textureIndex);
		finalVertices[2] = new Vertex(LocalVertices[2], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(1.0f, 0.0f), 
				textureIndex);
		finalVertices[3] = new Vertex(LocalVertices[3], 
				new vec4(color.r, color.g, color.b, color.a), 
				new vec2(1.0f, 1.0f), 
				textureIndex);
		
		finalPositions[0] = new vec2(0.0f, 0.0f);
		finalPositions[1] = new vec2(0.0f, 0.0f);
		finalPositions[2] = new vec2(0.0f, 0.0f);
		finalPositions[3] = new vec2(0.0f, 0.0f);

		
		verticesBuffer = BufferUtils.createFloatBuffer(getTotalFloatsPerQuad());
		calculateFinalVertices();
	}

	public RenderableQuad(RectArea rect, Texture texture, SimpleBatchRenderer batchRenderer) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;

		this.texture = texture;
		
		finalPositions[0] = new vec2(0.0f, 0.0f);
		finalPositions[1] = new vec2(0.0f, 0.0f);
		finalPositions[2] = new vec2(0.0f, 0.0f);
		finalPositions[3] = new vec2(0.0f, 0.0f);

		
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
	public RenderableQuad(RectArea rect, Texture texture, RectArea srcRect, SimpleBatchRenderer batchRenderer) {
		position = rect.getPosition();
		size = rect.getDimension();
		rotationAngle = 0.0f;

		this.texture = texture;
		
		finalPositions[0] = new vec2(0.0f, 0.0f);
		finalPositions[1] = new vec2(0.0f, 0.0f);
		finalPositions[2] = new vec2(0.0f, 0.0f);
		finalPositions[3] = new vec2(0.0f, 0.0f);
		
		// Check if the batch renderer already has the texture from other quads
		if(batchRenderer.currentTextures.containsKey(texture)) {
			textureIndex = batchRenderer.currentTextures.get(texture);
		}else{
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
		
		finalPositions[0] = new vec2(0.0f, 0.0f);
		finalPositions[1] = new vec2(0.0f, 0.0f);
		finalPositions[2] = new vec2(0.0f, 0.0f);
		finalPositions[3] = new vec2(0.0f, 0.0f);

		
		// Check if the batch renderer already has the texture from other quads
		if(batchRenderer.currentTextures.containsKey(texture)) {
			textureIndex = batchRenderer.currentTextures.get(texture);
		}else {
			textureIndex = batchRenderer.numAvailableTextureIndex;
			batchRenderer.currentTextures.put(texture, textureIndex);
			
			batchRenderer.numAvailableTextureIndex++;
			System.out.println(textureIndex);
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
	
	public void setTextureRect(RectArea srcRect) {
		vec2[] textCoords = Texture.getTextureCoords(srcRect, texture.getWidth(), texture.getHeight());

		finalVertices[0].setTextureCoord(textCoords[0]);
		finalVertices[1].setTextureCoord(textCoords[1]);
		finalVertices[2].setTextureCoord(textCoords[2]);
		finalVertices[3].setTextureCoord(textCoords[3]);
		
		putIntoLocalBuffer();
	}
	
	public void calculateFinalVertices() {
		// 1: Scale the vertices
		vec2.multiply(finalPositions[0], LocalVertices[0], size);
		vec2.multiply(finalPositions[1], LocalVertices[1], size);
		vec2.multiply(finalPositions[2], LocalVertices[2], size);
		vec2.multiply(finalPositions[3], LocalVertices[3], size);
		
		// 2: Rotate the vertices
		if(rotationAngle != 0.0f) {
			 
		}
		
		// 3: Local space -> World space (Origin is top left, Y-axis is inverted)
		translation.setX(size.getX()/2);
		translation.setY(size.getY()/2);

		// Invert the Y in the coordinate space
		// Translate to make (0, 0) the left-most vertex
		vec2.multiply(finalPositions[0], finalPositions[0], invertY);
		vec2.multiply(finalPositions[1], finalPositions[1], invertY);
		vec2.multiply(finalPositions[2], finalPositions[2], invertY);
		vec2.multiply(finalPositions[3], finalPositions[3], invertY);
		
		vec2.add(finalPositions[0], finalPositions[0], translation);
		vec2.add(finalPositions[1], finalPositions[1], translation);
		vec2.add(finalPositions[2], finalPositions[2], translation);
		vec2.add(finalPositions[3], finalPositions[3], translation);
		
		// Translate the vertices
		vec2.add(finalPositions[0], finalPositions[0], position);
		vec2.add(finalPositions[1], finalPositions[1], position);
		vec2.add(finalPositions[2], finalPositions[2], position);
		vec2.add(finalPositions[3], finalPositions[3], position);
		
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
		
		//buffer.put(verticesBuffer); -> NEVER DO THIS. WASTED a WHOLE DAY FIXING BUFFER ISSUES BECAUSE OF THIS SHIT!
		// IF YOU WISH TO COPY A BUFFER INTO ANOTHER, COPY ITS ELEMENTS MANUALLY!
	    for (int i = 0; i < verticesBuffer.limit(); i++) {
	        buffer.put(verticesBuffer.get(i));
	    }
	}
	
	public void setPosition(vec2 position) {
		this.position = position;
	}
	public void setSize(vec2 size) {
		this.size = size;
	}
	
	public vec2 getPositions() {
		return position;
	}
	
	public Vertex[] getQuadVertices() {
		return finalVertices;
	}
	public FloatBuffer getVerticesBuffer() {
		return verticesBuffer;
	}
	public static int getQuadSize() {
		return Vertex.getVertexSize()*4;
	}
	public static int getTotalFloatsPerQuad() {
		return Vertex.getTotalFloats()*4;
	}
}
