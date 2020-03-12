package sudokupak;

public interface SudokuModel {	
  void    clear();
  int[][] getBoard();
  void    setBoard(String input);
  int     getSquare(int row, int col);
  boolean setSquare(int row, int col, int val);
  boolean isLegal(int row, int col, int val);
  boolean solve();
  SudokuModel copy();
  default boolean isUnique() {return true;}  // Optional method
}