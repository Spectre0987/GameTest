package net.spectre.game.client.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader {

	private int id;
	private int vertexShaderID;
	private int fragShaderID;
	
	public Shader(String vertex, String frag) {
		vertexShaderID = loadShader(new File("shaders" + File.separator + vertex), GL20.GL_VERTEX_SHADER);
		fragShaderID = loadShader(new File("shaders" + File.separator + vertex), GL20.GL_FRAGMENT_SHADER);
		
		id = GL20.glCreateProgram();
		GL20.glAttachShader(id, vertexShaderID);
		GL20.glAttachShader(id, fragShaderID);
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int att, String varName) {
		GL20.glBindAttribLocation(this.id, att, varName);
	}
	
	public void destroy() {
		unbind();
		GL20.glDetachShader(id, this.vertexShaderID);
		GL20.glDetachShader(id, this.fragShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragShaderID);
		GL20.glDeleteProgram(id);
	}
	
	public void bind() {
		GL20.glUseProgram(id);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public static int loadShader(File file, int type) {
		String shader = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while(reader.ready()) {
				shader += reader.readLine() + "\n";
			}
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		int id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, shader);
		GL20.glCompileShader(id);
		if(GL20.glGetShader(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Shader " + file.getName() + " did nnot compile!");
			System.out.println(GL20.glGetShaderInfoLog(id, 500));
		}
		return id;
	}
}
