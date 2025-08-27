package cst201;

import java.util.Random;

public class AIBattleShip {

	private Random randNum = new Random();
	private Board aiBoard = new Board();

	public AIBattleShip {
		chooseShipPlacement();
	}
	private void chooseShipPlacement() {
		
		int xPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		int yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		int direction = randNum.nextInt(3 - 1 + 1) + 1;
		aiBoard.placeBattleships("Destroyer", direction, xPostionDestroyer, yPostionDestroyer);
		
		xPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		direction = randNum.nextInt(3 - 1 + 1) + 1;
		aiBoard.placeBattleships("Submarine", direction, xPostionDestroyer, yPostionDestroyer);
		
		xPostionDestroyer = randNum.nextInt(10 - 0 + 1) + 1;
		yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		direction = randNum.nextInt(3 - 1 + 1) + 1;
		aiBoard.placeBattleships("Cruiser", direciton, xPostionDestroyer, yPostionDestroyer);
	}
	
	private int[] shootTarget() {
	
		return int[] targetCell ={randNum.nextInt(10 - 1 + 1) + 1, randNum.nextInt(10 - 1 + 1) + 1}; 	
	}

	public void attack(){
		int[] targetCell = shootTarget();
		
	}
}
