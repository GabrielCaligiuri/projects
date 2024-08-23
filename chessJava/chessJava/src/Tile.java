/**
 * represents a tile on the chessboard at the given row and col
 */
public class Tile {
    int row;

    int col;

    public Tile(int row, int col){
        this.col = col;
        this.row = row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
