package sudokupak;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

	private final SudokuService sudokuService;

	@Autowired
	public SudokuController(SudokuService sudokuService) {
		this.sudokuService = sudokuService;
	}

	@RequestMapping("/new")
	public int[][] newBoard() {
		sudokuService.newBoard();
		return sudokuService.getBoard();
	}

	@RequestMapping("/reset")
	public int[][] resetBoard() {
		sudokuService.reset();
		return sudokuService.getBoard();
	}

	@RequestMapping("/solve")
	public int[][] solveBoard() {
		sudokuService.solve();
		return sudokuService.getBoard();
	}

	@PostMapping("/input")
	public boolean input(@RequestParam Map<String, String> params) {

		int row = Integer.parseInt(params.get("row"));
		int col = Integer.parseInt(params.get("col"));
		int value = Integer.parseInt(params.get("value"));

		return sudokuService.setSquare(row, col, value);
	}
}
