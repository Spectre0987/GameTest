package net.spectre.game.client.models;

import net.spectre.game.client.Model;
import net.spectre.game.client.textures.TextureMap;

public class TexturedModel {

	private Model model;
	private TextureMap texture;
	
	public TexturedModel(Model model, TextureMap texture) {
		this.model = model;
		this.texture = texture;
	}
	
	public Model getModel() {
		return model;
	}
	
	public TextureMap getTexture() {
		return this.texture;
	}
}
