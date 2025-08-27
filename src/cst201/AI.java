package cst201;

import java.util.Random;

public class AIBattleShip {

	private Random randNum = new Random();
	private Board aiBoard = new Board();
	
	public void chooseShipPlacement() {
		
		int xPostionDestroyer = randNum.nextInt(10 - 0 + 1) + 1;
		int yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		aiBoard.placeBattleships("Destroyer", xPostionDestroyer, yPostionDestroyer);
		
		xPostionDestroyer = randNum.nextInt(10 - 0 + 1) + 1;
		yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		aiBoard.placeBattleships("Submarine", xPostionDestroyer, yPostionDestroyer);
		
		xPostionDestroyer = randNum.nextInt(10 - 0 + 1) + 1;
		yPostionDestroyer = randNum.nextInt(10 - 1 + 1) + 1;
		aiBoard.placeBattleships("Cruiser", xPostionDestroyer, yPostionDestroyer);
	}
	
	public void shootTarget(Board playerBoard) {
		
		int xcordTarget = randNum.nextInt(10 - 0 + 1) + 1;
		int ycordTarget = randNum.nextInt(10 - 1 + 1) + 1;
		
		// TODO: implement code for trading shots between the player's board and the Ai's board
	}
}
