package sudokupak;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

import org.springframework.stereotype.Service;

@Service
public class MySudokuDataAccessor implements SudokuDataAccessor {

	@Override
	public String getPuzzle() {

		String puzzelsPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\SudokuPuzzles";

		File puzzelsDirectory = new File(puzzelsPath);
		String[] puzzles = puzzelsDirectory.list();

		Random r = new Random();
		int low = 1;
		int high = puzzles.length;
		int rand = r.nextInt(high - low) + low;

		File puzzleFile = new File(puzzelsPath + "//" + puzzles[rand]);

		String input = "";

		try {
			Scanner sc = new Scanner(puzzleFile);
			while (sc.hasNextLine())
				input += sc.nextLine() + '\n';
		} catch (FileNotFoundException ex) {
			return null;
		}

		return input;
	}
}
