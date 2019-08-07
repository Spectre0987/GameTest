package net.spectre.game.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import net.spectre.game.client.textures.TextureMap;

public class ModelLoader {

	public static ArrayList<Integer> VAOs = new ArrayList<Integer>();
	public static ArrayList<Integer> VBOs = new ArrayList<Integer>();
	public static ArrayList<Integer> TEXTURES = new ArrayList<Integer>();
	
	public Model loadToVAO(float[] positions, int[] indices, float[] uvs) {
		int id = this.createVAO();
		this.bindIndicesBuffer(indices);
		this.storeDataInAttributes(0, 3, positions);
		this.storeDataInAttributes(1, 2, uvs);
		this.unbind();
		return new Model(id, indices.length);
	}
	
	private int createVAO() {
		int id = GL30.glGenVertexArrays();
		VAOs.add(id);
		GL30.glBindVertexArray(id);
		return id;
	}
	
	private void storeDataInAttributes(int attNumber, int stride, float[] data) {
		int vboID = GL15.glGenBuffers();
		VBOs.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = this.createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attNumber, stride, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void clean() {
		for(int vao : VAOs) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : VBOs) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int tex : TEXTURES) {
			GL11.glDeleteTextures(tex);
		}
	}
	
	private void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] buffer) {
		int vboID = GL15.glGenBuffers();
		VBOs.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(buffer), GL15.GL_STATIC_DRAW);
	}
	
	public TextureMap createTexture(String name) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(Paths.get("data", "textures", name + ".png").toFile()));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		int id = texture.getTextureID();
		TEXTURES.add(id);
		return new TextureMap(id);
	}
	
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
