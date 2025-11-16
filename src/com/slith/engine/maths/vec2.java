package com.slith.engine.maths;

public class vec2 {
	private float x, y;
	
	public vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static vec2 multiply(vec2 first, vec2 second) {
		return new vec2(first.x*second.x, first.y*second.y);
	}
	public vec2 multiply(vec2 another) {
		return new vec2(this.x*another.x, this.y*another.y);
	}
	public static void multiply(vec2 result, vec2 first, vec2 second) {
		result.x = first.x*second.x;
		result.y = first.y*second.y;
	}
	public static vec2 add(vec2 first, vec2 second) {
		return new vec2(first.x+second.x, first.y+second.y);
	}
	public static void add(vec2 result, vec2 first, vec2 second) {
		result.x = first.x+second.x;
		result.y = first.y+second.y;
	}
	public vec2 add(vec2 another) {
		return new vec2(this.x+another.x, this.y+another.y);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
