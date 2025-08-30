package cst201;

import java.util.Random;

public class ShipPlacer {
	
	private Board board;
	private int size; 
	Cell[][] grid;
	
	public ShipPlacer(Board board) {
		this.board = board;
		this.size = board.getSIZE();
		this.grid = board.getGrid();
	}
	
	public void placeEnemyShips() {
	
		var random = new Random();
		
		placeDestroyerRandomly(random);
		placeCruiserRandomly(random);
		placeSubmarineRandomly(random);
	}

	private void placeSubmarineRandomly(Random random) {
		var rowS = random.nextInt(8) + 1;
		var colS = random.nextInt(8) + 1;
		var subPosition = random.nextInt(2) + 1;
		
		switch(subPosition) {
			case 1:
				placeLeftSubRandomly(random, rowS, colS, subPosition);
				break;
				
			case 2:
				placeRightSubRandomly(random, rowS, colS, subPosition);
				break;
				
			default:
				throw new IllegalArgumentException("Invalid submarine position");
		}
			
	}

	private void placeRightSubRandomly(Random random, int rowS, int colS, int subPosition) {
		while(!validRightSubmarineCoordinates(rowS, colS)) {
			rowS = random.nextInt(8) + 1;
			colS = random.nextInt(8) + 1;
		}
		
		placeBattleships("Submarine", subPosition, rowS, colS);
	}

	private void placeLeftSubRandomly(Random random, int rowS, int colS, int subPosition) {
		while(!validLeftSubmarineCoordinates(rowS, colS)) {
			rowS = random.nextInt(8) + 1;
			colS = random.nextInt(8) + 1;
		}
		
		placeBattleships("Submarine", subPosition, rowS, colS);
	}

	private void placeCruiserRandomly(Random random) {
		var rowC = random.nextInt(8) + 1;
		var colC = random.nextInt(8) + 1;
		var cruiserPosition = random.nextInt(2) + 1;
		
		switch(cruiserPosition) {
		
			case 1:
				placeHorizontalCruiserRandomly(random, rowC, colC, cruiserPosition);
				break;
				
			case 2:
				placeVerticalCruiserRandomly(random, rowC, colC, cruiserPosition);
				break;
				
			default:
				throw new IllegalArgumentException("Invalid cruiser position.");
		} 
		
	}

	private void placeVerticalCruiserRandomly(Random random, int rowC, int colC, int cruiserPosition) {
		while (!validVerticalCruiserCoordinates(rowC, colC)) {
			rowC = random.nextInt(8) + 1;
			colC = random.nextInt(10);
		}
		placeBattleships("Cruiser", cruiserPosition, rowC, colC);
	}

	private void placeHorizontalCruiserRandomly(Random random, int rowC, int colC, int cruiserPosition) {
		while (!validHorizontalCruiserCoordinates(rowC, colC)) {
			rowC = random.nextInt(10);
			colC = random.nextInt(8) + 1;
		}
		placeBattleships("Cruiser", cruiserPosition, rowC, colC);
	}

	private void placeDestroyerRandomly(Random random) {
		var rowD = random.nextInt(9);
		var colD = random.nextInt(9);
		
		placeBattleships("Destroyer", rowD, colD);
	}
	
	public void placeBattleships(String name, int row, int col) {
		
		switch(name) {
			case "Destroyer":
				placeDestroyer(row, col);
				break;
		}
	}
	
	// Overload placeBattleships() method
	public void placeBattleships(String name, int position, int row, int col) {
		switch(name) {
			case "Submarine":
				placeSubmarine(position, row, col);
				break;
				
			case "Cruiser":
				placeCruiser(position, row, col);
				break;
		}
	}

	private void placeDestroyer(int row, int col) {
		
		if (!validDestroyerCoordinates(row, col))
			return;
		
		for (int i = 0; i <= 1; i++)
			for (int j = 0; j <= 1; j++)
				grid[row + i][col + j].setOccupiedByDestroyer(true);
		
		for (int i = -1; i <= 2; i++)
			for (int j = -1; j <= 2; j++) {
				if (!cellIsOnBoard(row + i, col + j) ||
					grid[row + i][col + j].isOccupiedByDestroyer())
					continue;
				else
					grid[row + i][col + j].setNextToShip(true);
			}	
	}
	
	private void placeSubmarine(int position, int row, int col) {
		
		switch(position) {
		
			case 1: // Place submarine diagonally left
				placeSubmarineDiagonallyLeft(row, col);
				break;
				
			case 2: // Place submarine diagonally right
				placeSubmarineDiagonallyRight(row, col);
				break;
		}
	}
	
