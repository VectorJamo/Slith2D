package com.slith.engine.graphics.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.*;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.slith.engine.maths.*;

public class Shader {
	
	private String vertexShaderCode, fragmentShaderCode;
	private int shaderProgram;
	
	public Shader(String vsPath, String fsPath) {
		vertexShaderCode = loadShader(vsPath);
		fragmentShaderCode = loadShader(fsPath);
		
		int vs = compileShader(GL_VERTEX_SHADER, vertexShaderCode);
		int fs = compileShader(GL_FRAGMENT_SHADER, fragmentShaderCode);
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vs);
		glAttachShader(shaderProgram, fs);
		glLinkProgram(shaderProgram);
		
		// Error handling
		IntBuffer status = BufferUtils.createIntBuffer(1);
		glGetProgramiv(shaderProgram, GL_LINK_STATUS, status);
		if(status.get(0) == GL_FALSE) {
			System.out.println("| SHADER LINKING FAILED! |");
		}
		
		glDeleteShader(vs);
		glDeleteShader(fs);
	}
	
	private String loadShader(String shaderPath) {
		String shaderCode = "";
		
		File file = new File(shaderPath);
		try{
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String line = (reader.nextLine() + '\n');
				shaderCode += line;
			}
		}catch(FileNotFoundException e) {
			System.out.println("Error reading file: " + shaderPath);
			e.printStackTrace();
		}
		
		return shaderCode;
	}
	
	private int compileShader(int shaderType, String shaderCode) {
		int shader = glCreateShader(shaderType);
		glShaderSource(shader, (CharSequence)shaderCode);
		glCompileShader(shader);
		
		// Error handling
		IntBuffer status = BufferUtils.createIntBuffer(1);
		glGetShaderiv(shader, GL_COMPILE_STATUS, status);
		
		if(status.get(0) == GL_FALSE) {
			switch(shaderType) {
			case GL_VERTEX_SHADER:
				System.out.println("| VERTEX SHADER COMPILATION ERROR |");
				break;
			case GL_FRAGMENT_SHADER:
				System.out.println("| FRAGMENT SHADER COMPILATION ERROR |");
				break;
			}
			String error = glGetShaderInfoLog(shader);
			System.out.println(error);
		}
		
		return shader;
	}
	private int getUniformLocation(String uniformName) {
		int uniformLocation = glGetUniformLocation(shaderProgram, (CharSequence)uniformName);
		if(uniformLocation == -1) {
			System.out.println("Failed to find uniform: " + uniformName);
		}
		return uniformLocation;
	}
	public void setUniformVec4f(String uniformName, float[] vec4) {
		bind();
		glUniform4fv(getUniformLocation(uniformName), vec4);
	}
	public void setUniformVec2f(String uniformName, float[] vec2) {
		bind();
		glUniform2fv(getUniformLocation(uniformName), vec2);
	}
	public void setUniformMat4f(String uniformName, mat4 mat) {
		bind();
		glUniformMatrix4fv(getUniformLocation(uniformName), true, mat.values);
	}
	public void setUniform1i(String uniformName, int value) {
		bind();
		glUniform1i(getUniformLocation(uniformName), value);
	}
	public void setUniformiv(String uniformName, int[] values) {
		bind();
		glUniform1iv(getUniformLocation(uniformName), values);
	}
	
	public void bind() {
		glUseProgram(shaderProgram);
	}
	public void unbind() {
		glUseProgram(0);
	}
}
