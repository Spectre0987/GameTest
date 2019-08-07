package net.spectre.game.client;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import net.spectre.game.Main;
import net.spectre.game.client.models.TexturedModel;
import net.spectre.game.client.shaders.StaticShader;
import net.spectre.game.util.MathHelper;

public class GameRenderer {

	private static Matrix4f projection;
	private static float FOV = 70;
	private static float NEAR_PLANE = 0.01f;
	private static float FAR_PLANE = 1000;
	
	public GameRenderer(StaticShader shader) {
		//shader.bind();
		//this.createProjMatrix();
		//shader.unbind();
	}
	
	public void prepareFrame() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1, 1, 1, 1F);
	}
	
	static int frame;
	
	public void renderModel(TexturedModel texturedModel) {
		Model model = texturedModel.getModel();
		GL30.glBindVertexArray(model.getID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		Matrix4f transform = MathHelper.createTranslation(new Vector3f(0, 0, 0), 22.5F, ((++frame * 0.1F) % 360), 0, 1F);
		Main.shader.setTransform(transform);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjMatrix() {
		float aspectRatio = (float) Display.getWidth() / Display.getHeight();
		float yScale = (float)(1F / Math.tan(Math.toRadians(FOV / 2F))) * aspectRatio;
		float xScale = yScale / aspectRatio;
		float frustLength = FAR_PLANE - NEAR_PLANE;
		
		projection = new Matrix4f();
		projection.m00 = xScale;
		projection.m11 = yScale;
		projection.m22 = -((FAR_PLANE - NEAR_PLANE) / frustLength);
		projection.m23 = -1;
		projection.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustLength);
		projection.m33 = 0;
	}
}