	private void placeCruiser(int position, int row, int col) {
		
		switch(position) {
			
			case 1: // Place cruiser horizontally
				placeCruiserHorizontally(row, col);
				break;
				
			case 2: // Place cruiser vertically
				placeCruiserVertically(row, col);
				break;
		}
	}

	private void placeCruiserVertically(int row, int col) {
		if (!validVerticalCruiserCoordinates(row, col))
			return;
		
		grid[row][col].setOccupiedByCruiser(true);
		grid[row - 1][col].setOccupiedByCruiser(true);
		setNeighborsToNextToShip(row - 1, col);
		grid[row + 1][col].setOccupiedByCruiser(true);
		setNeighborsToNextToShip(row + 1, col);
	}

	private void placeCruiserHorizontally(int row, int col) {
		if (!validHorizontalCruiserCoordinates(row, col))
			return;
		
		grid[row][col].setOccupiedByCruiser(true);
		grid[row][col - 1].setOccupiedByCruiser(true);
		setNeighborsToNextToShip(row, col - 1);
		grid[row][col + 1].setOccupiedByCruiser(true);
		setNeighborsToNextToShip(row, col + 1);
	}

	private void placeSubmarineDiagonallyLeft(int row, int col) {
		
		if (!validLeftSubmarineCoordinates(row, col))
			return;
		
		grid[row + 1][col + 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row + 1, col + 1);
		
		grid[row][col].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row, col);
		
		grid[row - 1][col - 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row - 1, col -1);
	}
	
	private void placeSubmarineDiagonallyRight(int row, int col) {

		if (!validRightSubmarineCoordinates(row, col))
			return;
		
		grid[row + 1][col - 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row + 1, col - 1);
		
		grid[row][col].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row, col);
		
		grid[row - 1][col + 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row - 1, col + 1);
	}

	
	
	private void setNeighborsToNextToShip(int row, int col) {
		for (int i = -1; i <= 1; i++)
			for (int j = - 1; j <= 1; j++) {
				int r = row + i;
				int c = col + j;
				
				if (!cellIsOnBoard(r, c) || grid[r][c].isOccupied() || grid[r][c].isNextToShip())
					continue;
				else
					grid[r][c].setNextToShip(true);
			}
	}
	
	public boolean validDestroyerCoordinates(int row, int col) {
		for (int i = 0; i <= 1; i++)
			for (int j = 0; j <= 1; j++)
				if (!cellIsOnBoard(row + i, col + j) ||
					grid[row + i][col + j].isNextToShip()) 
					return false;
				
		return true;
	}
	
//	public boolean validSubmarineCoordinates(int row, int col) {
//		for (int i = -1; i <= 1; i++)
//			for (int j = -1; j <= 1; j++)
//				if (!cellIsOnBoard(row + i, col + j) ||
//					grid[row + i][col + j].isNextToShip())
//					return false;
//		
//		return true;
//	}
	
	public boolean validLeftSubmarineCoordinates(int row, int col) {
		return validCellCoordinates(row + 1, col + 1) &&
			   validCellCoordinates(row, col) &&
			   validCellCoordinates(row - 1, col - 1);
	}
	
	public boolean validRightSubmarineCoordinates(int row, int col) {
		return validCellCoordinates(row + 1, col - 1) &&
			   validCellCoordinates(row, col) &&
			   validCellCoordinates(row - 1, col + 1);
	}
	
	public boolean validHorizontalCruiserCoordinates(int row, int col) {
	
		for (int j = -1; j <= 1; j++) {
			if (!cellIsOnBoard(row, col + j) ||
				grid[row][col + j].isNextToShip())
				return false;
			}
		
		return true;
	}
	
	public boolean validVerticalCruiserCoordinates(int row, int col) {
		for (int i = -1; i <= 1; i++) {
			
			if (!cellIsOnBoard(row + i, col) ||
				grid[row + i][col].isNextToShip())
				return false;
			}
		
		return true;
	}
	
	private boolean validCellCoordinates(int row, int col) {
		return cellIsOnBoard(row, col) && 
			   !grid[row][col].isNextToShip() &&
			   !grid[row][col].isOccupied();
	}
	
	public boolean cellIsOnBoard(int row, int col) {
		return row >= 0 && row < size && col >= 0 && col < size;
	}
}
