import java.util.ArrayList;

/**
 * <h2><code><b>Piece</b></code> class</h2>
 * <ol><p>Contains 4 parameters</p>
 *  <li>Name - Name of piece (Ex: "Pawn")</li>
 *  <li>Color - Color of piece (Ex: "Black")</li>
 *  <li>Row - Row the piece is on</li>
 *  <li>Col - Column the piece is on</li>
 *  </ol>
 *  <p>Contains a function framework for <code><b>possibleMoves</b></code> and getters and setters for all parameters</p>
 * @author Gabriel Caligiuri
 */
public abstract class Piece {
    public String name;
    public String color;
    public int row;
    public int col;
    public Piece (String name, String color, int row, int col) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public abstract ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public void setPosition(int row, int col) {
        this.col = col;
        this.row = row;
    }
}
