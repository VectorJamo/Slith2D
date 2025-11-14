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

import com.slith.engine.ui.*;

public class Application {
	
	public Application() {
		Window window = new Window(800, 600, "Slith Engine");
		
		SimpleBatchRenderer batchRenderer = new SimpleBatchRenderer();
		Shader batchRendererShader = batchRenderer.getShaderObject();
		
		BMPFontRenderer fontRenderer = new BMPFontRenderer("res/images/ConsolasBMP.bmp");
		
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
		
		Texture texture = new Texture("res/images/wall.jpg", 3);
		
		RenderableQuad texturedQuad = new RenderableQuad(new RectArea(new vec2(0.0f, 0.0f), new vec2(100.0f, 100.0f)), texture, batchRenderer);
		batchRenderer.pushQuad(texturedQuad);
		
		Text text = new Text("press G to play", new vec2(200, 200), 32, fontRenderer);
		fontRenderer.pushText(text);

		while(!window.windowShouldClose()) {
			window.pollEvents();
			
			window.clear();
			
			batchRenderer.createBatchedBuffers();
			batchRenderer.drawQuads();
			
			fontRenderer.RenderText();
			
			window.show();
		}
	}

	public static void main(String[] args) {
		new Application();
	}
}
