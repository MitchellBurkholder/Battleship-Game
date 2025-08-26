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

	private void printOutOfBoundsMessage() {
		System.out.println("The ship (or part of the ship) cannot be placed outside the board. "
						 + "Please, enter valid coordinates.");
	}
	
	private void printNextToShipMessage() {
		System.out.println("The ship cannot be placed next to anoter ship. "
						 + "Please, enter valid coordinates.");
	}
	
	private boolean validDestroyerCoordinates(int row, int col) {
		for (int i = 0; i <= 1; i++)
			for (int j = 0; j <= 1; j++)
				if (!cellIsOnBoard(row + i, col + j)) {
					printOutOfBoundsMessage();
					return false;
				}
				else if (grid[row + i][col + j].isNextToShip()) {
					printNextToShipMessage();
					return false;
				}
		return true;
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
