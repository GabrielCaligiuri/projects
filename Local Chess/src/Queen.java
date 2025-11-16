import java.util.ArrayList;

public class Queen extends Piece{
    private static final int BOARD_SIZE = BoardDisplay.BOARD_SIZE - 1;
    public Queen(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    @Override
    public ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece){
        ArrayList<Tile> moves = new ArrayList<Tile>();
        Bishop bishop = new Bishop(piece.getName(), piece.getColor(), piece.getRow(), piece.getCol());
        Rook rook = new Rook(piece.getName(), piece.getColor(), piece.getRow(), piece.getCol());
        moves.addAll(bishop.possibleMoves(chessBoard, piece));
        moves.addAll(rook.possibleMoves(chessBoard, piece));
        return moves;
    }
}
