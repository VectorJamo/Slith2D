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
	
	public Texture(String texturePath) {
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
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, this.width, this.height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
	
		glBindTexture(GL_TEXTURE_2D, 0);
		STBImage.stbi_image_free(buffer);
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
	
	public void bind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	public void unbind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
