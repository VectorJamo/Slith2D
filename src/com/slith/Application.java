package com.slith;

import com.slith.engine.graphics.*;

public class Application {
	
	
	public Application() {
		Window window = new Window(800, 600, "Slith Engine");
		
		while(!window.windowShouldClose()) {
			window.pollEvents();
			
			window.clear();
			
			window.show();
		}
	}

	public static void main(String[] args) {
		new Application();
	}
}
