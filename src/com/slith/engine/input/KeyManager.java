package com.slith.engine.input;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

public class KeyManager extends GLFWKeyCallback {
	private static final int MAX_KEYS = 300;
	
	private static boolean[] keysPressed = new boolean[MAX_KEYS];
	private static boolean[] keysHold = new boolean[MAX_KEYS];
	private static boolean[] keysReleased = new boolean[MAX_KEYS];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if(action == GLFW_PRESS) {
			keysPressed[key] = true;
		}
		if(action == GLFW_REPEAT) {
			keysHold[key] = true;
		}
		if(action == GLFW_RELEASE) {
			keysHold[key] = false;
			keysReleased[key] = true;
		}
	}
	
	public static boolean isKeyPressed(int key) {
		return keysPressed[key];
	}
	public static boolean isKeyHold(int key) {
		return keysHold[key];
	}
	
	public static boolean isKeyReleased(int key) {
		return keysReleased[key];
	}
	
	public static void resetKeys() {
		Arrays.fill(keysPressed, false);
		Arrays.fill(keysReleased, false);
	}
	
}
