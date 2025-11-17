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

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.slith.engine.graphics.*;
import com.slith.engine.graphics.utils.*;

import com.slith.engine.maths.*;
import com.slith.engine.shapes.*;

import com.slith.engine.input.*;

import com.slith.engine.ui.*;
import com.slith.engine.audio.*;

public class Application {
	
	public Application() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		Window window = new Window(800, 600, "Slith Engine");
		
		BMPFontRenderer fontRenderer = new BMPFontRenderer(window, "res/images/ConsolasBMP.bmp");
		Text fpsText = new Text("FPS: ", new vec2(10.0f, 10.0f), 18, new Color(1.0f, 1.0f, 0.0f, 1.0f), fontRenderer);
		fontRenderer.pushText(fpsText);
		
		Renderer renderer = new Renderer();
		Texture texture = new Texture("res/images/minecraft.jpg", 3);
		
		SoundEffect sfx = new SoundEffect("res/audio/pickupCoin.wav");
		
		int fpsUpdateCounter = 0;
		while(!window.windowShouldClose()) {
			
			window.pollEvents();
			
			if(KeyManager.isKeyPressed(GLFW_KEY_SPACE)) {
				sfx.play();
			}
			
			window.clear();
			
			renderer.renderRect(new RectArea(new vec2(0.0f, 0.0f), new vec2(100.0f, 100.0f)), new Color(1.0f, 0.0f, 0.0f, 1.0f),
					0.0f);
			renderer.renderRect(texture, new RectArea(new vec2(100.0f, 100.0f), new vec2(100, 100)), 0.0f);

			fpsUpdateCounter++;
			if(fpsUpdateCounter > 1000) {
				fpsUpdateCounter = 0;

				int FPS = (int)(1/window.getDeltaTime());
				fpsText.setText("FPS:" + Integer.toString(FPS));
			}

			fontRenderer.batchTexts();
			fontRenderer.renderText();

			window.show();
		}
	}

	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		new Application();
	}
}
