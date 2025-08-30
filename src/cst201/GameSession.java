package cst201;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameSession {
	
	private int row = -1;
	private int col = -1;
	private int position = 0;
	
	private int command = -1;
	
	// These variables will help with determining who will win.
	private int playerScore = 0;
	private int aiScore = 0;
	
	private Board playerBoard = new Board();
	private Board computerBoard = new Board();
	
	private Board aiBoard = new Board();
	private AIBattleShip enemy = new AIBattleShip(aiBoard);
	
	private ShipPlacer playerPlacer = new ShipPlacer(playerBoard);
	private ShipPlacer enemyPlacer = new ShipPlacer(computerBoard);
	
	private Scanner scanner = new Scanner(System.in);
	
	public void start() {
		
		printMainMenu();
		
		System.out.print("Enter command: ");
		command = -1;
		
		chooseMenuOptions();
		
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
				System.out.println("\nPlease, enter a number.\n");
				System.out.print("Enter command: ");
				scanner.nextLine();
				continue;
			}
			
			switch(command) {
			
				case 1:
					playerBoard.printBoard(false);
					battleshipSetup();
					enemyPlacer.placeEnemyShips();
					computerBoard.printBoard(true); // CHANGE TO TRUE WHEN DONE
					tradingShots();
					inSession = false;
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
	
	private void battleshipSetup() {
		
		playerPlaceShip("Destroyer");
	}
	
	private void playerPlaceShip(String name) {
		
		placeShipMessage(name);
		
		while (true) {
			
			enterCoordinates();
			
			if (destroyerWithinBoard(row, col))
				break;
			else
				printOutOfBoundsMessage();
		}
	
		playerPlacer.placeBattleships(name, row, col);
		playerBoard.printBoard(false);
	}

	// Overload playerPlaceShip
	private void playerPlaceShip(String name,
							     int position) {
		placeShipMessage(name);
	}
	
	private void enterCoordinates() {
		row = enterRow(row);
		col = enterCol(col);
	}

	
	private void placeShipMessage(String name) {
		System.out.println("\nPlace " + name + ".\n");
	}
	
	
	private int enterCol(int col) {
		
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

	private int enterRow(int row) {
		
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
	
	private boolean shipWithinBoard(int row,
									int col,
									int minRow,
									int minCol) {
		return true;
	}
	
	public void printOutOfBoundsMessage() {
		System.out.println("\nThe ship (or part of the ship) cannot be placed outside the board. "
						 + "Please, enter valid coordinates.\n");
	}
	
	private void printShipOverlapMessage() {
		System.out.println("\nThe ship cannot be placed on top or next to anoter ship. "
						 + "Please, enter valid coordinates.\n");
	}
	
	private void tradingShots(){
		
		if(playerScore == 10){
			System.out.println("Player one has won the game. Game over");
			return;
		} else if(aiScore == 10){
			System.out.println("Player one has won the game. Game over");
			return;
		}
		
		int rowCord = 0;
		int columnCord = 0;
		int randRowCord = enemy.shootTarget();
		int randColumnCord = enemy.shootTarget();
		
		System.out.println("Enter the row you want to hit");
		rowCord = scanner.nextInt();

		System.out.println("Enter the column you want to hit");
		columnCord = scanner.nextInt();
		
		if(computerBoard.getGrid(rowCord, columnCord).isHit()){
			System.out.println("This cell has already been hit, please choose another cell");
			tradingShots();
		}
		else {
			computerBoard.getGrid(rowCord, columnCord).setHit(true);
			if(computerBoard.getGrid(rowCord, columnCord).isOccupied()){
				System.out.println("A ship has been hit at " + computerBoard.getGrid(rowCord, columnCord));
				playerScore++;
				playerBoard.printBoard(false);
				computerBoard.printBoard(true);
				tradingShots();
			}
			else {
				System.out.println("You've have missed. It is now player's two turn");
				
				playerBoard.getGrid(randRowCord, randRowCord).setHit(true);
				
				System.out.println("Player two has hit " + playerBoard.getGrid(randRowCord, randRowCord));
	
				while(playerBoard.getGrid(randRowCord, randColumnCord).isOccupied()){
					aiScore++;
					randRowCord = enemy.shootTarget();
					randColumnCord = enemy.shootTarget();
					playerBoard.getGrid(randRowCord, randRowCord).setHit(true);
					System.out.println("Player two has hit " + playerBoard.getGrid(randRowCord, randRowCord));
				}
				
				tradingShots();
			}
				
			}
		}
	}
}










