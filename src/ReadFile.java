import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile {
	public int CharMapToNum(char c) {
		return Character.getNumericValue(c) - Character.getNumericValue('a');
	}

	public static void main(String[] argv) throws IOException {
		FileReader fr = new FileReader("board.txt");
		//FileWriter fw = new FileWriter("board.txt", true);
		BufferedReader br = new BufferedReader(fr);

		OthelloGame board = new OthelloGame();
		Move move = new Move();
		board.clear();

		String text = br.readLine();

		TKind turn = TKind.black;

		if (text == null) {
			System.out.println("黑色下" + (char) (3 + 'A') + (char) (2 + '0' + 1));
			//fw.write("d3");
			board.move(new Move(3, 2), TKind.black);
		} else {
			while (text.length() > 0) {
				System.out.print(text.charAt(0));
				System.out.print(text.charAt(1));
				if (text.charAt(0) == '-' && text.charAt(1) == '-') {
					System.out.println(((turn == TKind.black) ? "\t黑色" : "\t白色")+"\tPASS");
					if (turn == TKind.black) {
						turn = TKind.white;
					} else {
						turn = TKind.black;
					}
				} else {
					System.out.println("\t" + ((turn == TKind.black) ? "黑色" : "白色") + "\t" + "("+ (Character.getNumericValue(text.charAt(1)) - Character.getNumericValue('0') - 1) + ","+ (Character.getNumericValue(text.charAt(0)) - Character.getNumericValue('a')) + ")");

					move.i = Character.getNumericValue(text.charAt(1)) - Character.getNumericValue('0') - 1;
					move.j = Character.getNumericValue(text.charAt(0)) - Character.getNumericValue('a');
					if (turn == TKind.black) {
						board.move(move, TKind.black);
						turn = TKind.white;
					} else {
						board.move(move, TKind.white);
						turn = TKind.black;
					}
				}
				if (text.length() == 2)
					break;
				text = text.substring(2);

			}
			//board.println();
			if (turn == TKind.black) {
				if (board.findMove2(TKind.black, 1000, move)) {
					System.out.println("黑色下" + "(" + move.i + "," + move.j + ")" + (char) (move.j + 'a')+ (char) (move.i + '0' + 1));
				//	fw.write(String.valueOf((char) (move.j + 'a'))+String.valueOf((char)(move.i + '0' + 1)));
					board.move(move, TKind.black);
				} else {
					System.out.println("黑色PASS");
				//	fw.write("--");
				}
			} else {
				if (board.findMove2(TKind.white, 1000, move)) {
					System.out.println("白色下" + "(" + move.i + "," + move.j + ")" + (char) (move.j + 'a')+ (char) (move.i + '0' + 1));
					//fw.write(String.valueOf((char) (move.j + 'a'))+String.valueOf((char)(move.i + '0' + 1)));
					board.move(move, TKind.white);
				} else {
					System.out.println("白色PASS");
					//fw.write("--");
				}
			}
		}

		fr.close();
	//	fw.close();

	}

	public ReadFile() {
		// TODO Auto-generated constructor stub
	}

}
