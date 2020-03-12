package sudokupak;

import java.util.stream.IntStream;

public class MySudokuModel implements SudokuModel {

	private static int BOARD_SIZE = 9;
	private static int SUBSECTION_SIZE = 3;
	private static int NO_VALUE = 0;
	private static int MIN_VALUE = 1;
	private static int MAX_VALUE = 9;

	private int[][] _board;

	// Constructor classes.
	MySudokuModel() {
		this._board = new int[BOARD_SIZE][BOARD_SIZE];
	}

	// clear board method
	public void clear() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				this._board[row][col] = NO_VALUE;
			}
		}
	}

	// Get board array
	public int[][] getBoard() {
		return this._board;
	}

	// Set board method from string input.
	public void setBoard(String input) {

		String[] inputRows = input.split("\n");

		if (inputRows.length != BOARD_SIZE) {
			throw new IllegalArgumentException("Invalid number of rows");
		}

		for (int row = 0; row < BOARD_SIZE; row++) {
			if (inputRows[row].length() != BOARD_SIZE) {
				throw new IllegalArgumentException("Invalid numbers in row");
			}
			inputRows[row] = inputRows[row].replace(".", "0");
		}

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				int val = Character.getNumericValue(inputRows[row].charAt(col));
				if (isLegal(row, col, val))
					this._board[row][col] = val;
				else
					throw new IllegalArgumentException("Illegal value in input file");
			}
		}
	}

	public int getSquare(int row, int col) {
		return this._board[row][col];
	}

	public boolean setSquare(int row, int col, int val) {
		if (isLegal(row, col, val)) {
			this._board[row][col] = val;
			return true;
		}

		return false;
	}

	public SudokuModel copy() {
		SudokuModel sudokuModel = new MySudokuModel();

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++)
				sudokuModel.setSquare(row, col, this.getSquare(row, col));
		}

		return sudokuModel;
	}

	public boolean solve() {
		return solve(_board);
	}

	// Checks if it's a legal value at location.
	public boolean isLegal(int row, int col, int val) {

		if (val == NO_VALUE) {
			return true;
		}
		
		if (val < MIN_VALUE || val > MAX_VALUE) {
			return false;
		}

		this._board[row][col] = val;
		if (isValid(this._board, row, col)) {
			return true;
		}

		this._board[row][col] = NO_VALUE;
		return false;
	}

	private boolean solve(int[][] board) {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				if (board[row][column] == NO_VALUE) {
					for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
						board[row][column] = k;
						if (isValid(board, row, column) && solve(board)) {
							return true;
						}
						board[row][column] = NO_VALUE;
					}
					return false;
				}
			}
		}
		return true;
	}

	private boolean isValid(int[][] board, int row, int column) {
		return (rowConstraint(board, row) && columnConstraint(board, column)
				&& subsectionConstraint(board, row, column));
	}

	private boolean rowConstraint(int[][] board, int row) {
		boolean[] constraint = new boolean[BOARD_SIZE];
		return IntStream.range(0, BOARD_SIZE).allMatch(column -> checkConstraint(board, row, constraint, column));
	}

	private boolean columnConstraint(int[][] board, int column) {
		boolean[] constraint = new boolean[BOARD_SIZE];
		return IntStream.range(0, BOARD_SIZE).allMatch(row -> checkConstraint(board, row, constraint, column));
	}

	private boolean subsectionConstraint(int[][] board, int row, int column) {
		boolean[] constraint = new boolean[BOARD_SIZE];
		int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
		int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

		int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
		int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

		for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
			for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
				if (!checkConstraint(board, r, constraint, c))
					return false;
			}
		}
		return true;
	}

	private boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
		if (board[row][column] != NO_VALUE) {
			if (!constraint[board[row][column] - 1]) {
				constraint[board[row][column] - 1] = true;
			} else {
				return false;
			}
		}
		return true;
	}
}
