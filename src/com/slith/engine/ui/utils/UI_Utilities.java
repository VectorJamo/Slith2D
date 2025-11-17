package com.slith.engine.ui.utils;

import com.slith.engine.graphics.Window;
import com.slith.engine.maths.vec2;
import com.slith.engine.shapes.Color;
import com.slith.engine.ui.BMPFontRenderer;
import com.slith.engine.ui.Text;

public class UI_Utilities {
	private static Window window;
	private static BMPFontRenderer fontRenderer;
	private static Text fpsText;
	private static double fpsUpdateFrequency = 1.0; // Every second
	private static double timePassed = 0.0;
	
	public static void Init(Window window) {
		UI_Utilities.window = window;
		
		fontRenderer = new BMPFontRenderer(window, "res/images/ConsolasBMP.bmp");
		
		fpsText = new Text("FPS: ", new vec2(0.0f, 0.0f), 24, new Color(1.0f, 1.0f, 1.0f, 1.0f), fontRenderer);
		fontRenderer.pushText(fpsText);
	}
	
	public static void Update() {
		UpdateFPS();
	}
	
	private static void UpdateFPS() {
		timePassed += window.getDeltaTime();
		if(timePassed > fpsUpdateFrequency) {
			timePassed = 0.0f;

			int fps = (int)(1.0/window.getDeltaTime());
			String text = "FPS:" + Integer.toString(fps);
			fpsText.setText(text);
		}
	}
	public static void RenderFPS() {
		fontRenderer.renderText();
	}
}
