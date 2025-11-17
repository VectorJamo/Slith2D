package com.slith.engine.ui;


import java.util.ArrayList;

import com.slith.engine.graphics.*;

public class BMPFontRenderer {
	private SimpleBatchRenderer renderer;
	private Texture fontTexture;
	private ArrayList<Text> texts;
	
	public BMPFontRenderer(Window window, String filePath) {
		RawImage bmpImage = Texture.getImageBuffer(filePath);
		RawImage pngImage = Texture.bmpToPng(bmpImage);
		
		fontTexture = new Texture(pngImage);
		
		renderer = new SimpleBatchRenderer(window);
		
		texts = new ArrayList<Text>();
	}
	
	public void pushText(Text text) {
		texts.add(text);
	}
	
	public void removeText(Text text) {
		texts.remove(text);
	}
	
	public void batchTexts() {
		renderer.clearQuads();
		
		//System.out.println("--------ANOTHER BATCH----------");
		int totalQuads = 0;
		for(int i = 0; i < texts.size(); i++) {
			Text text = texts.get(i);
			ArrayList<RenderableQuad> textQuads = text.getCharacterQuads();
			//System.out.println("Got Text Quad: " + text.getText());
			for(int j = 0; j < textQuads.size(); j++) {
				renderer.pushQuad(textQuads.get(j));
				//System.out.println("Quad to be drawn at:" +
				//textQuads.get(i).getPositions().getX() + ", " + textQuads.get(i).getPositions().getY());
				totalQuads++;
			}
		}
		//System.out.println("Total Quads in this batch: " + totalQuads);
	}
	
	
	public void renderText() {
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
