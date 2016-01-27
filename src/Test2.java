public class Test2 {
	public static void main(String[] args) {
		OthelloGame board = new OthelloGame();
		Move move = new Move();
		int n = 10;
		int win = 0; int drawn = 0;
		for (int i = 0 ; i < n ; i++) {
			board.clear();
			while (board.userCanMove(TKind.black) || board.userCanMove(TKind.white)) {
			//	System.out.println("-------------------------------------------------------------------------------");
				//board.println();
				if (board.findMove2(TKind.black,50,move)){
				//	System.out.println("黑色下"+"("+move.i+","+move.j+")");
					board.move(move,TKind.black); 
				}
					
				if (board.findMove2(TKind.white,50,move)){
				//	System.out.println("白色下"+"("+move.i+","+move.j+")");
					board.move(move,TKind.white);
				}
					
				//board.println();
				//System.out.println("-------------------------------------------------------------------------------");
			}
			System.out.print("Iteration#="+i+" Result:"+board.getCounter(TKind.black)+"-"+board.getCounter(TKind.white));
			if (board.getCounter(TKind.black) > board.getCounter(TKind.white)) win++;
			if (board.getCounter(TKind.black) == board.getCounter(TKind.white)) drawn++;
			System.out.println("  Win#="+win+"  Drawn#="+drawn);
		}
		System.out.println("Total#="+n+"  Win#="+win+"  Drawn#="+drawn);
	}
 
}