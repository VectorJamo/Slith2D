package com.slith;

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

import com.slith.engine.graphics.*;
import com.slith.engine.graphics.utils.*;

import com.slith.engine.maths.*;
import com.slith.engine.shapes.*;

import com.slith.engine.input.*;

public class Application {
	
	public Application() {
		Window window = new Window(800, 600, "Slith Engine");
		
		SimpleBatchRenderer batchRenderer = new SimpleBatchRenderer();
		Shader batchRendererShader = batchRenderer.getShaderObject();
		
		// 10K QUADS!
		int quadWidth = 800/100;
		int quadHeight = 600/100;
		for(int i = 0; i< 100; i++) {
			for(int j = 0; j < 100; j++) {
				float r = (float)Math.random();
				float g = (float)Math.random();
				float b = (float)Math.random();
				
				batchRenderer.pushQuad(new RenderableQuad(new RectArea(new vec2(j*quadWidth, i*quadHeight), new vec2(quadWidth, quadHeight)), 
						new Color(r, g, b, 1.0f)));
			}
		}
		
		Texture texture = new Texture("res/images/wall.jpg");
		Texture texture2 = new Texture("res/images/minecraft.jpg");
		
		RenderableQuad texturedQuad = new RenderableQuad(new RectArea(new vec2(0.0f, 0.0f), new vec2(100.0f, 100.0f)), texture, batchRenderer);
		batchRenderer.pushQuad(texturedQuad);
		
		RenderableQuad texturedQuad2 = new RenderableQuad(new RectArea(new vec2(100.0f, 200.0f), new vec2(100.0f, 100.0f)), texture2, batchRenderer);
		batchRenderer.pushQuad(texturedQuad2);
		
		while(!window.windowShouldClose()) {
			window.pollEvents();
			
			window.clear();
			
			batchRendererShader.setUniformVec2f("u_LightPosition", new float[] {MouseManager.getMouseX(), MouseManager.getMouseY()});
			batchRenderer.createBatchedBuffers();
			batchRenderer.drawQuads();
			
			window.show();
		}
	}

	public static void main(String[] args) {
		new Application();
	}
}
