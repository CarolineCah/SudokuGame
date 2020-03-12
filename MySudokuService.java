package sudokupak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MySudokuService implements SudokuService {
	
	private final SudokuDataAccessor sudokuDataAccessor;
	
	private SudokuModel startSudokuModel;
	private SudokuModel currentSudokuModel;
	
	@Autowired
	public MySudokuService(SudokuDataAccessor sudokuDataAccessor) {
		this.sudokuDataAccessor = sudokuDataAccessor;
	}
	
	@Override
	public void newBoard() {
		startSudokuModel = new MySudokuModel();
		startSudokuModel.setBoard(sudokuDataAccessor.getPuzzle());
		currentSudokuModel = startSudokuModel.copy();
	}

	@Override
	public int[][] getBoard() {
		return currentSudokuModel.getBoard();
	}

	@Override
	public boolean setSquare(int row, int col, int value) {
		return currentSudokuModel.setSquare(row, col, value);
	}

	@Override
	public void reset() {
		currentSudokuModel = startSudokuModel.copy();
	}

	@Override
	public void solve() {
		currentSudokuModel.solve();
	}
}
