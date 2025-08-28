package cst201;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameSession {
	
	private int command = -1;
	
	Board playerBoard = new Board();
	Board computerBoard = new Board();
	AIBattleShip enenmy = new AIBattleShip();
	ShipPlacer placer = new ShipPlacer(playerBoard);
	
	private Scanner scanner = new Scanner(System.in);
	
	public void start() {
		
		printMainMenu();
		
		System.out.print("Enter command: ");
		command = -1;
		
		chooseMenuOptions();
		
		playerPlaceShips();
		
		scanner.close();
	}
	
	private void printMainMenu() {
		
		System.out.println("Welcome to Battleship Game\n\n"
						 + "1 - Start Game\n"
						 + "0 - Exit\n");
	}
	
	private void chooseMenuOptions() {
		
		boolean inSession = true;
		
		while (inSession) {
			
			try {
				command = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nPlease enter a number.\n");
				System.out.print("Enter command: ");
				scanner.nextLine();
				continue;
			}
			
			switch(command) {
			
				case 1:
					System.out.println("\nPlayer's Board:");
					playerBoard.printBoard();
					playerPlaceShips();
					break;
					
				case 0:
					System.out.println("Please, come back again.");
					inSession = false;
					break;
					
				default:
					System.out.println("\nPlease, enter valid command.\n");
					System.out.print("Enter command: ");
					break;
			}
		}	
	}
	
	private void playerPlaceShips() {
		
		System.out.println("\nPlace Destroyer.\n");
		
		int row = -1;
		int col = -1;
		
		while (true) {
			
			row = enterDestroyerRow(row);
			col = enterDestroyerCol(col);
			
			if (destroyerWithinBoard(row, col))
				break;
			else
				System.out.println("The ship (or part of the ship) cannot be outside the board");
		}
	
		
		placer.placeBattleships("Destroyer", row, col);
		playerBoard.printBoard();
	}

	private int enterDestroyerCol(int col) {
		while (true) {
			
			System.out.print("\nColumn: ");
			
			try {
				col = scanner.nextInt();
				if (validNum(col))
					break;
				else
					System.out.println("Column must be between 0 and 9\n");
			
			} catch (InputMismatchException e) {
				System.out.println("Enter a number.\n");
				scanner.nextLine();
			}
		}
		return col;
	}

	private int enterDestroyerRow(int row) {
		while (true) {
			
			System.out.print("Row: ");
			
			try {
				row = scanner.nextInt();
				if (validNum(row))
					break;
				else 
					System.out.println("Row must be between 0 and 9\n");
				
			} catch (InputMismatchException e) {
				System.out.println("Enter a number.\n");
				scanner.nextLine();
			}
		}
		return row;
	}
	
	private boolean validNum(int num) {
		return num >= 0 && num <= 9;
	}
	
	private boolean destroyerWithinBoard(int row, int col) {
		return row >= 0 && row <= 8 && col >= 0 && col <= 8;
	}
	private void tradingShots(){
		
		int rowCord = 0;
		int columnCord = 0;
		
		System.out.println("Enter the row you want to hit");
		rowCord = scanner.nextInt();

		System.out.println("Enter the column you want to hit");
		columnCord = scanner.nextInt();
	}
}


