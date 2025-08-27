package cst201;

public class Main {

	public static void main(String[] args) {
		
		var board = new Board();
		
		board.printBoard();
		
		board.placeBattleships("Destroyer", 8, 1);
		board.placeBattleships("Submarine", 1, 1, 1);
		board.placeBattleships("Cruiser", 1, 6, 7);
		
		board.printBoard();
		
	}
}
