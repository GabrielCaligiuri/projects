import java.util.ArrayList;
import java.util.Objects;

public class Knight extends Piece{

    public Knight(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    @Override
    public ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece){
        ArrayList<Tile> moves = new ArrayList<Tile>();
        if (0 <= piece.getCol() - 2 && piece.getCol() - 2 <= BoardDisplay.BOARD_SIZE-1){
            if (0 <= piece.getRow() + 1 && piece.getRow() + 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() + 1, piece.getCol() - 2))){
                    moves.add(new Tile(piece.getRow() + 1, piece.getCol() - 2));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() + 1, piece.getCol() - 2))){
                    moves.add(new Tile(piece.getRow() + 1, piece.getCol() - 2));
                }
            }
            if (0 <= piece.getRow() - 1 && piece.getRow() - 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() - 1, piece.getCol() - 2))){
                    moves.add(new Tile(piece.getRow() - 1, piece.getCol() - 2));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() - 1, piece.getCol() - 2))){
                    moves.add(new Tile(piece.getRow() - 1, piece.getCol() - 2));
                }
            }
        }
        if (0 <= piece.getCol() + 2 && piece.getCol() + 2 <= BoardDisplay.BOARD_SIZE-1){
            if (0 <= piece.getRow() + 1 && piece.getRow() + 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() + 1, piece.getCol() + 2))){
                    moves.add(new Tile(piece.getRow() + 1, piece.getCol() + 2));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() + 1, piece.getCol() + 2))){
                    moves.add(new Tile(piece.getRow() + 1, piece.getCol() + 2));
                }
            }
            if (0 <= piece.getRow() - 1 && piece.getRow() - 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() - 1, piece.getCol() + 2))){
                    moves.add(new Tile(piece.getRow() - 1, piece.getCol() + 2));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() - 1, piece.getCol() + 2))){
                    moves.add(new Tile(piece.getRow() - 1, piece.getCol() + 2));
                }
            }
        }
        if (0 <= piece.getRow() - 2 && piece.getRow() - 2 <= BoardDisplay.BOARD_SIZE-1){
            if (0 <= piece.getCol() + 1 && piece.getCol() + 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() - 2, piece.getCol() + 1))) {
                    moves.add(new Tile(piece.getRow() - 2, piece.getCol() + 1));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() - 2, piece.getCol() + 1))){
                    moves.add(new Tile(piece.getRow() - 2, piece.getCol() + 1));
                }
            }
            if (0 <= piece.getCol() - 1 && piece.getCol() - 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() - 2, piece.getCol() - 1))) {
                    moves.add(new Tile(piece.getRow() - 2, piece.getCol() - 1));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() - 2, piece.getCol() - 1))){
                    moves.add(new Tile(piece.getRow() - 2, piece.getCol() - 1));
                }
            }
        }
        if (0 <= piece.getRow() + 2 && piece.getRow() + 2 <= BoardDisplay.BOARD_SIZE-1){
            if (0 <= piece.getCol() + 1 && piece.getCol() + 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() + 2, piece.getCol() + 1))){
                    moves.add(new Tile(piece.getRow() + 2, piece.getCol() + 1));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() + 2, piece.getCol() + 1))){
                    moves.add(new Tile(piece.getRow() + 2, piece.getCol() + 1));
                }
            }
            if (0 <= piece.getCol() - 1 && piece.getCol() - 1 <= BoardDisplay.BOARD_SIZE-1){
                if (!(chessBoard.isTileOccupied(piece.getRow() + 2, piece.getCol() - 1))){
                    moves.add(new Tile(piece.getRow() + 2, piece.getCol() - 1));
                }
                if (!(cantTake(chessBoard, piece, piece.getRow() + 2, piece.getCol() - 1))){
                    moves.add(new Tile(piece.getRow() + 2, piece.getCol() - 1));
                }
            }
        }
        return moves;
    }

    private static boolean cantTake(Board chessBoard, Piece piece, int targetRow, int targetCol){
        Piece targetPiece = chessBoard.findPiece(targetRow, targetCol);
        if (targetPiece != null){
            return (Objects.equals(piece.getColor(), targetPiece.getColor()));
        }
        else{
            return false;
        }
    }
}
