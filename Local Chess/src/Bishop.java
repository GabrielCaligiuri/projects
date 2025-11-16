import java.util.ArrayList;
import java.util.Objects;

public class Bishop extends Piece{
    private static final int BOARD_SIZE = BoardDisplay.BOARD_SIZE - 1;
    int placeHolder;
    boolean topBlock;
    boolean botBlock;
    public Bishop(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    @Override

    /*
    Function to get the possible moves of a bishop and return them as type Tile.
     */
    public ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece) {
        ArrayList<Tile> moves = new ArrayList<>();
        topBlock = false;
        botBlock = false;
        for (int i = piece.getRow() + 1; 0 <= i && i <= BOARD_SIZE; i++){
            placeHolder = i - piece.getRow();
            if (0 <= piece.getCol() + placeHolder && piece.getCol() + placeHolder <= BOARD_SIZE && !topBlock){
                if(chessBoard.isTileOccupied(i, piece.getCol() + placeHolder)){
                    if(!Objects.equals(chessBoard.findPiece(i, piece.getCol() + placeHolder).getColor(), piece.getColor())){
                        moves.add(new Tile(i, piece.getCol() + placeHolder));
                    }
                    topBlock = true;
                }
                if(!topBlock){
                    moves.add(new Tile(i, piece.getCol() + placeHolder));
                }
            }
            if (0 <= piece.getCol() - placeHolder && piece.getCol() - placeHolder <= BOARD_SIZE && !botBlock){
                if(chessBoard.isTileOccupied(i, piece.getCol() - placeHolder)){
                    if(!Objects.equals(chessBoard.findPiece(i, piece.getCol() - placeHolder).getColor(), piece.getColor())){
                        moves.add(new Tile(i, piece.getCol() - placeHolder));
                    }
                    botBlock = true;
                }
                moves.add(new Tile(i, piece.getCol() - placeHolder));
            }
        }
        topBlock = false;
        botBlock = false;
        for (int i = piece.getRow() - 1; 0 <= i && i <= BOARD_SIZE; i--){
            placeHolder = piece.getRow() - i;
            if (0 <= piece.getCol() - placeHolder && piece.getCol() - placeHolder <= BOARD_SIZE && !topBlock){
                if(chessBoard.isTileOccupied(i, piece.getCol() - placeHolder)){
                    if(!Objects.equals(chessBoard.findPiece(i, piece.getCol() - placeHolder).getColor(), piece.getColor())){
                        moves.add(new Tile(i, piece.getCol() - placeHolder));
                    }
                    topBlock = true;
                }
                moves.add(new Tile(i, piece.getCol() - placeHolder));
            }
            if (0 <= piece.getCol() + placeHolder && piece.getCol() + placeHolder <= BOARD_SIZE && !botBlock){
                if(chessBoard.isTileOccupied(i, piece.getCol() + placeHolder)){
                    if(!Objects.equals(chessBoard.findPiece(i, piece.getCol() + placeHolder).getColor(), piece.getColor())){
                        moves.add(new Tile(i, piece.getCol() + placeHolder));
                    }
                    botBlock = true;
                }
                moves.add(new Tile(i, piece.getCol() + placeHolder));
            }
        }
        return moves;
    }
}
