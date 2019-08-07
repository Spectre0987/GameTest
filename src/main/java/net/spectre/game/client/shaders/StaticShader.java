package net.spectre.game.client.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends Shader{
	
	private int projectionMatrix;
	private int transform_loc;
	
	public StaticShader() {
		super("static");
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		this.transform_loc = this.getUnformVarLocation("transform");
		this.projectionMatrix = this.getUnformVarLocation("projection");
	}
	
	public void setTransform(Matrix4f mat) {
		this.setMatrix(this.transform_loc, mat);
	}
	
	public void setProjectionMatrix(Matrix4f mat) {
		this.setMatrix(this.projectionMatrix, mat);
	}

}
