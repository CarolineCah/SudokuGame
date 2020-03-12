package sudokupak;

public interface SudokuService {
  void newBoard(); 
  int[][] getBoard();
  boolean setSquare(int row, int col, int value);
  void reset();
  void solve();
  // Optional methods
  default void makeSolvable() {}
  default void undo() {}
  default void redo() {}
  default void generate() {}
}
