package net.spectre.game;

import org.lwjgl.opengl.Display;

import net.spectre.game.client.GameRenderer;
import net.spectre.game.client.Model;
import net.spectre.game.client.ModelLoader;
import net.spectre.game.client.renderer.RenderEngine;
import net.spectre.game.client.shaders.StaticShader;

public class Main {

	private static boolean IS_PAUSED = false;
	private static GameRenderer renderer = new GameRenderer();
	private static ModelLoader modelLoader = new ModelLoader();
	private static StaticShader shader;
	
	private static Thread clientThread = new Thread("Game Client") {

		@Override
		public void run() {
			RenderEngine.startDisplay();
			shader = new StaticShader();
			  float[] vert = {
			    -0.5f, 0.5f, 0f,
			    -0.5f, -0.5f, 0f,
			    0.5f, -0.5f, 0f,
			    0.5f, -0.5f, 0f,
			    0.5f, 0.5f, 0f,
			    -0.5f, 0.5f, 0f
			  };
			Model test = modelLoader.loadToVAO(vert);
			shader.bind();
			
			while(!Display.isCloseRequested()) {
				renderer.prepareFrame();
				shader.bind();
				renderer.renderModel(test);
				shader.unbind();
				RenderEngine.update();
			}
			shader.unbind();
			shader.destroy();
			modelLoader.clean();
			RenderEngine.stopDisplay();
		}
		
	};
	
	public static void main(String[] args) {
		clientThread.start();
	}
	
	public static void run() {
		
	}
}
