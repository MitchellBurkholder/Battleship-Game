package cst201;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class GameSession {
	
	private int row = -1;
	private int col = -1;
	private int position = 0;
	
	private int command = -1;
	
	// These variables will help with determining who will win.
	private int playerScore = 0;
	private int aiScore = 0;
	private final int WINNING_SCORE = 10;
	
	private Board playerBoard = new Board();
	private Cell[][] playerGrid = playerBoard.getGrid();
	
	private Board computerBoard = new Board();
	private Cell[][] computerGrid = computerBoard.getGrid();
	private HashSet<Cell> computerShotSet = new HashSet<>();
	
	private Board aiBoard = new Board();
	
	private ShipPlacer playerPlacer = new ShipPlacer(playerBoard);
	private ShipPlacer enemyPlacer = new ShipPlacer(computerBoard);
	
	private Scanner scanner = new Scanner(System.in);
	
	public void start() throws InterruptedException {
		
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
	
	private void chooseMenuOptions() throws InterruptedException {
		
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
					computerShotSet = fillComputerShotSet();
					System.out.println("\nComputer places ships...");
					Thread.sleep(2000);
					enemyPlacer.placeEnemyShips();
					computerBoard.printBoard(false); // CHANGE TO TRUE WHEN DONE!!!
					navalCombat();
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
				System.out.println("\nEnter a number.");
				scanner.nextLine();
			}
		}
		
		return col;
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
	
	private void navalCombat() throws InterruptedException {
		
		while (playerScore < WINNING_SCORE &&
			   aiScore < WINNING_SCORE) {
			
			playerOffense();
			if (playerScore == WINNING_SCORE) {
				System.out.println("\nCongratulation! You won.");
				return;
			}
			
			computerOffense();
			if (aiScore == WINNING_SCORE) {
				System.out.println("Computer won. You lost.");
				return;
			}
		}
	}
	
	private void playerOffense() throws InterruptedException {
		
		System.out.println("\nMake a shot");
		
		boolean playerTurn = true;
		
		while (playerTurn) {
			
			playerShoots();
			
			if (computerGrid[row][col].isOccupied()) {
				playerScore++;
				computerBoard.printBoard(false); // CHANGE TO TRUE!!!
				System.out.println("Your current score is " + playerScore + ".");
				if (playerScore == WINNING_SCORE) {
					return;
				}
				System.out.println("\nMake another shot.");
			}
			
			else {
				System.out.println("\nYou have missed.");
				playerBoard.printBoard(false);
				playerTurn = false;
				return;
			}
		}
	}

	private void playerShoots() {
		
		while (true) {
			
			enterCoordinates();
			
			var targetCell = computerGrid[row][col];
			
			if (targetCell.isHit())
				System.out.println("\nThis field has been already shot. Try again.");
			
			else {
				targetCell.setHit(true);
				break;
			}
			
		}
	}
	
	private void computerOffense() throws InterruptedException {
		
		System.out.println("\nComputer's turn...");
		Thread.sleep(2000);
		computerShoots();
	}

	private void computerShoots() throws InterruptedException {
		
		var computerTurn = true;
		while (computerTurn) {
			computerTurn = computerShotOutcome(computerTurn);				
		}
	}

	private boolean computerShotOutcome(boolean computerTurn) throws InterruptedException {
		var targetCell = randomShot();
		targetCell.setHit(true);
		
		if (targetCell.isOccupied()) {
			aiScore++;
			playerBoard.printBoard(false);
			System.out.println("\nComputer has hit one of your ships.\n"
							 + "Computer score is " + aiScore + ".");
			if (aiScore == WINNING_SCORE)
				return false;
			
			System.out.println("\nComputer shoots again...");
			Thread.sleep(2000);
		}
		else {
			playerBoard.printBoard(false);
			computerBoard.printBoard(false); // CHANGE TO TRUE!!!
			System.out.println("\nComputer missed. Your turn.");
			computerTurn = false;
		}
		return computerTurn;
	}

	private Cell randomShot() {
		
		var shot = computerShotSet.iterator().next();
		computerShotSet.remove(shot);
		
		var targetCell = playerGrid[shot.getRow()][shot.getCol()];
		

//		}
		
		return targetCell;
	}
	
	private HashSet<Cell> fillComputerShotSet() {
		
		var random = new Random();
		
		while (computerShotSet.size() < 100) {
			int row = random.nextInt(10);
			int col = random.nextInt(10);
			computerShotSet.add(computerGrid[row][col]);
		}
		
		return computerShotSet;
	}

}









