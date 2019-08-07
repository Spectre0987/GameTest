package net.spectre.game.client.renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class RenderEngine {

	public static int WIDTH = 1920, HEIGHT = 1080;
	public static int FPS = 120;
	
	public static void startDisplay() {
		
		ContextAttribs att = new ContextAttribs(3, 2);
		att.withForwardCompatible(true);
		att.withProfileCore(true);
		
		try {
			
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), att);
			Display.setTitle("Test Game!");
			Display.setResizable(true);
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	public static void update() {
		Display.sync(FPS);
		Display.update();
		
		
	}
	
	public static void stopDisplay() {
		Display.destroy();
	}
}
