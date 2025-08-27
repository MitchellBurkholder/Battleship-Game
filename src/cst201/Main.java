package cst201;

public class Main {

	public static void main(String[] args) {
		
		var playerBoard = new Board();
		
		var placer = new ShipPlacer(playerBoard);
		
		playerBoard.printBoard();
		
		placer.placeBattleships("Destroyer", 8, 1);
		placer.placeBattleships("Submarine", 1, 1, 1);
		placer.placeBattleships("Cruiser", 1, 6, 7);
		
		playerBoard.printBoard();
	}
}
