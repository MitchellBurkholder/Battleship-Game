package cst201;

import java.util.Random;

public class AIBattleShip {

	final int SIZE = 10;

	Cell[][] grid = new Cell[SIZE][SIZE];
	private Random randNum = new Random();
	
	public AIBattleShip(Board aiBoard) {
		chooseShipPlacement(aiBoard);
	}
	private void chooseShipPlacement(Board aiBoard) {
		
		ShipPlacer placer = new ShipPlacer(aiBoard);
		int xPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		int yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		int direction = randNum.nextInt(3 - 1 + 1) + 1;
		
		placer.placeBattleships("Destroyer", xPostionDestroyer, yPostionDestroyer);
		
		xPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		direction = randNum.nextInt(3 - 1 + 1) + 1;
		placer.placeBattleships("Submarine", direction, xPostionDestroyer, yPostionDestroyer);
		
		xPostionDestroyer = randNum.nextInt(10 - 0 + 1) + 1;
		yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		direction = randNum.nextInt(3 - 1 + 1) + 1;
		placer.placeBattleships("Cruiser", direction, xPostionDestroyer, yPostionDestroyer);
	}
	
	public Cell[][] shootTarget() {

		return grid[randNum.nextInt(10 - 1 + 1) + 1][randNum.nextInt(10 - 1 + 1) + 1];
	}
}







