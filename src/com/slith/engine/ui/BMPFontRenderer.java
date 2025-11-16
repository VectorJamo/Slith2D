package com.slith.engine.ui;


import java.util.ArrayList;

import com.slith.engine.graphics.*;

public class BMPFontRenderer {
	private SimpleBatchRenderer renderer;
	private Texture fontTexture;
	private ArrayList<Text> texts;
	
	public BMPFontRenderer(String filePath) {
		RawImage bmpImage = Texture.getImageBuffer(filePath);
		RawImage pngImage = Texture.bmpToPng(bmpImage);
		
		fontTexture = new Texture(pngImage);
		
		renderer = new SimpleBatchRenderer();
		
		texts = new ArrayList<Text>();
	}
	
	public void pushText(Text text) {
		texts.add(text);
	}
	
	public void batchTexts() {
		renderer.getQuadBuffer().clear();
		
		for(int i = 0; i < texts.size(); i++) {
			Text text = texts.get(i);
			ArrayList<RenderableQuad> textQuads = text.getCharacterQuads();
			for(int j = 0; j < textQuads.size(); j++) {
				renderer.pushQuad(textQuads.get(j));
			}
		}
		renderer.createBatchedBuffers();
	}
	
	
	public void RenderText() {
		batchTexts();
		renderer.drawQuads();
	}
	
	public Texture getFontTexture() {
		return fontTexture;
	}
	
	public SimpleBatchRenderer getRenderer() {
		return renderer;
	}
}
