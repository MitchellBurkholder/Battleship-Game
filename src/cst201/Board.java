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
	
	
	
	public void printBoard(boolean isAIBoard) {
		printColumnNumbers();
		System.out.println();
		
		for (int row = 0; row < SIZE; row++) {
			indent(3);
			for (int col = 0; col < SIZE; col++)
				System.out.print("+---");
			System.out.println("+");
			
			System.out.print(" " + row + " ");
			
			for (int col = 0; col < SIZE; col++)
				printUpdatedCell(row, col, isAIBoard);
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
	
	private void printUpdatedCell(int row, int col, boolean isAIBoard) {
		var cell = grid[row][col];
		
		if(isAIBoard){
		if (cell.isHit() && !(cell.isOccupied()))
			printCustomCell("M");
		else if (cell.isHit() && cell.isOccupied())
			printCustomCell("H");
		else 
			printCustomCell(" ");
		return;
		}
		
		if (cell.isOccupiedByDestroyer())
			printCustomCell("D");
		else if (cell.isOccupiedBySubmarine())
			printCustomCell("S");
		else if (cell.isOccupiedByCruiser())
			printCustomCell("C");
		else if (cell.isNextToShip())
			printCustomCell("*");
		else if (cell.isHit() && !(cell.isOccupied()))
			printCustomCell("M");
		else if (cell.isHit() && cell.isOccupied())
			printCustomCell("H");
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
	
	public int getSIZE() {
		return SIZE;
	}
	
	public Cell[][] getGrid() {
		return grid;
	}

	public Cell[][] getGrid(int row, int col) {
		return grid[row][col];
	}

}







