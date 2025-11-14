package com.slith.engine.ui;


import java.util.ArrayList;

import com.slith.engine.graphics.*;

public class BMPFontRenderer {
	private SimpleBatchRenderer renderer;
	private Texture fontTexture;
	
	public BMPFontRenderer(String filePath) {
		RawImage bmpImage = Texture.getImageBuffer(filePath);
		RawImage pngImage = Texture.bmpToPng(bmpImage);
		
		fontTexture = new Texture(pngImage);
		
		renderer = new SimpleBatchRenderer();
	}
	
	public void pushText(Text text) {
		ArrayList<RenderableQuad> quads = text.getCharacterQuads();
		for(int i =0; i < quads.size(); i++) {
			renderer.pushQuad(quads.get(i));
		}
		renderer.createBatchedBuffers();
	}
	
	
	public void RenderText() {
		renderer.drawQuads();
	}
	
	public Texture getFontTexture() {
		return fontTexture;
	}
	
	public SimpleBatchRenderer getRenderer() {
		return renderer;
	}
}
