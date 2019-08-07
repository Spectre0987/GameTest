package net.spectre.game.client.shaders;

public class StaticShader extends Shader{
	
	public StaticShader() {
		super("vertexShader.txt", "fragShader.txt");
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
