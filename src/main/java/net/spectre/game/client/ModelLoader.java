package net.spectre.game.client;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelLoader {

	public static ArrayList<Integer> VAOs = new ArrayList<Integer>();
	public static ArrayList<Integer> VBOs = new ArrayList<Integer>();
	
	public Model loadToVAO(float[] positions) {
		int id = this.createVAO();
		this.storeDataInAttributes(0, positions);
		this.unbind();
		return new Model(id, positions.length / 3);
	}
	
	private int createVAO() {
		int id = GL30.glGenVertexArrays();
		VAOs.add(id);
		GL30.glBindVertexArray(id);
		return id;
	}
	
	private void storeDataInAttributes(int attNumber, float[] data) {
		int vboID = GL15.glGenBuffers();
		VBOs.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = this.createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attNumber, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void clean() {
		for(int vao : VAOs) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : VBOs) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	private void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
