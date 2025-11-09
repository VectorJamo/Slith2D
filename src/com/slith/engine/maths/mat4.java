package com.slith.engine.maths;

public class mat4 {
	
	public float[] values;
	
	public mat4() {
		values = new float[]{
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		};
	}
	
	public static mat4 createNull() {
		mat4 mat = new mat4();
		
		mat.values[0*0] = 0.0f;
		mat.values[1*1] = 0.0f;
		mat.values[2*2] = 0.0f;
		mat.values[3*3] = 0.0f;
		
		return mat;
	}
	
	public static mat4 createScale() {
		mat4 mat = new mat4();
		
		return mat;
	}
	public static mat4 createRotate() {
		mat4 mat = new mat4();
		
		return mat;
	}
	public static mat4 createTranslate() {
		mat4 mat = new mat4();
		
		return mat;
	}
}
