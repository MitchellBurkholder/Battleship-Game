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
				System.out.println("\nPlease, enter a number.");
				System.out.print("\nEnter command: ");
				scanner.nextLine();
				continue;
			}
			
			switch(command) {
			
				case 1:
					playerBoard.printBoard(false);
					battleshipSetup();
					enemyPlacer.placeEnemyShips();
					computerBoard.printBoard(false); // CHANGE TO TRUE WHEN DONE!!!
					tradingShots();
					inSession = false;
					break;
					
				case 0:
					System.out.println("\nPlease, come back again.");
					inSession = false;
					break;
					
				default:
					System.out.println("\nPlease, enter valid command.\n");
					System.out.print("\nEnter command: ");
					break;
			}
		}	
	}
	
	private void battleshipSetup() {
		
		playerPlaceDestroyer();
		playerBoard.printBoard(false);
		playerPlaceSubmarine();
		playerBoard.printBoard(false);
		playerPlaceCruiser();
		playerBoard.printBoard(false);
	}
	
	private void playerPlaceDestroyer() {
		
		placeShipMessage("destroyer");
		
		while (true) {
			
			enterCoordinates();
			
			if (playerPlacer.validDestroyerCoordinates(row, col))
				break;
			else
				printErrorMessage();
		}
	
		playerPlacer.placeBattleships("Destroyer", row, col);
	}

	private void playerPlaceSubmarine() {
		
		boolean placingShip = true;
		placeShipMessage("submarine");
		
		while (placingShip) {
			
			printSubPositionOptions();
			position = enterPosition();
			enterCoordinates();
			
			switch(position) {
				case 1:
					if (playerPlacer.validLeftSubmarineCoordinates(row, col))
						placingShip = false;
					else
						printErrorMessage();
					break;
					
				case 2:
					if (playerPlacer.validRightSubmarineCoordinates(row, col))
						placingShip = false;
					else
						printErrorMessage();
					break;
			}		
		}
		
		playerPlacer.placeBattleships("Submarine", position, row, col);
	}
	
	private void playerPlaceCruiser() {
		
		boolean placingShip = true;
		
		placeShipMessage("cruiser");
		
		while (placingShip) {
			
			printCruiserPositionOptions();
			position = enterPosition();
			enterCoordinates();
			
			switch(position) {
			
				case 1:
					if (playerPlacer.validHorizontalCruiserCoordinates(row, col))
						placingShip = false;
					else
						printErrorMessage();
					break;
					
				case 2:
					if (playerPlacer.validVerticalCruiserCoordinates(row, col))
						placingShip = false;
					else
						printErrorMessage();
					break;
			}
		}
		
		playerPlacer.placeBattleships("Cruiser", position, row, col);
	}
	
	private int enterPosition() {
		
		while (true) {
			
			System.out.print("\nChoose position: ");
			
			try {
				position = scanner.nextInt();
				if (position == 1 || position == 2)
					break;
				else
					System.out.println("\nEnter valid position.");
			
			} catch (InputMismatchException e) {
				System.out.println("\nEnter a number.");
				scanner.nextLine();
			}
		}
		
		return position;
	}
	
	private void enterCoordinates() {
		row = enterRow(row);
		col = enterCol(col);
	}

	
	private void placeShipMessage(String name) {
		System.out.println("\nPlace " + name);
	}
	
	
	private int enterCol(int col) {
		
		while (true) {
			
			System.out.print("\nColumn: ");
			
			try {
				col = scanner.nextInt();
				if (validNum(col))
					break;
				else
					System.out.println("\nColumn must be between 0 and 9");
			
			} catch (InputMismatchException e) {
				System.out.println("Enter a number.\n");
				scanner.nextLine();
			}
		}
		return col;
	}

	private int enterRow(int row) {
		
		while (true) {
			
			System.out.print("\nRow: ");
			
			try {
				row = scanner.nextInt();
				if (validNum(row))
					break;
				else 
					System.out.println("\nRow must be between 0 and 9");
				
			} catch (InputMismatchException e) {
				System.out.println("\nEnter a number.");
				scanner.nextLine();
			}
		}
		return row;
	}
	
	private boolean validNum(int num) {
		return num >= 0 && num <= 9;
	}
	
	
	public void printErrorMessage() {
		System.out.println("\nThe ship must be fully on board "
						 + "and cannot be next to another ship. "
						 + "Please, enter valid coordinates.");
	}
	
	private void printSubPositionOptions() {
		System.out.println("\n1 - Place submarine diagonally left.\n"
						 + "2 - Place submarine diagonally right.");
	}
	
	private void printCruiserPositionOptions() {
		System.out.println("\n1 - Place cruiser horizontally.\n"
						 + "2 - Place cruiser vertically.");
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
				System.out.println("You have missed. It is now player's two turn");
				enemy.shootTarget();
			}
		}
	}
}







