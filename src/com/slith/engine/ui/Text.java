package com.slith.engine.ui;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.slith.engine.graphics.*;
import com.slith.engine.maths.*;
import com.slith.engine.shapes.Color;
import com.slith.engine.shapes.RectArea;

public class Text {
	
	private String text;
	private Color color;
	private ArrayList<RenderableQuad> characterQuads;
	private BMPFontRenderer fontRenderer;
	
	private vec2 position;
	private int characterSize;
	
	public Text(String text, vec2 position, int charSize, Color color, BMPFontRenderer renderer) {
		this.position = position;
		this.color = color;
		characterSize = charSize;
		this.fontRenderer = renderer;
		this.text = text.toUpperCase();
		
		characterQuads = new ArrayList<RenderableQuad>();
		
		float x = position.getX();
		float y = position.getY();
		float width = charSize;
		float height = charSize;
		
		for(int i = 0; i< this.text.length(); i++) {
			characterQuads.add(createCharacterQuad(new RectArea(new vec2(x, y), new vec2(width, height)), this.text.charAt(i), color));
			x += width;
		}
	}
	
	public void setText(String text) {
		characterQuads.clear();
		this.text = text.toUpperCase();
		
		float x = position.getX();
		float y = position.getY();
		float width = characterSize;
		float height = characterSize;
		
		for(int i = 0; i < this.text.length(); i++) {
			characterQuads.add(createCharacterQuad(new RectArea(new vec2(x, y), new vec2(width, height)), this.text.charAt(i), color));
			x += width;
		}
	}
	
	public RenderableQuad createCharacterQuad(RectArea destination, char charCode, Color color) {
		RectArea textureArea = getCharPlaceInFont(charCode);
		
		// For debugging
		//System.out.println("For Char: " + (char)(charCode));
		//System.out.println("X: " + textureArea.getPosition().getX() + ", Y: " + textureArea.getPosition().getY());
		//System.out.println("WIDTH: " + textureArea.getDimension().getX() + ", HEIGHT: " + textureArea.getDimension().getY());
		
		return new RenderableQuad(destination, fontRenderer.getFontTexture(), textureArea, fontRenderer.getRenderer(), color);
	}
	
	public RectArea getCharPlaceInFont(char c) {
		int x, y, width, height;
		
		int index = (int)c - 32;
		int numRows = 8;
		int numCols = 8;
		
		y = index / numCols;
		x = index % numCols;
		width = 32;
		height = 32;
		
		return new RectArea(new vec2(x*32, y*32), new vec2(width, height));
	}
	
	public ArrayList<RenderableQuad> getCharacterQuads() {
		return characterQuads;
	}
	
	public String getText() {
		return text;
	}
}
