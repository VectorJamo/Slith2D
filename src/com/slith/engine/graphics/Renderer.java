package com.slith.engine.graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.slith.engine.maths.vec2;
import com.slith.engine.shapes.Color;
import com.slith.engine.shapes.RectArea;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer extends BasicRenderer {

	public Renderer() {
		super();
	}
	
	@Override
	public void RenderRect(RectArea rect, Color color) {
	}

	@Override
	public void RenderRect(Texture texture, RectArea destination, RectArea source) {
		
	}

	@Override
	public void RenderRectRotated(RectArea rect, Color color, float angleInDegrees) {
		
	}

	@Override
	public void RenderRectRotated(Texture texture, RectArea destination, RectArea source, float angleInDegrees) {
		
	}

	@Override
	public void RenderPoint(vec2 point, Color color) {
		
	}

	@Override
	public void RenderLine(vec2 point1, vec2 point2, Color color) {
		
	}
	
}
