package net.spectre.game.client;

public class VAO {

	private int vaoID;
	private int vertexCount;
	
	public VAO(int id, int count) {
		this.vaoID = id;
		this.vertexCount = count;
	}
	
	public int getVaoID() {
		return this.vaoID;
	}
	
	public int getVertexCount() {
		return this.vertexCount;
	}
}
