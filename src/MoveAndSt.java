public class MoveAndSt {
	int wintime;
	Move m;

	public MoveAndSt() {
		this.m = new Move();
		wintime = 0;
	}

	public MoveAndSt(int i, int j) {
		this.m = new Move(i, j);
		wintime = 0;
	}

}