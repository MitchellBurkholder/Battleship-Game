package cst201;

public class Cell {
	
	private int row;
	private int col;
	
	private boolean hit;
	
	private boolean nextToShip;
	private boolean occupiedByDestroyer;
	private boolean occupiedBySubmarine;
	private boolean occupiedByCruiser;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public boolean isOccupied() {
		return occupiedByDestroyer || occupiedBySubmarine || occupiedByCruiser;
	}
	
	// Getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isNextToShip() {
		return nextToShip;
	}

	public void setNextToShip(boolean nextToShip) {
		this.nextToShip = nextToShip;
	}

	public boolean isOccupiedByDestroyer() {
		return occupiedByDestroyer;
	}

	public void setOccupiedByDestroyer(boolean occupiedByDestroyer) {
		this.occupiedByDestroyer = occupiedByDestroyer;
	}

	public boolean isOccupiedBySubmarine() {
		return occupiedBySubmarine;
	}

	public void setOccupiedBySubmarine(boolean occupiedBySubmarine) {
		this.occupiedBySubmarine = occupiedBySubmarine;
	}

	public boolean isOccupiedByCruiser() {
		return occupiedByCruiser;
	}

	public void setOccupiedByCruiser(boolean occupiedByCruiser) {
		this.occupiedByCruiser = occupiedByCruiser;
	}

	

}
