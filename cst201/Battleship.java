package cst201;

public class Battleship {
	
	private String name;
	private int numCells;
	private boolean hit;
	private boolean destroyed;
	
	public Battleship(String name, int numCells) {
		super();
		this.numCells = numCells;
		this.name = name;
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getNumCells() {
		return numCells;
	}

	public void setNumCells(int numCells) {
		this.numCells = numCells;
	}
	public boolean isHit() {
		return hit;
	}
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

}
