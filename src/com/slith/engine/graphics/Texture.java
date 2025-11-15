package com.slith.engine.graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.stb.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.slith.engine.maths.*;
import com.slith.engine.shapes.Color;
import com.slith.engine.shapes.RectArea;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.*;

public class Texture {
	
	private int texture;
	int width, height, nChannels;
	
	public Texture(String texturePath, int channels) {
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		// Load the texture
		STBImage.stbi_set_flip_vertically_on_load(true); // To adhere with OpenGL's texture coordinate standards

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer nChannels = BufferUtils.createIntBuffer(1);
		
		ByteBuffer buffer = STBImage.stbi_load(texturePath, width, height, nChannels, 0);
		this.width = width.get();
		this.height = height.get();
		this.nChannels = nChannels.get();
		
		int type = GL_RGB;
		if(channels == 4) {
			type = GL_RGBA;
		}
		glTexImage2D(GL_TEXTURE_2D, 0, type, this.width, this.height, 0, type, GL_UNSIGNED_BYTE, buffer);
	
		glBindTexture(GL_TEXTURE_2D, 0);
		STBImage.stbi_image_free(buffer);
	}
	
	public Texture(RawImage rawImage) {
		this.width = rawImage.width;
		this.height = rawImage.height;
		this.nChannels = rawImage.numChannels;
		
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		int type = GL_RGB;
		if(rawImage.numChannels == 4) {
			type = GL_RGBA;
		}
		glTexImage2D(GL_TEXTURE_2D, 0, type, rawImage.width, rawImage.height, 0, type, GL_UNSIGNED_BYTE, rawImage.imageBuffer);
		
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static RawImage bmpToPng(RawImage image) {
		ByteBuffer pngBuffer = BufferUtils.createByteBuffer(image.width*image.height*4);
		
		for(int i = 0; i < image.width*image.height; i++) {
			int r = image.imageBuffer.get(i*3) & 0xff;
			int g = image.imageBuffer.get(i*3 + 1) & 0xff;
			int b = image.imageBuffer.get(i*3 + 2) & 0xff;
			
			pngBuffer.put(i*4, (byte)r);
			pngBuffer.put(i*4+1, (byte)g);
			pngBuffer.put(i*4+2, (byte)b);
			
			int a = r | g| b;
			
			if(r == 0 && g == 0 && b == 0)
				pngBuffer.put(i*4+3, (byte)0); 
			else
				pngBuffer.put(i*4+3, (byte)255); 
		}
		
		return new RawImage(pngBuffer, image.width, image.height, 4);
	}
	public static RawImage getImageBuffer(String path) {
		STBImage.stbi_set_flip_vertically_on_load(true); // To adhere with OpenGL's texture coordinate standards

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer nChannels = BufferUtils.createIntBuffer(1);
		
		ByteBuffer buffer = STBImage.stbi_load(path, width, height, nChannels, 0);
		return new RawImage(buffer, width.get(), height.get(), nChannels.get());
	}
	
	public static int createUnitWhiteTexture() {
		int unitTexture = 0;

		unitTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, unitTexture);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		ByteBuffer buffer = BufferUtils.createByteBuffer(4);
		buffer.put((byte)255);
		buffer.put((byte)255);
		buffer.put((byte)255);
		buffer.put((byte)255);
		buffer.flip();
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return unitTexture;
	}
	
	public static vec2[] getTextureCoords(RectArea area, int textureWidth, int textureHeight) {
		vec2 textureTopLeft = area.getPosition();
		vec2 textureSize = area.getDimension();
		
		float topLeftXNormalized = textureTopLeft.getX()/textureWidth;
		float topLeftYNormalized = textureTopLeft.getY()/textureHeight;
		float textureWidthNormalized = textureSize.getX()/textureWidth;
		float textureHeightNormalized = textureSize.getY()/textureHeight;
		
		vec2[] textCoords = new vec2[4];

		textCoords[0] = new vec2(topLeftXNormalized, (1.0f - topLeftYNormalized));
		textCoords[1] = new vec2(topLeftXNormalized, 1.0f - (topLeftYNormalized + textureHeightNormalized));
		textCoords[2] = new vec2(topLeftXNormalized + textureWidthNormalized, 1.0f - (topLeftYNormalized + textureHeightNormalized));
		textCoords[3] = new vec2(topLeftXNormalized + textureWidthNormalized, (1.0f - topLeftYNormalized));

		return textCoords;
	}
	
	public void bind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	public void unbind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
