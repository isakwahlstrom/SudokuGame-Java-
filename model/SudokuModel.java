/**
 * The SudokuModel class represents the game logic for one game.
 */

/*Imports*/

public class SudokuModel implements Serializable {
    private ArrayList<Tile> GameBoard;
    private ArrayList<Tile> solutionBoard;
    private int[][][] referenceCopy;
    private SudokuUtilities.SudokuLevel sudokuLevel;

    /**
     * Constructor, initializes a new sudoku game.
     * @param level represents the difficulty of the game.
     */
    public SudokuModel(SudokuUtilities.SudokuLevel level) {
        setLevel(level);
        initBoard();
        initSolutionBoard();
    }

    private void initBoard() {
        this.GameBoard = new ArrayList<>();
        for(int row=0;row<9;row++) {
            for(int col=0;col<9;col++)  {
                GameBoard.add(new Tile(row,col, referenceCopy[row][col][0]));
            }
        }
    }

    private void initSolutionBoard() {
        this.solutionBoard = new ArrayList<>();
        for(int row=0;row<9;row++) {
            for(int col=0;col<9;col++)  {
                solutionBoard.add(new Tile(row,col, referenceCopy[row][col][1]));
            }
        }
    }

    /**
     * Sets the game board to the chosen level
     * @param level the difficulty
     */
    public void setLevel(SudokuUtilities.SudokuLevel level) {
        referenceCopy = SudokuUtilities.generateSudokuMatrix(level);
        sudokuLevel = level;
    }

    public SudokuUtilities.SudokuLevel getLevel() {
        return sudokuLevel;
    }

    public void setTile(int row, int col, int number) {
        GameBoard.get(row*9+col).setNumber(number);
    }

    /**
     * Gets the tile-objects value from the selected pane in the form of a string.
     * @param row the selected tiles row
     * @param col the selected tiles column
     * @return the tiles value in string-format.
     */
    public String getTile(int row, int col) {
        int number = GameBoard.get(row*9+col).getNumber();
        char character = (char) (number + '0');
        return String.valueOf(character);
    }

    public boolean getStartTile(int row, int col) {
        return GameBoard.get(row*9+col).isStartTiles();
    }

    private int[][] updateBoard() {
        int[][] copy = new int[9][9];
        for(int row=0;row<9;row++) {
            for(int col=0;col<9;col++) {
                copy[row][col] = GameBoard.get((row*9)+col).getNumber();
            }
        }
        return copy;
    }

    /**
     * Clears the gameboard of all tiles the user has placed out.
     */
    public void clearBoard() {
        for(int row=0;row<9;row++) {
            for(int col=0;col<9;col++) {
                if(!getStartTile(row,col))
                    clearTile(row,col);
            }
        }
    }

    /**
     * Clears the modified tile by setting the value to 0.
     * @param row the selected tiles row
     * @param col the selected tiles column
     */
    public void clearTile(int row, int col) {
        GameBoard.get(row*9+col).setNumber(0);
    }

    /**
     * Checks if all tiles have been placed correctly.
     * @return true if all tiles are correct
     */
    public boolean isGameSolved() {
        int check=0;
        for(int i=0;i<81;i++) {
            if(GameBoard.get(i).getNumber()==solutionBoard.get(i).getNumber())
                check++;
        }
        return (check==81);
    }

    /**
     * Checks if any number has been placed wrong
     * @return false if tiles are wrongly placed, true if all are correct.
     */
    public boolean checkCorrect() {
        for (int i = 0; i < 81; i++) {
            if (GameBoard.get(i).getNumber() != solutionBoard.get(i).getNumber()
                    && GameBoard.get(i).getNumber()!=0)
                return false;
        }
        return true;
    }

    /**
     * When user wants a hint this method places a correct number to the game board.
     * The do-while loop exist so that the correct number is not to be placed
     * on a modified tile or a start tile.
     */
    public void hint() {
        Random rand = new Random();
        int index;
        do { index = rand.nextInt(81); }
        while ((GameBoard.get(index).getNumber()!=0)
                || (GameBoard.get(index).getNumber()==solutionBoard.get(index).getNumber()));
        Tile hint = solutionBoard.get(index);
        GameBoard.get(index).setNumber(hint.getNumber());
        GameBoard.get(index).setStartTileTrue(); // Make immutable.
    }

    public String rules() {
        return  "RULES:" +
                "\n Place numbers into the empty tiles so that the rows," +
                "\n columns and grid(3x3 board) only have one and " +
                "\n only one occurence of each number between 1-9\n";
    }

    @Override
    public String toString() {
        return  "\n" + GameBoard;
    }
}
