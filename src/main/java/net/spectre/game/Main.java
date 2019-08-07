package net.spectre.game;

import org.lwjgl.opengl.Display;

import net.spectre.game.client.GameRenderer;
import net.spectre.game.client.ModelLoader;
import net.spectre.game.client.models.TexturedModel;
import net.spectre.game.client.renderer.RenderEngine;
import net.spectre.game.client.shaders.StaticShader;
import net.spectre.game.client.textures.TextureMap;

public class Main {

	private static GameRenderer renderer;
	private static ModelLoader modelLoader = new ModelLoader();
	public static StaticShader shader;
	private static TexturedModel model;
	
	public static void main(String[] args) {
		RenderEngine.startDisplay();
		shader = new StaticShader();
		renderer = new GameRenderer(shader);
		  float[] vert = {
		    -0.5F, -0.5F, 0,
		    0.5F, -0.5F, 0,
		    0.5F, 0.75F, 0,
		    -0.5F, 0.75F, 0
		  };
		  int[] indices = {
				  0, 1, 2, 2, 3, 0
		  };
		  float[] uvs = {
				  0, 0,
				  0, 0.5F,
				  0.5F, 0.5F,
				  0.5F, 0
		  };
		  
		TextureMap texture = modelLoader.createTexture("test");
		model = new TexturedModel(modelLoader.loadToVAO(vert, indices, uvs), texture);
		
		
		while(!Display.isCloseRequested()) {
			renderer.prepareFrame();
			render();
			RenderEngine.update();
		}
		shader.destroy();
		modelLoader.clean();
		RenderEngine.stopDisplay();
	}
	
	public static void render() {
		shader.bind();
		renderer.renderModel(model);
		shader.unbind();
	}
}
