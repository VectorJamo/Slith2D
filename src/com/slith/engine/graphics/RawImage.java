package com.slith.engine.graphics;

import java.nio.ByteBuffer;

public class RawImage {
	public ByteBuffer imageBuffer;
	public int width, height, numChannels;
	
	public RawImage(ByteBuffer buffer, int width, int height, int numChannels) {
		imageBuffer = buffer;
		this.width = width;
		this.height = height;
		this.numChannels = numChannels;
	}
}
