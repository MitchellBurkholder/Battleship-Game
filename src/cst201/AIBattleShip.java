package cst201;

import java.util.Random;

public class AIBattleShip {
	
	private Random randNum = new Random();
	
	public AIBattleShip() {
		chooseShipPlacement();
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
	
	public int[] shootTarget() {
	
		int[] targetCell = {randNum.nextInt(10 - 1 + 1) + 1, randNum.nextInt(10 - 1 + 1) + 1};
		return targetCell;
	}

	public void attack(){
		int[] targetCell = shootTarget();
		
	}
}





