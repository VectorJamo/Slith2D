package com.slith.engine.shapes;

import com.slith.engine.maths.*;

public class RectArea {
	private vec2 position, dimension;
	
	public RectArea(vec2 position, vec2 dimension) {
		this.position = position;
		this.dimension = dimension;
	}
	
	public void SetPosition(vec2 position) {
		this.position = position;
	}
	public void SetDimension(vec2 dimension) {
		this.dimension = dimension;
	}
	
	public vec2 GetPosition() {
		return position;
	}
	public vec2 GetDimension() {
		return dimension;
	}
}
