package com.slith.engine.graphics.utils;

import java.nio.FloatBuffer;

import com.slith.engine.maths.*;

public class Vertex {
	private vec2 position;
	private vec4 color;
	private vec2 textCoord;
	private float textIndex;
	
	public Vertex(vec2 position, vec4 color, vec2 textCoord, float textIndex) {
		this.position = position;
		this.color = color;
		this.textCoord = textCoord;
		this.textIndex = textIndex;
	}
	
	public void putIntoBuffer(FloatBuffer buffer) {
		buffer.put(position.getX()).put(position.getY()).put(color.getX()).put(color.getY()).put(color.getZ()).put(color.getW())
		.put(textCoord.getX()).put(textCoord.getY()).put(textIndex);
	}
	
	public void setPosition(vec2 position) {
		this.position = position;
	}
	public void setTextureCoord(vec2 textCoord) {
		this.textCoord = textCoord;
	}
	
	public vec2 getPosition() {
		return position;
	}
	public vec4 getColor() {
		return color;
	}
	public vec2 getTextureCoord() {
		return textCoord;
	}
	public float getTextureIndex() {
		return textIndex;
	}
	
	public static int getTotalFloats() {
		return (2+4+2+1);
	}
	public static int getVertexSize() {
		return getTotalFloats()*Float.BYTES;
	}
	
	// Data offsets
	public static int getOffsetOfPosition() {
		return 0;
	}
	public static int getOffsetOfColor() {
		return getOffsetOfPosition() + Float.BYTES*2;
	}
	public static int getOffsetOfTextCoord() {
		return getOffsetOfColor() + Float.BYTES*4;
	}
	public static int getOffsetOfTextIndex() {
		return getOffsetOfTextCoord() + Float.BYTES*2;
	}
}
