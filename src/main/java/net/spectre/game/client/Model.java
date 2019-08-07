package net.spectre.game.client;

public class Model {

	private int id;
	private int vertexCount;
	
	public Model(int id, int positions) {
		this.id = id;
		this.vertexCount = positions;
	}
	
	public int getID() {
		return id;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
