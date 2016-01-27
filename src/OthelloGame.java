import java.util.Random;
import java.util.LinkedList;
import java.util.ListIterator;

enum TKind {
	nil, black, white
}; // don't change the order for any reason!

public class OthelloGame {
	TKind[][] board = new TKind[8][8];
	int[] counter = new int[2]; // 0 = black, 1 = white
	boolean PassCounter;

	public OthelloGame() {
		clear();
	}

	public TKind get(int i, int j) {
		return board[i][j];
	}

	// 把(player)子放board的在( move.i,move.j) 這個位子上
	public void set(Move move, TKind player) {
		switch (board[move.i][move.j]) {
		case white:
			counter[1]--;
			break;
		case black:
			counter[0]--;
			break;
		}
		board[move.i][move.j] = player;
		switch (player) {
		case white:
			counter[1]++;
			break;
		case black:
			counter[0]++;
			break;
		}
	}

	// 回傳輸入的player那邊目前在盤面上有幾顆子
	public int getCounter(TKind player) {
		return counter[player.ordinal() - 1];
	}

	// 重新開始盤面
	public void clear() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				board[i][j] = TKind.nil;
		board[3][4] = TKind.black;
		board[4][3] = TKind.black;
		board[3][3] = TKind.white;
		board[4][4] = TKind.white;
		counter[0] = 2;
		counter[1] = 2;
		PassCounter = false;
	}

	// 顯示盤面
	public void println() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				System.out.print(board[i][j] + "\t");
			System.out.println("");

		}
	}

	//
	public int move(Move move, TKind kind) {
		return checkBoard(move, kind);
	}

	public boolean gameEnd() {
		return counter[0] + counter[1] == 64;
	}

	// 檢查 (incx,incy)方向上可不可以下，並回傳中間會有幾個對方的子，set為真的話就是把子放下去
	private int Check(Move move, int incx, int incy, TKind kind, boolean set) {
		TKind opponent;
		int x = move.i;
		int y = move.j;
		if (kind == TKind.black)
			opponent = TKind.white;
		else
			opponent = TKind.black;
		int n_inc = 0;
		x += incx;
		y += incy;
		while ((x < 8) && (x >= 0) && (y < 8) && (y >= 0) && (board[x][y] == opponent)) {
			x += incx;
			y += incy;
			n_inc++;
		}
		if ((n_inc != 0) && (x < 8) && (x >= 0) && (y < 8) && (y >= 0) && (board[x][y] == kind)) {
			if (set)
				for (int j = 1; j <= n_inc; j++) {
					x -= incx;
					y -= incy;
					set(new Move(x, y), kind);
				}
			return n_inc;
		} else
			return 0;
	}

	// 檢查 (incx,incy)這個點可不可以下，可以下的話就下，並回傳改變了幾個點
	private int checkBoard(Move move, TKind kind) {
		// check increasing x
		int j = Check(move, 1, 0, kind, true);
		// check decreasing x
		j += Check(move, -1, 0, kind, true);
		// check increasing y
		j += Check(move, 0, 1, kind, true);
		// check decreasing y
		j += Check(move, 0, -1, kind, true);
		// check diagonals
		j += Check(move, 1, 1, kind, true);
		j += Check(move, -1, 1, kind, true);
		j += Check(move, 1, -1, kind, true);
		j += Check(move, -1, -1, kind, true);
		if (j != 0)
			set(move, kind);
		return j;
	}

	// 檢查 (incx,incy)這個點上可不可以下
	private boolean isValid(Move move, TKind kind) {
		// check increasing x
		if (Check(move, 1, 0, kind, false) != 0)
			return true;
		// check decreasing x
		if (Check(move, -1, 0, kind, false) != 0)
			return true;
		// check increasing y
		if (Check(move, 0, 1, kind, false) != 0)
			return true;
		// check decreasing y
		if (Check(move, 0, -1, kind, false) != 0)
			return true;
		// check diagonals
		if (Check(move, 1, 1, kind, false) != 0)
			return true;
		if (Check(move, -1, 1, kind, false) != 0)
			return true;
		if (Check(move, 1, -1, kind, false) != 0)
			return true;
		if (Check(move, -1, -1, kind, false) != 0)
			return true;
		return false;
	}

	// 檢查player有沒有步可以走
	public boolean userCanMove(TKind player) {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if ((board[i][j] == TKind.nil) && isValid(new Move(i, j), player))
					return true;
		return false;
	}

	// 檢查me贏opponent的機率方法是用(自己邊上子的個數-對方邊上子的個數
	private int strategy(TKind me, TKind opponent) {
		int tstrat = 0;
		for (int i = 0; i < 8; i++)
			if (board[i][0] == opponent)
				tstrat++;
			else if (board[i][0] == me)
				tstrat--;
		for (int i = 0; i < 8; i++)
			if (board[i][7] == opponent)
				tstrat++;
			else if (board[i][7] == me)
				tstrat--;
		for (int i = 0; i < 8; i++)
			if (board[0][i] == opponent)
				tstrat++;
			else if (board[0][i] == me)
				tstrat--;
		for (int i = 0; i < 8; i++)
			if (board[7][i] == opponent)
				tstrat++;
			else if (board[7][i] == me)
				tstrat--;
		return tstrat;
	}

	// nb是黑色的數量，nw是白色的數量
	private class resultFindMax {
		int max, nb, nw;
	};

	private resultFindMax FindMax(int level, TKind me, TKind opponent) {
		int min, score, tnb, tnw;
		TKind[][] TempBoard = new TKind[8][8];
		int[] TempCounter = new int[2];
		resultFindMax res = new resultFindMax();
		level--;
		res.nb = counter[0];
		res.nw = counter[1];
		for (int i = 0; i < 8; i++)
			System.arraycopy(board[i], 0, TempBoard[i], 0, 8);
		System.arraycopy(counter, 0, TempCounter, 0, 2);
		min = 10000; // high value

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if ((board[i][j] == TKind.nil) && (checkBoard(new Move(i, j), me) != 0)) {
					if (level != 0) {
						resultFindMax tres = FindMax(level, opponent, me);
						tnb = tres.nb;
						tnw = tres.nw;
						score = tres.max;
					} else {
						tnb = counter[0];
						tnw = counter[1];
						score = counter[opponent.ordinal() - 1] - counter[me.ordinal() - 1] + strategy(me, opponent);
					}
					if (min > score) {
						min = score;
						res.nb = tnb;
						res.nw = tnw;
					}
					for (int k = 0; k < 8; k++)
						System.arraycopy(TempBoard[k], 0, board[k], 0, 8);
					System.arraycopy(TempCounter, 0, counter, 0, 2);
				}
		res.max = -min;
		return res;
	}

	public boolean findMove(TKind player, int llevel, Move move) {
		TKind[][] TempBoard = new TKind[8][8];
		int[] TempCounter = new int[2];
		int nb, nw, min, n_min;
		boolean found;
		resultFindMax res = new resultFindMax();
		Random random = new Random();

		if (counter[0] + counter[1] >= 52 + llevel) {
			llevel = counter[0] + counter[1] - 52;
			if (llevel > 5)
				llevel = 5;
		}

		for (int i = 0; i < 8; i++)
			System.arraycopy(board[i], 0, TempBoard[i], 0, 8);
		System.arraycopy(counter, 0, TempCounter, 0, 2);
		found = false;
		min = 10000; // high value
		n_min = 1;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if ((board[i][j] == TKind.nil) && (checkBoard(new Move(i, j), player) != 0)) {
					if (player == TKind.black)
						res = FindMax(llevel - 1, TKind.white, player);
					else
						res = FindMax(llevel - 1, TKind.black, player);
					if ((!found) || (min > res.max)) {
						min = res.max;
						nw = res.nw;
						nb = res.nb;
						move.i = i;
						move.j = j;
						found = true;
					} else if (min == res.max) { // RANDOM MOVE GENERATOR
						n_min++;
						if (random.nextInt(n_min) == 0) {
							nw = res.nw;
							nb = res.nb;
							move.i = i;
							move.j = j;
						}
					}
					// if found
					// then PreView(nw,nb);
					for (int k = 0; k < 8; k++)
						System.arraycopy(TempBoard[k], 0, board[k], 0, 8);
					System.arraycopy(TempCounter, 0, counter, 0, 2);
				}
		return found;
	}
	public boolean findRandomMove(TKind player , Move move){
		
		Random random = new Random();
		TKind[][] TempBoard = new TKind[8][8];
		int[] TempCounter = new int[2];
		
		for (int i = 0; i < 8; i++)
			System.arraycopy(board[i], 0, TempBoard[i], 0, 8);
		System.arraycopy(counter, 0, TempCounter, 0, 2);
		
		LinkedList<Move> MoveList = new LinkedList<Move>();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Move t = new Move(i, j);
				if ((board[i][j] == TKind.nil) && (isValid(t, player))) {
					MoveList.add(t);
				}
			}
		}
		if (MoveList.isEmpty()) {
			return false;
		}
		Move randomMove = MoveList.get(random.nextInt(MoveList.size()));
		move.i = randomMove.i;
		move.j = randomMove.j;
		return true;
		
	}
	public boolean findMove2(TKind player,int runtimes,Move move) {
		int win = 0;
		Random random = new Random();
		TKind[][] TempBoard = new TKind[8][8];
		int[] TempCounter = new int[2];
		
		for (int i = 0; i < 8; i++)
			System.arraycopy(board[i], 0, TempBoard[i], 0, 8);
		System.arraycopy(counter, 0, TempCounter, 0, 2);
		
		LinkedList<MoveAndSt> MoveAndStList = new LinkedList<MoveAndSt>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				MoveAndSt t = new MoveAndSt(i, j);
				if ((board[i][j] == TKind.nil) && (isValid(t.m, player))) {
					MoveAndStList.add(t);
				}
			}
		}
		if (MoveAndStList.isEmpty()) {
			return false;
		}
		for (int i = 0; i < runtimes; i++) {
			for (MoveAndSt One : MoveAndStList) {
				move(One.m, player);
				Move tmove = new Move();
				while (userCanMove(TKind.black) || userCanMove(TKind.white)) {
					if(player == TKind.white){
						if (findRandomMove(TKind.black, tmove))
							move(tmove, TKind.black);
						if (findRandomMove(TKind.white, tmove))
							move(tmove, TKind.white);
					}
					else{
						if (findRandomMove(TKind.white, tmove))
							move(tmove, TKind.white);
						if (findRandomMove(TKind.black, tmove))
							move(tmove, TKind.black);
					}
				}
				if (player == TKind.black){
					if (getCounter(TKind.black) > getCounter(TKind.white))
						One.wintime++;
				}
				else{
					if (getCounter(TKind.white)>getCounter(TKind.black))
						One.wintime++;
				}
					
				
				for (int k = 0; k < 8; k++)
					System.arraycopy(TempBoard[k], 0, board[k], 0, 8);
				System.arraycopy(TempCounter, 0, counter, 0, 2);
			}
		}
		int max = -1;
		if(MoveAndStList.isEmpty()){System.out.println("NULL!!");}
		
		for (MoveAndSt One : MoveAndStList) {
			if (max < One.wintime) {
				max = One.wintime;
			}
		}
		ListIterator<MoveAndSt> itr = MoveAndStList.listIterator();
		while(itr.hasNext()) {
			MoveAndSt t = itr.next();
			if (max > t.wintime) {
				itr.remove();
			}
		}
		
		
		MoveAndSt randomMove = MoveAndStList.get(random.nextInt(MoveAndStList.size()));
		move.i = randomMove.m.i;
		move.j = randomMove.m.j;
		return true;
	}
	public static void main(String[] args) {
		OthelloGame board = new OthelloGame();
		Move move = new Move();
		int n = 10;
		int win = 0; int drawn = 0;
		for (int i = 0 ; i < n ; i++) {
			board.clear();
			while (board.userCanMove(TKind.black) || board.userCanMove(TKind.white)) {
				System.out.println("-------------------------------------------------------------------------------");
				board.println();
				if (board.findMove2(TKind.black,1,move)){
					System.out.println("黑色下"+"("+move.i+","+move.j+")");
					board.move(move,TKind.black); 
				}
					
				if (board.findMove2(TKind.white,1,move)){
					System.out.println("白色下"+"("+move.i+","+move.j+")");
					board.move(move,TKind.white);
				}
					
				board.println();
				System.out.println("-------------------------------------------------------------------------------");
			}
			System.out.print("Iteration#="+i+" Result:"+board.getCounter(TKind.black)+"-"+board.getCounter(TKind.white));
			if (board.getCounter(TKind.black) > board.getCounter(TKind.white)) win++;
			if (board.getCounter(TKind.black) == board.getCounter(TKind.white)) drawn++;
			System.out.println("  Win#="+win+"  Drawn#="+drawn);
		}
		System.out.println("Total#="+n+"  Win#="+win+"  Drawn#="+drawn);
	}
}

