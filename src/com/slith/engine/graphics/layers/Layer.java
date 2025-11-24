package com.slith.engine.graphics.layers;

import java.util.HashMap;

import com.slith.engine.graphics.SimpleBatchRenderer;
import com.slith.engine.graphics.Window;
import com.slith.engine.graphics.utils.Shader;

public abstract class Layer {
	protected Window window;
	protected Shader shader;
	protected SimpleBatchRenderer batchRenderer;
	
	protected static HashMap<String, Layer> CurrentLayers = new HashMap<String, Layer>(); 
	
	public Layer(Window window, String layerName) {
		this.window = window;
		
		shader = new Shader("res/shaders/default_shaders/batch_rendering/vs.glsl", 
				"res/shaders/default_shaders/batch_rendering/fs.glsl");
		
		batchRenderer = new SimpleBatchRenderer(window);

		CurrentLayers.put(layerName, this);
	}
	
	public abstract void update();
	public abstract void render();
}
