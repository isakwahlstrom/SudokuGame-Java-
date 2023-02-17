/**
 * A tile represents one number on the sudoku game board
 * with its position (row & column), the number value and
 * if it is a start tile (= a correct number placed out
 * when the game is initialized).
 */
package se.kth.isakwah.labb4.model;

import java.io.Serializable;

public class Tile implements Serializable {
    private final int row;
    private final int column;
    private int number;
    private boolean startTiles;

    /**
     * Constructor.
     * Sets the tile to a start tile in the beginning of the game if the tiles number isn't 0.
     * @param row is the tiles row on the sudoku board
     * @param col is the tiles the column on the sudoku board
     * @param number is the tiles game number/value
     */
    Tile(int row, int col, int number) {
        this.row = row;
        this.column = col;
        this.number = number;
        if(number!=0)startTiles = true;
        if(number==0)startTiles = false;
    }

    public boolean isStartTiles() {return this.startTiles;}

    public int getRow() {return row;}

    public int getColumn() {return column;}

    public int getNumber() {return number;}

    public void setStartTileTrue(){startTiles=true;}

    /**
     * Modifies the tiles value to the new value(the newNumber). If the tile can't be modified method throws an exception.
     * @param newNumber represents the value that the tile value will be modified to
     * @throw if the tile is immutable.
     */
    public void setNumber(int newNumber) {
        if(!this.startTiles)
            this.number = newNumber;
        else { throw new IllegalStateException("This tile can't be removed or altered");}
    }

    @Override
    public String toString() {
        return "Tile{" +
                "row=" + row +
                ", column=" + column +
                ", number=" + number +
                ", startTiles=" + startTiles +
                '}';
    }
}
