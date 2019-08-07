package net.spectre.game.client.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Shader {

	private int id;
	private int vertexShaderID;
	private int fragShaderID;
	
	private static FloatBuffer matrixBufferTemp = BufferUtils.createFloatBuffer(16);
	
	public Shader(String shader) {
		vertexShaderID = loadShader(Paths.get("data", "shaders", shader + ".vsh").toFile(), GL20.GL_VERTEX_SHADER);
		fragShaderID = loadShader(Paths.get("data", "shaders", shader + ".fsh").toFile(), GL20.GL_FRAGMENT_SHADER);
		
		id = GL20.glCreateProgram();
		GL20.glAttachShader(id, vertexShaderID);
		GL20.glAttachShader(id, fragShaderID);
		this.bindAttributes();
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
		this.getAllUniformLocations();
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
		if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Shader " + file.getName() + " did not compile!");
			System.out.println(GL20.glGetShaderInfoLog(id, 500));
		}
		return id;
	}
	
	protected int getUnformVarLocation(String name) {
		return GL20.glGetUniformLocation(id, name);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected void setFloat(int loc, float val) {
		GL20.glUniform1f(loc, val);
	}
	
	protected void setVector(int loc, Vector3f vec) {
		GL20.glUniform3f(loc, vec.x, vec.y, vec.z);
	}
	
	public void setBoolean(int loc, boolean val) {
		GL20.glUniform1i(loc, val ? 1 : 0);
	}
	
	public void setMatrix(int loc, Matrix4f val) {
		val.store(matrixBufferTemp);
		matrixBufferTemp.flip();
		GL20.glUniformMatrix4(loc, false, matrixBufferTemp);
		//matrixBufferTemp.clear();
	}
}
