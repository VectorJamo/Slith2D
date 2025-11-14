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
	
	public static void printMatrix(mat4 mat) {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(mat.values[i+(i*3) + j] + ",");
			}
			System.out.println(" ");
		}
	}
	
	public static mat4 createScale(float xScale, float yScale) {
		mat4 mat = new mat4();
		
		mat.values[0*3 + 0] = xScale;
		mat.values[1+(1*3) + 1] = yScale;
		mat.values[2+(2*3) + 2] = 1.0f;
		mat.values[3+(3*3) + 3] = 1.0f;

		return mat;
	}
	public static void editScaleMatrix(mat4 scaleMatrix, float xScaleNew, float yScaleNew) {
		scaleMatrix.values[0*3 + 0] = xScaleNew;
		scaleMatrix.values[1+(1*3) + 1] = yScaleNew;
	}
	
	public static mat4 createRotate(float angleInDegrees) {
		mat4 mat = new mat4();
		
		mat.values[0*3 + 0] = (float) Math.cos(Math.toRadians(angleInDegrees));
		mat.values[0*3 + 1] = -(float) Math.sin(Math.toRadians(angleInDegrees));
		mat.values[1+(1*3) + 0] = (float) Math.sin(Math.toRadians(angleInDegrees));
		mat.values[1+(1*3) + 1] = (float) Math.cos(Math.toRadians(angleInDegrees));

		return mat;
	}
	public static void editRotationMatrix(mat4 rotationMatrix, float angleInDegrees) {
		rotationMatrix.values[0*3 + 0] = (float) Math.cos(Math.toRadians(angleInDegrees));
		rotationMatrix.values[0*3 + 1] = -(float) Math.sin(Math.toRadians(angleInDegrees));
		rotationMatrix.values[1+(1*3) + 0] = (float) Math.sin(Math.toRadians(angleInDegrees));
		rotationMatrix.values[1+(1*3) + 1] = (float) Math.cos(Math.toRadians(angleInDegrees));
	}
	
	public static mat4 createTranslate(float translateX, float translateY) {
		mat4 mat = new mat4();
		
		mat.values[0*3 + 3] = translateX;
		mat.values[1+(1*3) + 3] = translateY;
		
		return mat;
	}
	public static void editTranslationMatrix(mat4 translationMatrix, float translateX, float translateY) {
		translationMatrix.values[0*3 + 3] = translateX;
		translationMatrix.values[1+(1*3) + 3] = translateY;
	}
	
	public static mat4 createOrthographic(float left, float right, float top, float bottom) {
		mat4 mat = new mat4();
		
		float width = right - left;
		float height = top - bottom;
		float xFactor = 2/width;
		float yFactor = 2/height;
		float translationX = -((left+right)/(right-left));
		float translationY = -((top+bottom)/(top-bottom));
		
		mat.values[0*3 + 0] = xFactor;
		mat.values[1+(1*3) + 1] = yFactor;
		mat.values[0*3 + 3] = translationX;
		mat.values[1+(1*3) + 3] = translationY;
	
		return mat;
	}
}
