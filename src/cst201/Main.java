package cst201;

public class Main {

	public static void main(String[] args) {
		
		var board = new Board();
		
		board.printBoard();
		
		board.placeBattleships("Destroyer", 2, 3);
		
		board.printBoard();
		
	}
}
