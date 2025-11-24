package com.slith.engine.input;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

public class MouseManager {
	private static final int MAX_BUTTONS = 3;
	
	private static boolean[] mouseButtonPressed = new boolean[MAX_BUTTONS];
	private static boolean[] mouseButtonReleased = new boolean[MAX_BUTTONS];
	
	private static int mousePosX, mousePosY;
	private static boolean isMouseInside = false;
	
	public static void init(long window) {
		glfwSetMouseButtonCallback(window, (long win, int button, int action, int mods) -> {
			if(action == GLFW_PRESS) {
				mouseButtonPressed[button] = true;
			}
			if(action == GLFW_RELEASE) {
				mouseButtonReleased[button] = true;
			}
		});
		glfwSetCursorPosCallback(window, (long win, double xpos, double ypos) -> {
			mousePosX = (int)xpos;
			mousePosY = (int)ypos;
		});
		glfwSetCursorEnterCallback(window, (long win, boolean entered) -> {
			isMouseInside = entered;
		});
	}
	
	public static boolean isMouseButtonPressed(int button) {
		return mouseButtonPressed[button];
	}

	public static boolean isMouseButtonReleased(int button) {
		return mouseButtonReleased[button];
	}
	
	public static int getMouseX() {
		return mousePosX;
	}
	public static int getMouseY() {
		return mousePosY;
	}
	public static boolean isMouseInsideWindow() {
		return isMouseInside;
	}
	
	public static void resetButtons() {
		Arrays.fill(mouseButtonPressed, false);
		Arrays.fill(mouseButtonReleased, false);
	}
}
