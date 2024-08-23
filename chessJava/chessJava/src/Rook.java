import java.util.ArrayList;
import java.util.Objects;

public class Rook extends Piece{
    static boolean blocked;
    public Rook(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    @Override
    public ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece){
        ArrayList<Tile> moves = new ArrayList<Tile>();
        blocked = false;
        for (int i = piece.getRow() + 1; i <= BoardDisplay.BOARD_SIZE-1; i++) {
            if (chessBoard.isTileOccupied(i, piece.getCol())) {
                if (!Objects.equals(chessBoard.findPiece(i, piece.getCol()).getColor(), piece.getColor()) && !blocked) {
                    moves.add(new Tile(i, piece.getCol()));
                }
                blocked = true;
            } else if(!blocked){
                moves.add(new Tile(i, piece.getCol()));
            }
        }
        blocked = false;
        for (int i = piece.getRow() - 1; i >= 0; i--){
            if(chessBoard.isTileOccupied(i, piece.getCol())) {
                if (!Objects.equals(chessBoard.findPiece(i, piece.getCol()).getColor(), piece.getColor()) && !blocked) {
                    moves.add(new Tile(i, piece.getCol()));
                }
                blocked = true;
            } else if(!blocked){
                moves.add(new Tile(i, piece.getCol()));
            }
        }
        blocked = false;
        for (int i = piece.getCol() + 1; i <= BoardDisplay.BOARD_SIZE-1; i++){
            if(chessBoard.isTileOccupied(piece.getRow(), i)) {
                if (!Objects.equals(chessBoard.findPiece(piece.getRow(), i).getColor(), piece.getColor()) && !blocked) {
                    moves.add(new Tile(piece.getRow(), i));
                }
                blocked = true;
            } else if(!blocked){
                moves.add(new Tile(piece.getRow(), i));
            }
        }
        blocked = false;
        for(int i = piece.getCol() - 1; i >= 0; i--){
            if(chessBoard.isTileOccupied(piece.getRow(), i)) {
                if (!Objects.equals(chessBoard.findPiece(piece.getRow(), i).getColor(), piece.getColor()) && !blocked) {
                    moves.add(new Tile(piece.getRow(), i));
                }
                blocked = true;
            } else if(!blocked){
                moves.add(new Tile(piece.getRow(), i));
            }
        }
        return moves;
    }
}
