package com.slith.engine.graphics.layers;

import com.slith.engine.graphics.SimpleBatchRenderer;
import com.slith.engine.graphics.Window;
import com.slith.engine.graphics.utils.Shader;

public abstract class Layer {
	protected Window window;
	protected Shader shader;
	protected SimpleBatchRenderer batchRenderer;
	
	public Layer(Window window) {
		this.window = window;
		
		shader = new Shader("res/shaders/default_shaders/batch_rendering/vs.glsl", 
				"res/shaders/default_shaders/batch_rendering/fs.glsl");
		
		batchRenderer = new SimpleBatchRenderer();
	}
	
	public abstract void update();
	public abstract void render();
}
