package cst201;

public class Board {
	
	final int SIZE = 10;
	Cell[][] grid = new Cell[SIZE][SIZE];
	
	Battleship destroyer = new Battleship("Destroyer", 4);
	Battleship submarine = new Battleship("Submarine", 3);
	Battleship cruiser = new Battleship("Cruiser", 3);
	
	public Board() {
		initializeCells();
	}
	
	public void initializeCells() {
		for (int row = 0; row < SIZE; row++)
			for (int col = 0; col < SIZE; col++)
				grid[row][col] = new Cell(row, col);
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
		
		if (!validSubmarineCoordinates(row, col))
			return;
		
		grid[row][col].setOccupiedBySubmarine(true); // Place the middle cell
		setNeighborsToNextToShip(row, col);
		
		switch(position) { // Place adjoining cells
		
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

	private void placeSubmarineDiagonallyRight(int row, int col) {
		grid[row + 1][col - 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row + 1, col - 1);
		
		grid[row - 1][col + 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row - 1, col + 1);
	}

	private void placeSubmarineDiagonallyLeft(int row, int col) {
		grid[row + 1][col + 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row + 1, col + 1);
		
		grid[row - 1][col - 1].setOccupiedBySubmarine(true);
		setNeighborsToNextToShip(row - 1, col -1);
	}
	
	private void setNeighborsToNextToShip(int row, int col) {
		for (int i = -1; i <= 1; i++)
			for (int j = - 1; j <= 1; j++) {
				int r = row + i;
				int c = col + j;
				
				if (!cellIsOnBoard(r, c) || grid[r][c].occupied() || grid[r][c].isNextToShip())
					continue;
				else
					grid[r][c].setNextToShip(true);
			}
	}
	
	private boolean validDestroyerCoordinates(int row, int col) {
		for (int i = 0; i <= 1; i++)
			for (int j = 0; j <= 1; j++)
				if (!cellIsOnBoard(row + i, col + j)) {
					printOutOfBoundsMessage();
					return false;
				}
				else if (grid[row + i][col + j].isNextToShip()) {
					printShipOverlapMessage();
					return false;
				}
		return true;
	}
	
	private boolean validSubmarineCoordinates(int row, int col) {
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (!cellIsOnBoard(row + i, col + j)) {
					printOutOfBoundsMessage();
					return false;
				}
				else if (grid[row + i][col + j].isNextToShip()) {
					printShipOverlapMessage();
					return false;
				}
		return true;
	}
	
	private boolean validHorizontalCruiserCoordinates(int row, int col) {
	
		for (int j = -1; j <= 1; j++) {
			if (!cellIsOnBoard(row, col + j)) {
				printOutOfBoundsMessage();
				return false;
				}
			else if (grid[row][col + j].isNextToShip()) {
				printShipOverlapMessage();
				return false;
				}
			}
		
		return true;
	}
	
	private boolean validVerticalCruiserCoordinates(int row, int col) {
		for (int i = -1; i <= 1; i++) {
			
			if (!cellIsOnBoard(row + i, col)) {
				printOutOfBoundsMessage();
				return false;
				}
			else if (grid[row + i][col].isNextToShip()) {
				printShipOverlapMessage();
				return false;
				}
			}
		
		return true;
	}
	
	private void printOutOfBoundsMessage() {
		System.out.println("The ship (or part of the ship) cannot be placed outside the board. "
						 + "Please, enter valid coordinates.");
	}
	
	private void printShipOverlapMessage() {
		System.out.println("The ship cannot be placed on top or next to anoter ship. "
						 + "Please, enter valid coordinates.");
	}
	
	public boolean cellIsOnBoard(int row, int col) {
		return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
	}
	
	public void printBoard() {
		printColumnNumbers();
		System.out.println();
		
		for (int row = 0; row < SIZE; row++) {
			indent(3);
			for (int col = 0; col < SIZE; col++)
				System.out.print("+---");
			System.out.println("+");
			
			System.out.print(" " + row + " ");
			
			for (int col = 0; col < SIZE; col++)
				printUpdatedCell(row, col);
			System.out.println("|");
		}
		
		indent(3);
		
		for (int i = 0; i < SIZE; i++)
			System.out.print("+---");
		System.out.println("+");
	}
	
	private void printColumnNumbers() {
		System.out.println();
		indent(5);
		for (int i = 0; i < SIZE; i++)
			System.out.print(i + "   ");
	}
	
	private void printRowNumbers() {
		for (int i = 0; i < SIZE; i++) {
			System.out.println();
			System.out.println(" " + i + " ");
		}
	}
	
	private void printUpdatedCell(int row, int col) {
		var cell = grid[row][col];
		
		if (cell.isOccupiedByDestroyer())
			printCustomCell("D");
		else if (cell.isOccupiedBySubmarine())
			printCustomCell("S");
		else if (cell.isOccupiedByCruiser())
			printCustomCell("C");
		else if (cell.isNextToShip())
			printCustomCell("*");
		else
			printCustomCell(" ");
	}
	
	private void printCustomCell(String content) {
		System.out.print("| " + content + " ");
	}
	
	private void indent(int spaces) {
		for (int i = 0; i < spaces; i++)
			System.out.print(" ");
	}

}
